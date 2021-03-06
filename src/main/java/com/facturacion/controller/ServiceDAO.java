package com.facturacion.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.facturacion.model.Service;

public class ServiceDAO {
	
	private Connection con;
	
	public List<Service> getServices() throws SQLException{
		List<Service> listaServicios = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM SERVICE ORDER BY CODE ASC";
		try{
			con = DBConnection.createConnection();
			statement = con.prepareStatement(query);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				Service s = new Service();
				s.setCode(resultSet.getInt(1));
				s.setName(resultSet.getString(2));
				s.setDescription(resultSet.getString(4));
				s.setPrice(resultSet.getInt(3));
				
				listaServicios.add(s);
			}
		}catch(SQLException e){
			e.printStackTrace();
		} finally {
			statement.close();
			resultSet.close();
			con.close();
		}
		return listaServicios;
	}
}
