package com.hcl.jtl.reportservice.controller;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;
import com.hcl.jtl.reportservice.exception.InvalidTokenException;
import com.hcl.jtl.reportservice.service.Reportservice;
import com.hcl.jtl.reportservice.service.feign.StockTradeService;
import com.hcl.jtl.reportservice.service.feign.UserService;
import com.hcl.jtl.reportservice.util.TokenValidationRequest;

import feign.FeignException;

@RunWith(SpringRunner.class)
public class ReportControllerTest {

	public final String bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGFsaXN0YWFAZ21haWwuY29tIiwiZXhwIjoxNzA5MTMzNTEyLCJpYXQiOjE3MDkwOTc1MTJ9.TeGZidSSDO0UOvDYlQPiNNWZP2J2fqsT4Wj2h1CmejI";
	
	ReportController reportController;
	
	@Mock
	StockTradeService stockTradeService;

	@Mock
	UserService userService;

	@Mock
	Reportservice reportservice;
	
	@Before
	public void setUp() throws Exception {
		reportController = new ReportController();
		ReflectionTestUtils.setField(reportController, "userService", userService);
		ReflectionTestUtils.setField(reportController, "stockTradeService", stockTradeService);
		ReflectionTestUtils.setField(reportController, "reportservice", reportservice);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSayWelcome() throws InvalidTokenException {
		ResponseEntity<Boolean> valueTrue = ResponseEntity.ok(true);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueTrue);
		ResponseEntity<String> response = reportController.sayWelcome(bearerToken);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Welcome to Microservice Capstone Project for Report Service", response.getBody());
		
		String wrongToken = "Bearer JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGFsaXN0YWFAZ21haWwuY29tIiwiZXhwIjoxNzA5MTMzNTEyLCJpYXQiOjE3MDkwOTc1MTJ9.TeGZidSSDO0UOvDYlQPiNNWZP2J2fqsT4Wj2h1CmejI";
		ResponseEntity<Boolean> valueFalse = ResponseEntity.ok(false);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueFalse);
		ResponseEntity<String> response1 = reportController.sayWelcome(wrongToken);
		assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
		assertEquals(401, response1.getStatusCodeValue());
		
		String unauthorizedToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmFpbkBnbWFpbC5jb20iLCJleHAiOjE3MDkwODEzMDEsImlhdCI6MTcwOTA0NTMwMX0.jnWlWKGGNfzueU980V9ltm0xKOcF1MlRMQSgZ_18NnU";
		TokenValidationRequest tokenValidationRequest =  new TokenValidationRequest();
		tokenValidationRequest.setToken(unauthorizedToken);
		Mockito.when(userService.validate(any())).thenThrow(FeignException.class);
		ResponseEntity<String> response2 = reportController.sayWelcome(unauthorizedToken);
		assertEquals("Unauthorized Access", response2.getBody());
		assertEquals(401, response2.getStatusCodeValue());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetAllStocks() throws InvalidTokenException {
		ResponseEntity<Boolean> valueTrue = ResponseEntity.ok(true);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueTrue);
		Mockito.when(stockTradeService.getStockDetails()).thenReturn(new ArrayList<StockTradeDetailsDto>());
		ResponseEntity<List<StockTradeDetailsDto>> response = reportController.getAllStocks(bearerToken);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		
		String wrongToken = "Bearer JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGFsaXN0YWFAZ21haWwuY29tIiwiZXhwIjoxNzA5MTMzNTEyLCJpYXQiOjE3MDkwOTc1MTJ9.TeGZidSSDO0UOvDYlQPiNNWZP2J2fqsT4Wj2h1CmejI";
		ResponseEntity<Boolean> valueFalse = ResponseEntity.ok(false);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueFalse);
		ResponseEntity<List<StockTradeDetailsDto>> response1 = reportController.getAllStocks(wrongToken);
		assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
		assertEquals(401, response1.getStatusCodeValue());
		
		String unauthorizedToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmFpbkBnbWFpbC5jb20iLCJleHAiOjE3MDkwODEzMDEsImlhdCI6MTcwOTA0NTMwMX0.jnWlWKGGNfzueU980V9ltm0xKOcF1MlRMQSgZ_18NnU";
		TokenValidationRequest tokenValidationRequest =  new TokenValidationRequest();
		tokenValidationRequest.setToken(unauthorizedToken);
		Mockito.when(userService.validate(any())).thenThrow(FeignException.class);
		ResponseEntity<List<StockTradeDetailsDto>> response2 = reportController.getAllStocks(unauthorizedToken);
		assertNull(response2.getBody());
		assertEquals(401, response2.getStatusCodeValue());
		
	}

	@Test
	public void testGetWeeklyReport() throws InvalidTokenException {
		ResponseEntity<Boolean> valueTrue = ResponseEntity.ok(true);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueTrue);
		Mockito.when(reportservice.getWeeklyReport()).thenReturn(new ArrayList<StockTradeDetailsDto>());
		ResponseEntity<List<StockTradeDetailsDto>> response = reportController.getWeeklyReport(bearerToken);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		
		String wrongToken = "Bearer JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGFsaXN0YWFAZ21haWwuY29tIiwiZXhwIjoxNzA5MTMzNTEyLCJpYXQiOjE3MDkwOTc1MTJ9.TeGZidSSDO0UOvDYlQPiNNWZP2J2fqsT4Wj2h1CmejI";
		ResponseEntity<Boolean> valueFalse = ResponseEntity.ok(false);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueFalse);
		ResponseEntity<List<StockTradeDetailsDto>> response1 = reportController.getWeeklyReport(wrongToken);
		assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
		assertEquals(401, response1.getStatusCodeValue());
		
		String unauthorizedToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmFpbkBnbWFpbC5jb20iLCJleHAiOjE3MDkwODEzMDEsImlhdCI6MTcwOTA0NTMwMX0.jnWlWKGGNfzueU980V9ltm0xKOcF1MlRMQSgZ_18NnU";
		TokenValidationRequest tokenValidationRequest =  new TokenValidationRequest();
		tokenValidationRequest.setToken(unauthorizedToken);
		Mockito.when(userService.validate(any())).thenThrow(FeignException.class);
		ResponseEntity<List<StockTradeDetailsDto>> response2 = reportController.getWeeklyReport(unauthorizedToken);
		assertNull(response2.getBody());
		assertEquals(401, response2.getStatusCodeValue());
	}

	@Test
	public void testGetDailyReport() throws InvalidTokenException {
		ResponseEntity<Boolean> valueTrue = ResponseEntity.ok(true);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueTrue);
		Mockito.when(reportservice.getDailyReport()).thenReturn(new ArrayList<StockTradeDetailsDto>());
		ResponseEntity<List<StockTradeDetailsDto>> response = reportController.getDailyReport(bearerToken);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		
		String wrongToken = "Bearer JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZGFsaXN0YWFAZ21haWwuY29tIiwiZXhwIjoxNzA5MTMzNTEyLCJpYXQiOjE3MDkwOTc1MTJ9.TeGZidSSDO0UOvDYlQPiNNWZP2J2fqsT4Wj2h1CmejI";
		ResponseEntity<Boolean> valueFalse = ResponseEntity.ok(false);
		Mockito.when(userService.validate(ArgumentMatchers.any())).thenReturn(valueFalse);
		ResponseEntity<List<StockTradeDetailsDto>> response1 = reportController.getDailyReport(wrongToken);
		assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
		assertEquals(401, response1.getStatusCodeValue());
		
		String unauthorizedToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmFpbkBnbWFpbC5jb20iLCJleHAiOjE3MDkwODEzMDEsImlhdCI6MTcwOTA0NTMwMX0.jnWlWKGGNfzueU980V9ltm0xKOcF1MlRMQSgZ_18NnU";
		TokenValidationRequest tokenValidationRequest =  new TokenValidationRequest();
		tokenValidationRequest.setToken(unauthorizedToken);
		Mockito.when(userService.validate(any())).thenThrow(FeignException.class);
		ResponseEntity<List<StockTradeDetailsDto>> response2 = reportController.getDailyReport(unauthorizedToken);
		assertNull(response2.getBody());
		assertEquals(401, response2.getStatusCodeValue());
		
		
	}

}
