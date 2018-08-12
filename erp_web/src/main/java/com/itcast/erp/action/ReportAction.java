package com.itcast.erp.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.itcast.erp.biz.IReportBiz;

public class ReportAction {
	private Date startDate;
	private Date endDate;
	private IReportBiz reportBiz;
	
	private int year;
	
	
	public void setYear(int year) {
		this.year = year;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}
	
	public void orderReport(){
		List reportData=reportBiz.orderReport(startDate, endDate);
		write(JSON.toJSONString(reportData));
	}
	
	/**
	 * 销售趋势表
	 * @param mapString
	 */
	public void trendReport(){
		List<Map<String,Object>> reportData=reportBiz.trendReport(year);
		write(JSON.toJSONString(reportData));
	}
	
	public void write(String mapString){
		//返回对象
		HttpServletResponse res=ServletActionContext.getResponse();
		res.setContentType("text/html; charset=utf-8");
		
		try {
			res.getWriter().write(mapString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
