package com.hcl.jtl.reportservice.service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.hcl.jtl.reportservice.config.FeignConfig;
import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;
import com.hcl.jtl.reportservice.serviceImpl.feign.FallbackStockTradeService;

@Service
@FeignClient(name = "stocktradeservice", contextId = "stocktradeservice", configuration = FeignConfig.class, fallback = FallbackStockTradeService.class)
public interface StockTradeService {

	@GetMapping(path ="stocktradeservice/stocks")
	List<StockTradeDetailsDto>  getStockDetails();
}
