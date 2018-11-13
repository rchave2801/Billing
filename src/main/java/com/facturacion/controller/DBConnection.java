package com.facturacion.controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static Connection connect = null;
	
	public static Connection createConnection(){
		
		String url = "jdbc:mysql://localhost:3308/BillingDB?useSSL=false";
		String username = "billingApp";
		String password = "billing_App";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return connect;
	}
}
