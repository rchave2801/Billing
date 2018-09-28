package com.facturacion.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DBConnection {
	private static Connection connect = null;
	private static Properties props = new Properties();
	private static InputStream input = null;
	
	public static Connection createConnection(){
		try {
		input = new FileInputStream("application.properties");
		props.load(input);
		} catch(IOException e) {
			e.printStackTrace();
		}	
		
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		return connect;
	}
}
