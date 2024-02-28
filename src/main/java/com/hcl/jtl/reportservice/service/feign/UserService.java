package com.hcl.jtl.reportservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hcl.jtl.reportservice.config.FeignConfig;
import com.hcl.jtl.reportservice.exception.InvalidTokenException;
import com.hcl.jtl.reportservice.serviceImpl.feign.FallbackStockTradeService;
import com.hcl.jtl.reportservice.util.TokenValidationRequest;

@Service
@FeignClient(name = "authserver", contextId = "authserver", configuration = FeignConfig.class, fallback = FallbackStockTradeService.class)
public interface UserService {

	@PostMapping(path ="authserver/auth/validate")
    ResponseEntity<Boolean> validate(@RequestBody TokenValidationRequest tokenValidationRequest) throws InvalidTokenException;
}
