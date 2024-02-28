package com.hcl.jtl.reportservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;
import com.hcl.jtl.reportservice.exception.InvalidTokenException;
import com.hcl.jtl.reportservice.service.Reportservice;
import com.hcl.jtl.reportservice.service.feign.StockTradeService;
import com.hcl.jtl.reportservice.service.feign.UserService;
import com.hcl.jtl.reportservice.util.TokenValidationRequest;

import feign.FeignException;

@RestController
@RequestMapping("/report")
public class ReportController {

	Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	StockTradeService stockTradeService;

	@Autowired
	UserService userService;

	@Autowired
	Reportservice reportservice;

	@GetMapping("/welcome")
	public ResponseEntity<String> sayWelcome(
			@RequestHeader(value = "Authorization", required = true) String bearerToken) {
		try {
			if (checkAuthorization(bearerToken).getStatusCode().equals(HttpStatus.OK))
				return new ResponseEntity<>("Welcome to Microservice Capstone Project for Report Service",
						HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (FeignException | InvalidTokenException e) {
			return new ResponseEntity<>("Unauthorized Access", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/stocks")
	public ResponseEntity<List<StockTradeDetailsDto>> getAllStocks(
			@RequestHeader(value = "Authorization", required = true) String bearerToken) {
		try {
			if (checkAuthorization(bearerToken).getStatusCode().equals(HttpStatus.OK))
				return new ResponseEntity<>(stockTradeService.getStockDetails(), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (FeignException | InvalidTokenException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/weekly")
	public ResponseEntity<List<StockTradeDetailsDto>> getWeeklyReport(
			@RequestHeader(value = "Authorization", required = true) String bearerToken) {
		try {
			if (checkAuthorization(bearerToken).getStatusCode().equals(HttpStatus.OK))
				return new ResponseEntity<>(reportservice.getWeeklyReport(), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (FeignException | InvalidTokenException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/daily")
	public ResponseEntity<List<StockTradeDetailsDto>> getDailyReport(
			@RequestHeader(value = "Authorization", required = true) String bearerToken) {
		try {
			if (checkAuthorization(bearerToken).getStatusCode().equals(HttpStatus.OK))
				return new ResponseEntity<>(reportservice.getDailyReport(), HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (FeignException | InvalidTokenException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	private ResponseEntity<HttpStatus> checkAuthorization(String bearerToken) throws InvalidTokenException {
		String token = null;
		if (bearerToken.contains("Bearer")) {
			token = bearerToken.substring(bearerToken.indexOf("Bearer ") + 7);
		}
		TokenValidationRequest tokenValidationRequest = new TokenValidationRequest();
		tokenValidationRequest.setToken(token);
		if (userService.validate(tokenValidationRequest).getBody().equals(true))
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}
}
