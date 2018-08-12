package com.itcast.erp.biz.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itcast.erp.biz.IStoreoperBiz;
import com.itcast.erp.dao.IEmpDao;
import com.itcast.erp.dao.IGoodsDao;
import com.itcast.erp.dao.IStoreDao;
import com.itcast.erp.dao.IStoreoperDao;
import com.itcast.erp.entity.Storeoper;
/**
 * 仓库操作记录业务逻辑类
 * @author Administrator
 *
 */
public class StoreoperBiz extends BaseBiz<Storeoper> implements IStoreoperBiz {

	private IStoreoperDao storeoperDao;
	private IEmpDao empDao;
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;
	
	
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
		super.setBaseDao(this.storeoperDao);
	}

	/**
	 * 重写分页方法
	 */
	@Override
	public List<Storeoper> getListByPage(Storeoper t1, Storeoper t2, Object param, int firstResult, int maxResults) {
		List<Storeoper> logList=super.getListByPage(t1, t2, param, firstResult, maxResults);
		//缓存 员工名称
		Map<Long,String> empNameMap=new HashMap<Long,String>();
		//缓存仓库名称
		Map<Long,String> storeNameMap=new HashMap<Long,String>();
		//缓存商品名称
		Map<Long,String> goodsNameMap=new HashMap<Long,String>();
		
		for(Storeoper log : logList){
			log.setEmpName(getEmpName(log.getEmpuuid(),empNameMap));
			log.setGoodsName(getGoodsName(log.getGoodsuuid(),goodsNameMap));
			log.setStoreName(getStoreName(log.getStoreuuid(),storeNameMap));
		}
		return super.getListByPage(t1, t2, param, firstResult, maxResults);
	}

	private String getEmpName(Long uuid, Map<Long,String> empNameMap) {
		String name=empNameMap.get(uuid);
		if(null ==name){
			name=empDao.get(uuid).getName();
			empNameMap.put(uuid, name);
		}
		return name;
	}
	
	private String getGoodsName(Long uuid, Map<Long,String> goodsNameMap) {
		String name=goodsNameMap.get(uuid);
		if(null ==name){
			name=empDao.get(uuid).getName();
			goodsNameMap.put(uuid, name);
		}
		return name;
	}
	
	private String getStoreName(Long uuid, Map<Long,String> storeNameMap) {
		String name=storeNameMap.get(uuid);
		if(null ==name){
			name=empDao.get(uuid).getName();
			storeNameMap.put(uuid, name);
		}
		return name;
	}
	
}
