package com.facturacion.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.JOptionPane;

import com.facturacion.model.Client;

public class ClientDAO {

	private Connection con;

	public void insertClient(Client client) throws SQLException {
		con = null;
		int exec = 0;
		String query = "{CALL SP_INSERT_CLIENT(?,?,?,?,?)}";
		try {
			con = DBConnection.createConnection();
			CallableStatement cstmt = con.prepareCall(query);

			cstmt.setLong(1, client.getId());
			cstmt.setString(2, client.getName());
			cstmt.setInt(3, client.getPhone());
			cstmt.setString(4, client.getMail());
			cstmt.setString(5, client.getAddress());
			exec = cstmt.executeUpdate();
			if (exec == 1)
				JOptionPane.showMessageDialog(null, "Cliente ingresado satisfactoriamente.");
			else
				JOptionPane.showMessageDialog(null,
						"El cliente con identificación " + client.getId() + " Ya está registrado en el sistema");
			con.close();
			cstmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Ocurrio un error insertando el cliente en la base de datos " + "(" + e.getMessage() + ")", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Client getClient(Long id){
		String query = "{ CALL SP_GET_CLIENT (?,?,?,?,?)";
		con = null;
		Client client = new Client();
		try{
			con = DBConnection.createConnection();
			CallableStatement cstmt = con.prepareCall(query);
			
			cstmt.setLong(1, id);		
			cstmt.registerOutParameter(2, Types.BIGINT);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.registerOutParameter(5, Types.VARCHAR);	
			cstmt.registerOutParameter(6, Types.VARCHAR);
			
			cstmt.executeUpdate();
			
			if(cstmt.getResultSet().next()){
				client.setId(cstmt.getLong(1));
				client.setName(cstmt.getString(2));
				client.setPhone(cstmt.getInt(3));
				client.setMail(cstmt.getString(4));
				client.setAddress(cstmt.getString(5));
				
				con.close();
				cstmt.close();
			}
			else{
				JOptionPane.showMessageDialog(null, "El cliente que está buscando no se encuentra registrado. "+"("+id+")", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error consultando datos del cliente. "+ "(" + e.getMessage() + ")", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return client;
	}
	
	public long searchClientById(long id){
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
			con.close();
			statement.close();
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return userId;
	}
}