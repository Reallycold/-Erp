package com.itcast.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itcast.erp.dao.IReportDao;

public class ReportDao extends HibernateDaoSupport implements IReportDao{

	/**
	 * 销售报表统计
	 * 
	 * 注意 from + 类名  不是表名 Goods NOT goods,需要添加空格
	 *  多的一方 一的属性=  一的一方的别名
	 *  投影查询  注意括号
	 */
	public List orderReport(Date startDate, Date endDate) {
		String hql="select new Map(gt.name as name,sum(od.money) as y) "+
					"from Goodstype gt,Goods g,Orders o, Orderdetail od "+
		"where g.goodstype=gt  and  od.orders=o  and od.goodsuuid=g.uuid "
			+ "and o.type='2' ";
			
		
		List<Date> query=new ArrayList<Date>();
		if(null!=startDate){
			hql+= "and o.createtime>= ?";
			query.add(startDate);
		}
		if(null!=endDate){
			hql+= "and o.createtime<= ?";
			query.add(startDate);
		}
		hql += "group by gt.name";
		// support Date query
		if(query.size()>0){
			return getHibernateTemplate().find(hql, query.toArray(new Object[]{}));
		}
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 销售趋势图
	 * @param year
	 * @return
	 */
	public List<Map<String,Object>> getSumMoney(int year){
		String hql = "select new Map(month(o.createtime) as name,sum(ol.money) as y)"
				+ "from Orderdetail ol, Orders o "
				+ "where ol.orders=o "
				+ "and o.type='2' and year(o.createtime)=? "
				+ "group by month(o.createtime)";
		
		return (List<Map<String, Object>>) getHibernateTemplate().find(hql, year);
	}

}
