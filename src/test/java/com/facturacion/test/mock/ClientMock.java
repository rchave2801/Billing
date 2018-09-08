package com.facturacion.test.mock;

import com.facturacion.model.Client;

public class ClientMock {

	public boolean insertClient(Client c) {
		if(c.getId() != 0 && !c.getName().isEmpty())
			return true;
		else
			return false;
	}

}
