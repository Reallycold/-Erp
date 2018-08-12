package com.itcast.erp.dao;

import java.util.List;

import com.itcast.erp.entity.Storealert;
import com.itcast.erp.entity.Storedetail;
/**
 * 仓库库存数据访问接口
 * @author Administrator
 *
 */
public interface IStoredetailDao extends IBaseDao<Storedetail>{
	/**
	 * 获取库存预警列表
	 */
	public List<Storealert> getStorealertList();
}
