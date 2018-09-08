package com.facturacion.test.mock;

import com.facturacion.model.User;

public class UserMock {
	public User getUser(){
		User u = new User();
		u.setUsername("admin");
		u.setPassword("1234");
		
		return u;
	}
	
	public boolean loginUser(User user){
		if(user.getUsername().equals("admin") && user.getPassword().equals("1234")){
			return true;
		}
		else{
			return false;
		}
	}
}
