package com.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;

import org.hibernate.criterion.Restrictions;

import com.itcast.erp.dao.IOrderdetailDao;
import com.itcast.erp.entity.Orderdetail;
/**
 * 订单明细数据访问类
 * @author Administrator
 *
 */
public class OrderdetailDao extends BaseDao<Orderdetail> implements IOrderdetailDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Orderdetail orderdetail1,Orderdetail orderdetail2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Orderdetail.class);
		if(orderdetail1!=null){
			if(null != orderdetail1.getGoodsname() && orderdetail1.getGoodsname().trim().length()>0){
				dc.add(Restrictions.eq("goodsname", orderdetail1.getGoodsname()));
			}
			if(null != orderdetail1.getState() && orderdetail1.getState().trim().length()>0){
				dc.add(Restrictions.eq("state", orderdetail1.getState()));
			}
			/**
			 * 查询条件有问题  我写的是state 
			 * 实际上应该是orders
			 * 三天了 困扰了老子
			 * 报错是hibernate ---定位到 dao  ---connot cast to java String  getCount(BaseDao) 
			 * 这里是 DetailDao出错   将记住
			 */
			if(null != orderdetail1.getOrders() && null!=orderdetail1.getOrders().getUuid()){
				dc.add(Restrictions.eq("orders", orderdetail1.getOrders()));
			}

		}
		return dc;
	}

}
