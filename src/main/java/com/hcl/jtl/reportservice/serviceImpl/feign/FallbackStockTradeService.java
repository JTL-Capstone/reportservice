package com.hcl.jtl.reportservice.serviceImpl.feign;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;
import com.hcl.jtl.reportservice.service.feign.StockTradeService;
import com.hcl.jtl.reportservice.service.feign.UserService;
import com.hcl.jtl.reportservice.util.TokenValidationRequest;

public class FallbackStockTradeService implements StockTradeService, UserService {

	@Override
	public List<StockTradeDetailsDto> getStockDetails() {
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Boolean> validate(TokenValidationRequest tokenValidationRequest) {
		return (ResponseEntity<Boolean>) Collections.EMPTY_MAP;
	}

}
