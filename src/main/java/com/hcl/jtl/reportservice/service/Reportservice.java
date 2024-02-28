package com.hcl.jtl.reportservice.service;

import java.util.List;

import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;

public interface Reportservice {

	public List<StockTradeDetailsDto> getWeeklyReport() ;
	
	public List<StockTradeDetailsDto> getDailyReport();
}
