package com.itcast.erp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReportDao {
	/**
	 * 销售统计报表
	 */
	public List orderReport(Date startDate,Date endDate);
	
	/**
	 * 统计某年中每个月的金额
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> getSumMoney(int year);
		
}
