package com.itcast.erp.action;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.itcast.erp.biz.IOrdersBiz;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Orderdetail;
import com.itcast.erp.entity.Orders;
import com.itcast.erp.exception.ErpException;
import com.redsum.bos.ws.Waybilldetail;
import com.redsum.bos.ws.impl.IWaybillWs;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	//前端提交过来的商品列表  转成json字符串
	private String json;
	private IOrdersBiz ordersBiz;
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}

	public void add(){
		Emp loginUser=getLoginUser();
		//先检验用户是否登录
		if(null == loginUser){
			ajaxReturn(false, "亲，您还没有登录");
			return;
		}
		try {
			//获取提交的订单
			Orders orders=getT();
			//设置订单的创建者
			orders.setCreater(loginUser.getUuid());
			//设置订单的明细
			List<Orderdetail> orderDetailList=JSON.parseArray(json, Orderdetail.class);
			orders.setOrderDetails(orderDetailList);
			//保存订单
			ordersBiz.add(orders);
			ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "添加订单失败");
		}
	}
	
	public void doCheck(){
		Emp loginUser=getLoginUser();
		if(null == loginUser){
			ajaxReturn(false, "请先登录");
			return;
		}
		try {
			ordersBiz.doCheck(getId(), loginUser.getUuid());
			ajaxReturn(true, "审核成功");
		}catch(ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "审核失败");
		}
	}
	
	public void doStart(){
		Emp loginUser=getLoginUser();
		if(null == loginUser){
			ajaxReturn(false, "请先登录");
			return;
		}
		try {
			ordersBiz.doStart(getId(), loginUser.getUuid());
			ajaxReturn(true, "确认成功");
		}catch(ErpException e){
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "确认失败");
		}
	}
	
	
	/**
	 * 我的订单
	 */
	public void myListByPage(){
		if(null==getT1()){
			setT1(new Orders());
		}
		Emp loginUser=getLoginUser();
		//设置订单的创建者
		getT1().setCreater(loginUser.getUuid());
		super.listByPage();
	}
	
	public void export(){
		String filename="Orders_"+getId()+".xls";
		HttpServletResponse response=ServletActionContext.getResponse();
		try {
			response.setHeader("Content-Disposition", "attachment;filename="+
					new String(filename.getBytes(),"ISO-8859-1"));
			ordersBiz.exportById(response.getOutputStream(), getId());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}
	
	private Long waybillsn;
	public Long getWaybillsn() {
		return waybillsn;
	}
	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}
	
	/**
	 * 根据运单号查询运单详情
	 */
	public void waybilldetailList(){
		List<Waybilldetail> waybilldetailList = ordersBiz.waybilldetailList(waybillsn);
		write(JSON.toJSONString(waybilldetailList));
	}
	
}














