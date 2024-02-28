package com.hcl.jtl.reportservice.exception;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InvalidTokenExceptionTest {

	InvalidTokenException invalidTokenException;
	
	@Before
	public void setUp() throws Exception {
		String msg = "Message";
		invalidTokenException = new InvalidTokenException(msg);
	}

	@Test
	public void testInvalidTokenException() {
		assertEquals("Message", invalidTokenException.getMessage());
	}

}
