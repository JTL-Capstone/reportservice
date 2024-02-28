package com.hcl.jtl.reportservice.exception;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ErrorDetailsTest {
	
	@Mock
	ErrorDetails errorDetails;
	
	DateTimeFormatter dateTimeFormatter;

	@Before
	public void setUp() throws Exception {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime timestamp = LocalDateTime.parse("2024-02-28 19:25:27.000", dateTimeFormatter);
		String message = "Message";
		String description = "Description";
		errorDetails = new ErrorDetails(timestamp, message, description);
	}

	@Test
	public void testErrorDetails() {
		assertEquals(LocalDateTime.parse("2024-02-28 19:25:27.000", dateTimeFormatter), errorDetails.getTimestamp());
		assertEquals("Message", errorDetails.getMessage());
		assertEquals("Description", errorDetails.getDetails());
	}

}
