package com.itcast.erp.biz.impl;
import com.itcast.erp.biz.IReturnordersBiz;
import com.itcast.erp.dao.IReturnordersDao;
import com.itcast.erp.entity.Returnorders;
/**
 * 退货订单业务逻辑类
 * @author Administrator
 *
 */
public class ReturnordersBiz extends BaseBiz<Returnorders> implements IReturnordersBiz {

	private IReturnordersDao returnordersDao;
	
	public void setReturnordersDao(IReturnordersDao returnordersDao) {
		this.returnordersDao = returnordersDao;
		super.setBaseDao(this.returnordersDao);
	}
	
}
