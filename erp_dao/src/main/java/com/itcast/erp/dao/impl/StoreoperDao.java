package com.itcast.erp.dao.impl;
import java.util.Calendar;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itcast.erp.dao.IStoreoperDao;
import com.itcast.erp.entity.Storeoper;
/**
 * 仓库操作记录数据访问类
 * @author Administrator
 *
 */
public class StoreoperDao extends BaseDao<Storeoper> implements IStoreoperDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Storeoper storeoper1,Storeoper storeoper2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Storeoper.class);
		
		//添加日历
		Calendar car=Calendar.getInstance();
		
		if(storeoper1!=null){
			if(null != storeoper1.getType() && storeoper1.getType().trim().length()>0){
				dc.add(Restrictions.like("type", storeoper1.getType(), MatchMode.ANYWHERE));
			}
			//操作员编号
			if(null != storeoper1.getEmpuuid()){
				dc.add(Restrictions.eq("empuuid", storeoper1.getEmpuuid()));
			}
			//商品编号
			if(storeoper1.getGoodsuuid()!=null){
				dc.add(Restrictions.eq("goodsuuid", storeoper1.getGoodsuuid()));
			}
			//仓库编号
			if(storeoper1.getStoreuuid()!=null){
				dc.add(Restrictions.eq("storeuuid", storeoper1.getStoreuuid()));
			}
			//操作的起始时间
			if(storeoper1.getOpertime()!=null){
				car.setTime(storeoper1.getOpertime());
				car.set(Calendar.HOUR,0);
				car.set(Calendar.MINUTE,0);
				car.set(Calendar.SECOND,0);
				car.set(Calendar.MILLISECOND,0);
				dc.add(Restrictions.ge("opertime", car.getTime()));
			}
			
			if(null != storeoper2){
				//操作结束时间
				if(storeoper2.getOpertime()!=null){
					car.setTime(storeoper1.getOpertime());
					car.set(Calendar.HOUR,23);
					car.set(Calendar.MINUTE,59);
					car.set(Calendar.SECOND,59);
					car.set(Calendar.MILLISECOND,999);
					dc.add(Restrictions.le("opertime", car.getTime()));
				}
			}

		}
		return dc;
	}

}
