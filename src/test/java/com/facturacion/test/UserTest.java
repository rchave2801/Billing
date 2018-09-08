package com.facturacion.test;

import org.junit.Assert;
import org.junit.Test;

import com.facturacion.model.User;
import com.facturacion.test.mock.UserMock;

public class UserTest {

	@Test
	public void loginTest() {
		UserMock uMock= new UserMock();
		User u = uMock.getUser();
		Assert.assertTrue(uMock.loginUser(u));
	}
	
	@Test
	public void loginTestFail(){
		UserMock uMock = new UserMock();
		User u = new User();
		u.setUsername("pepito");
		u.setPassword("abc");
		Assert.assertFalse(uMock.loginUser(u));
	}
}