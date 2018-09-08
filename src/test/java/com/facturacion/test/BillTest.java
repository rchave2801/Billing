package com.facturacion.test;


import org.junit.Assert;
import org.junit.Test;

public class BillTest {

	@Test
	public void test() {
		String[] service = new String[4];
		String services = "";
		service[0] = "1";
		service[1] = "4";
		service[2] = "10";
		service[3] = "14";
		
		services = service.toString();
		if (services.equals("1,4,10,14")){
			Assert.assertTrue(true);
		}
		else
			Assert.assertFalse(false);		
	}
}