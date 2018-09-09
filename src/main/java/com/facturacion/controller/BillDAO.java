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

	public List<Bill> getBills(int idClient) {
		con = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<Bill> billList = new ArrayList<Bill>();
		String query = "SELECT * FROM BILL B, BILL_HAS_SERVICES BS, SERVICE S WHERE B.CLIENT_ID = ? AND BS.BILL_BILLNUMBER = B.BILL_NUMBER AND BS.SERVICE_CODE = S.CODE";
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
			con.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billList;
	}

	public boolean generateBill(int[] servicesList, int[] valuesList, long idCliente) throws SQLException {
		con = null;
		boolean response = false;
		int exec = 0;
		PreparedStatement stmt = null;
		CallableStatement cstmt = null;
		String totalPrice = "";
		String query = "INSERT INTO BILL (DATE, CLIENT_ID) VALUES (?,?)";
		String procedure = "{CALL SP_GENERATE_BILL(?,?,?,?)}";

		try {
			con = DBConnection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, getActualDate());
			stmt.setLong(2, idCliente);
			stmt.execute();
			int lastInserted = getLasInsertedBillNumber();
			stmt.close();
			con.close();

			for (int i = 0; i < servicesList.length; i++) {
				con = DBConnection.createConnection();
				cstmt = con.prepareCall(procedure);
				cstmt.setLong(1, idCliente);
				cstmt.setInt(2, lastInserted);
				cstmt.setInt(3, servicesList[i]);
				cstmt.setInt(4, valuesList[i]);
				exec = cstmt.executeUpdate();
				con.close();
				cstmt.close();
			}
			if (exec == 1) {
				String sql = "{CALL SP_GET_TOTAL_FACTURA(?,?)}"; 
				con = DBConnection.createConnection();
				cstmt = con.prepareCall(sql);
				cstmt.setInt(1, lastInserted);
				cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
				cstmt.executeUpdate();
				totalPrice = String.valueOf(cstmt.getInt(2));

				con.close();
				cstmt.close();
				printBill(totalPrice);
				response = true;
			} else {
				JOptionPane.showMessageDialog(null, "Ocurrió un error generando la factura. Intente de nuevo.");
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public int getLasInsertedBillNumber() {
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
			con.close();
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

	public void watchBill(String totalPriceLiteral, String BillNumberFactura) {
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
			con.close();
		} catch (JRException E) {
			E.printStackTrace();
		} catch (SQLException E) {
			E.printStackTrace();
		}
	}

	public boolean validateBill(int billNumber) {
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
			con.close();
			stmt.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public void printBill(String totalPrice) {
		watchBill(NumberToString.Convertir(totalPrice, true), String.valueOf(getLasInsertedBillNumber()));
	}

	public String getBillPrice(int billNumber) {
		con = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		String total = "";
		String query = "SELECT TOTAL_PRICE FROM BILL WHERE BILL_NUMBER = ?";
		try {
			con = DBConnection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setInt(1, billNumber);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				total = String.valueOf(resultSet.getInt(1));
			}
			con.close();
			stmt.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	public void printByBillNumber(String price, String bill) {
		watchBill(NumberToString.Convertir(price, true), bill);
	}
}