package com.hcl.jtl.reportservice.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ServiceNotAvailableExceptionTest {
	
	ServiceNotAvailableException serviceNotAvailableException;

	@Before
	public void setUp() throws Exception {
		String msg = "Message";
		serviceNotAvailableException = new ServiceNotAvailableException(msg);
	}

	@Test
	public void testServiceNotAvailableException() {
		assertEquals("Message", serviceNotAvailableException.getMessage());
	}

}
