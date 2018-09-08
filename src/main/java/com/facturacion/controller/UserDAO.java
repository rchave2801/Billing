package com.facturacion.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	
	private Connection con;
	
	public boolean loginUser(String username, String password){
		con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean response = false;
		String query = "SELECT ID FROM USER WHERE USER_NAME = ? AND PASSWORD = ?";
		try{
			con = DBConnection.createConnection();
			statement = con.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				response = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return response;
	}
}
