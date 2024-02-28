package com.hcl.jtl.reportservice.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockTradeDetailsDto {

	private int stockCode;
	private String stockName;
	private float dayOpen;
	private float dayHigh;
	private float dayLow;
	private float tradedPrice;
	private float previousClose;
	private Date tradedDateTime;	
}
