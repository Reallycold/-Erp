package com.itcast.erp.biz;
import java.io.OutputStream;
import java.util.List;

import com.itcast.erp.entity.Orders;
import com.redsum.bos.ws.Waybilldetail;
/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
	/**
	 * 采购订单审核业务
	 */
	void doCheck(Long uuid, Long empUuid);
	
	/**
	 * 采购订单确认业务
	 */
	void doStart(Long uuid, Long empUuid);
	
	void exportById(OutputStream os, Long uuid) throws Exception;

	List<Waybilldetail> waybilldetailList(Long waybillsn);
}

