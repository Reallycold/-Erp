package com.itcast.erp.biz;
import com.itcast.erp.entity.Orderdetail;
/**
 * 订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{
	/**
	 * 采购入库
	 * @param uuid 明细编号
	 * @param storeUuid 仓库管理
	 * @param empUuid 库管员编号
	 */
	void doInStore(Long uuid,Long storeuuid,Long empuuid);
	
	void doOutStore(Long uuid,Long storeuuid,Long empuuid);
}

