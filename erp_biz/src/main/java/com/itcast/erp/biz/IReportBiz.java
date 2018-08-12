package com.itcast.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReportBiz {
	/**
	 * 销售报表统计
	 */
	public List orderReport(Date startDate, Date endDate);
	
	/**
	 * 销售趋势图
	 */

	public List<Map<String, Object>> trendReport(int year);
}
