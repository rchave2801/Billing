package com.facturacion.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.facturacion.model.Client;

public class ClientDAO {

	private Connection con;

	public void insertClient(Client client) throws SQLException {
		int exec = 0;
		String query = "INSERT INTO CLIENT (ID,NAME,ADDRESS,PHONE,EMAIL) VALUES (?,?,?,?,?)";
		con = DBConnection.createConnection();
		PreparedStatement cstmt = null;
		try {
			cstmt = con.prepareStatement(query);
			cstmt.setLong(1, client.getId());
			cstmt.setString(2, client.getName());
			cstmt.setInt(4, client.getPhone());
			cstmt.setString(5, client.getMail());
			cstmt.setString(3, client.getAddress());
			exec = cstmt.executeUpdate();
			if (exec == 1) {
				JOptionPane.showMessageDialog(null, "Cliente ingresado satisfactoriamente.");	
			}
		} catch (SQLException e) {
			if (e.getSQLState().startsWith("23")) {
				JOptionPane.showMessageDialog(null,
						"Ya existe un cliente con identificación " + client.getId() + " registrado en el sistema");
          } else {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error insertando el cliente en la base de datos " + "(" + e.getMessage() + ")", "Error",
					JOptionPane.ERROR_MESSAGE);
          }
		}  finally {
			cstmt.close();
			con.close();
		}
	}
	
	public Client getClient(Long id) throws SQLException{
		String query = "SELECT * FROM CLIENT WHERE ID = ?";	
		con = DBConnection.createConnection();
		PreparedStatement cstmt = null;
		Client client = new Client();
		ResultSet resultSet = null;
		try{
			cstmt = con.prepareCall(query);		
			cstmt.setLong(1, id);					
			resultSet = cstmt.executeQuery();
			
			if(resultSet.next()){
				client.setId(resultSet.getLong(1));
				client.setName(resultSet.getString(2));
				client.setPhone(resultSet.getInt(4));
				client.setMail(resultSet.getString(5));
				client.setAddress(resultSet.getString(3));			
			}
			else{
				JOptionPane.showMessageDialog(null, "El cliente que está buscando no se encuentra registrado. "+"("+id+")", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error consultando datos del cliente. "+ "(" + e.getMessage() + ")", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			cstmt.close();
			resultSet.close();
			con.close();

		}
		return client;
	}
	
	public long searchClientById(long id) throws SQLException{
		long userId= 0;
		con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT ID FROM CLIENT WHERE ID = ?";
		try{
			con = DBConnection.createConnection();
			statement = con.prepareStatement(query);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				userId=resultSet.getLong("Id");
			}
		}catch(SQLException e){
			e.printStackTrace();
		} finally {
			con.close();
			statement.close();
			resultSet.close();
		}
		return userId;
	}
}