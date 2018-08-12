package com.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.itcast.erp.dao.IStoredetailDao;
import com.itcast.erp.entity.Storealert;
import com.itcast.erp.entity.Storedetail;
/**
 * 仓库库存数据访问类
 * @author Administrator
 *
 */
public class StoredetailDao extends BaseDao<Storedetail> implements IStoredetailDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Storedetail storedetail1,Storedetail storedetail2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Storedetail.class);
		if(storedetail1!=null){
			//根据商品编辑查询
			if(null != storedetail1.getGoodsuuid()){
				dc.add(Restrictions.eq("goodsuuid", storedetail1.getGoodsuuid()));
			}
			//根据仓库去查
			if(null != storedetail1.getStoreuuid()){
				dc.add(Restrictions.eq("storeuuid", storedetail1.getStoreuuid()));
			}
			

		}
		return dc;
	}

	/**
	 * 获取库存预警列表
	 */
	public List<Storealert> getStorealertList() {
		String hql="from Storealert where storenum < outnum";
		return (List<Storealert>) getHibernateTemplate().find(hql);
	}

}
