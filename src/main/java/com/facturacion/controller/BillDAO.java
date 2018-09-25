package com.facturacion.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.facturacion.model.Bill;
import com.facturacion.utilities.NumberToString;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class BillDAO {

	private Connection con;

	public List<Bill> getBills(int idClient) throws SQLException {
		con = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Bill> billList = new ArrayList<Bill>();
		String query = "SELECT * FROM BILL B, BILL_HAS_SERVICES BS, SERVICE S WHERE B.CLIENT_ID = ? AND BS.BILL_CODE = B.BILL_NUMBER AND BS.SERVICE_CODE = S.CODE";
		con = DBConnection.createConnection();
		try {
			Bill bill = new Bill();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, idClient);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				bill.setBillNumber(resultSet.getInt("Bill_Number"));
				bill.setDate(resultSet.getString("Date"));
				bill.setStatus(resultSet.getString("Status").charAt(0));
				billList.add(bill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			resultSet.close();
			con.close();
		}
		return billList;
	}

	@SuppressWarnings("resource")
	public boolean generateBill(int[] servicesList, int[] valuesList, long idCliente, double discount) throws SQLException {
		con = null;
		boolean response = false;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		long totalValue = 0;
		long totalBill = 0;
		String query = "INSERT INTO BILL (DATE, CLIENT_ID, TOTAL_PRICE, DISCOUNT) VALUES (?,?,?,?)";
		String queryPrice = "SELECT PRICE FROM SERVICE WHERE CODE = ?";
		String queryBillService = "INSERT INTO BILL_HAS_SERVICES (BILL_CODE, SERVICE_CODE, QUANTITY, TOTAL) VALUES (?,?,?,?)";
		String updateBill = "UPDATE BILL SET TOTAL_PRICE = ? WHERE BILL_NUMBER = ?";

		try {
			con = DBConnection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, getActualDate());
			stmt.setLong(2, idCliente);
			stmt.setLong(3, totalValue);
			stmt.setDouble(4, discount);
			stmt.execute();
			int lastInserted = getLasInsertedBillNumber();

			for (int i = 0; i < servicesList.length; i++) {
				con = DBConnection.createConnection();
				stmt = con.prepareStatement(queryPrice);
				stmt.setInt(1,servicesList[i]);
				resultSet = stmt.executeQuery();
				if(resultSet.next()) {
					totalValue += (resultSet.getLong(1))*valuesList[i];
					stmt = con.prepareStatement(queryBillService);
					stmt.setInt(1, lastInserted);
					stmt.setInt(2, servicesList[i]);
					stmt.setInt(3, valuesList[i]);
					stmt.setLong(4, totalValue);
					stmt.execute();
				}
				
				totalBill += totalValue;
			}
			stmt = con.prepareStatement(updateBill);
			stmt.setLong(1,totalBill);
			stmt.setInt(2, lastInserted);
			stmt.executeUpdate();
			
			printBill(String.valueOf(totalBill));
			response = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resultSet.close();
			stmt.close();			
			con.close();
		}
		return response;
	}

	public String getActualDate() {
		String date = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		date = dtf.format(localDate);
		
		return date;
	}

	public int getLasInsertedBillNumber() throws SQLException {
		con = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		int n = 0;
		String query = "SELECT MAX(BILL_NUMBER) FROM BILL";
		try {
			con = DBConnection.createConnection();
			stmt = con.prepareStatement(query);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				n = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			resultSet.close();
			con.close();
		}
		
		return n;
	}

	public void watchBill(String totalPriceLiteral, String BillNumberFactura) throws SQLException {
		JasperReport report;
		JasperPrint report_view;
		try {
			URL in = this.getClass().getResource("Bill_Report.jasper");
			report = (JasperReport) JRLoader.loadObject(in);
			Map<String, Object> parameters = new HashMap<>();
			parameters.clear();
			parameters.put("TOTALPRICELITERAL", totalPriceLiteral);
			parameters.put("BillNumberFactura", BillNumberFactura);
			con = DBConnection.createConnection();
			report_view = JasperFillManager.fillReport(report, parameters, con);
			JasperViewer.viewReport(report_view, false);
		} catch (JRException E) {
			E.printStackTrace();
		} finally {
			con.close();
		}
	}

	public boolean validateBill(int billNumber) throws SQLException {
		boolean response = false;
		con = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String query = "SELECT BILL_NUMBER FROM BILL WHERE BILL_NUMBER = ?";
		try {
			con = DBConnection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, billNumber);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				response = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			resultSet.close();
			con.close();
		}
		
		return response;
	}

	public void printBill(String totalPrice) throws SQLException {
		watchBill(NumberToString.Convertir(totalPrice, true), String.valueOf(getLasInsertedBillNumber()));
	}

	public String getBillPrice(int billNumber) throws SQLException {
		con = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String total = "";
		String query = "SELECT (TOTAL_PRICE - (TOTAL_PRICE * (DISCOUNT/100))) FROM BILL WHERE BILL_NUMBER = ?";
		try {
			con = DBConnection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, billNumber);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				total = String.valueOf(resultSet.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			resultSet.close();
			con.close();
		}
		
		return total;
	}

	public void printByBillNumber(String price, String bill) throws SQLException {
		watchBill(NumberToString.Convertir(price, true), bill);
	}
}