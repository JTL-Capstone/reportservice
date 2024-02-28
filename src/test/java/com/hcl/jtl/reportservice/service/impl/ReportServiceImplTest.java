package com.hcl.jtl.reportservice.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyList;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.hcl.jtl.reportservice.dto.StockTradeDetailsDto;
import com.hcl.jtl.reportservice.service.Reportservice;
import com.hcl.jtl.reportservice.service.feign.StockTradeService;

public class ReportServiceImplTest {

	ReportServiceImpl reportService;

	StockTradeService stockTradeServiceMock;

	@Before
	public void setUp() throws Exception {
		stockTradeServiceMock = Mockito.mock(StockTradeService.class);
		reportService = new ReportServiceImpl();
		ReflectionTestUtils.setField(reportService, "stockTradeService", stockTradeServiceMock);
	}

	@Test
	public void testGetWeeklyReport() {
		List<StockTradeDetailsDto> stockDetailsList = new ArrayList<>();
		stockDetailsList.add(new StockTradeDetailsDto(532281, "HCL TECHNO", 1489.85f, 1489.85f, 1461.75f, 1461.75f,
				1483.15f, Date.valueOf(LocalDate.now())));
		stockDetailsList.add(new StockTradeDetailsDto(532282, "ABC TECHNO", 1489.85f, 1489.85f, 1461.75f, 1461.75f,
				1483.15f, Date.valueOf("2024-02-22")));
		Mockito.when(stockTradeServiceMock.getStockDetails()).thenReturn(stockDetailsList);
		List<StockTradeDetailsDto> result = reportService.getWeeklyReport();
		assertEquals(stockDetailsList, result);

	}

	@Test
	public void testGetDailyReport() {
		List<StockTradeDetailsDto> stockDetailsList = new ArrayList<>();
		stockDetailsList.add(new StockTradeDetailsDto(532281, "HCL TECHNO", 1489.85f, 1489.85f, 1461.75f, 1461.75f,
				1483.15f, Date.valueOf(LocalDate.now())));
		Mockito.when(stockTradeServiceMock.getStockDetails()).thenReturn(stockDetailsList);
		List<StockTradeDetailsDto> result = reportService.getDailyReport();
		assertEquals(stockDetailsList, result);
		
		List<StockTradeDetailsDto> stockDetailsList1 = new ArrayList<>();
		stockDetailsList1.add(new StockTradeDetailsDto(532282, "ABC TECHNO", 1489.85f, 1489.85f, 1461.75f, 1461.75f,
				1483.15f, Date.valueOf("2024-02-22")));
		Mockito.when(stockTradeServiceMock.getStockDetails()).thenReturn(stockDetailsList1);
		List<StockTradeDetailsDto> result1 = reportService.getDailyReport();
		assertEquals(stockDetailsList1, result1);

	}

}
