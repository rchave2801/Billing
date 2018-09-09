package com.facturacion.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	
	private Connection con;
	
	public boolean loginUser(String username, String password){
		boolean response = false;
		String query = "SELECT ID FROM USER WHERE USER_NAME = ? AND PASSWORD = ?";
		con = DBConnection.createConnection(); 
		try{
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				response = true;
			}
			statement.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return response;
	}
}
