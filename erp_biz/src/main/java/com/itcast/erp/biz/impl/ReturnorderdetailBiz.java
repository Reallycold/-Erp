package com.itcast.erp.biz.impl;
import com.itcast.erp.biz.IReturnorderdetailBiz;
import com.itcast.erp.dao.IReturnorderdetailDao;
import com.itcast.erp.entity.Returnorderdetail;
/**
 * 退货订单明细业务逻辑类
 * @author Administrator
 *
 */
public class ReturnorderdetailBiz extends BaseBiz<Returnorderdetail> implements IReturnorderdetailBiz {

	private IReturnorderdetailDao returnorderdetailDao;
	
	public void setReturnorderdetailDao(IReturnorderdetailDao returnorderdetailDao) {
		this.returnorderdetailDao = returnorderdetailDao;
		super.setBaseDao(this.returnorderdetailDao);
	}
	
}
