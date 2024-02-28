package com.hcl.jtl.reportservice.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;
import com.hcl.jtl.reportservice.service.Reportservice;
import com.hcl.jtl.reportservice.service.feign.StockTradeService;

@Service
public class ReportServiceImpl implements Reportservice {

	@Autowired
	StockTradeService stockTradeService;

	@Override
	public List<StockTradeDetailsDto> getWeeklyReport() {
		List<StockTradeDetailsDto> totalStockTradeList = stockTradeService.getStockDetails();
		List<StockTradeDetailsDto> weeklyReportList = new ArrayList<StockTradeDetailsDto>();
		LocalDate date = LocalDate.now();
		System.out.println("date --> " + date);
		LocalDate lastDate = date.minusDays(7);
		System.out.println("lastDate --> " + lastDate);
		for (StockTradeDetailsDto stockTradeDetails : totalStockTradeList) {
			if (Date.valueOf(stockTradeDetails.getTradedDateTime().toString()).equals(Date.valueOf(LocalDate.now()))
					|| Date.valueOf(stockTradeDetails.getTradedDateTime().toString()).after(Date.valueOf(lastDate))) {
				weeklyReportList.add(stockTradeDetails);
			}
		}
		System.out.println("weeklyReportList --> " + weeklyReportList);
		return weeklyReportList;
	}

	@Override
	public List<StockTradeDetailsDto> getDailyReport() {
		List<StockTradeDetailsDto> totalStockTradeList = stockTradeService.getStockDetails();
		String lastUploadedDate = getLastUploadedDate(totalStockTradeList);
		List<StockTradeDetailsDto> dailyReportList = new ArrayList<StockTradeDetailsDto>();
		for (StockTradeDetailsDto stockTradeDetails : totalStockTradeList) {
			if (Date.valueOf(stockTradeDetails.getTradedDateTime().toString()).equals(Date.valueOf(LocalDate.now()))) {
				dailyReportList.add(stockTradeDetails);
			} else if (Date.valueOf(stockTradeDetails.getTradedDateTime().toString())
					.equals(Date.valueOf(lastUploadedDate))) {
				dailyReportList.add(stockTradeDetails);
			}
		}
		System.out.println("dailyReportList --> " + dailyReportList);
		return dailyReportList;
	}

	private String getLastUploadedDate(List<StockTradeDetailsDto> stockTradeDetailsList) {
		Set<Date> dateSet = new HashSet<Date>();
		for (StockTradeDetailsDto stockTradeDetails : stockTradeDetailsList) {
			dateSet.add(Date.valueOf(stockTradeDetails.getTradedDateTime().toString()));
		}
		List<Date> list = new ArrayList<Date>(dateSet);
		Collections.sort(list);
		return list.get(list.size() - 1).toString();
	}

}
