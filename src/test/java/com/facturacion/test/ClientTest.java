package com.facturacion.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.facturacion.model.Client;
import com.facturacion.test.mock.ClientMock;

public class ClientTest {

	@Test
	public void insertNewClient() {
		Client c = new Client();
		ClientMock cMock = new ClientMock();
		
		c.setId(1);
		c.setAddress("La floresta");
		c.setName("Fernando");
		c.setPhone(5234890);
		c.setMail("client@mail.com");
	
		assertTrue(cMock.insertClient(c));
	}

}
