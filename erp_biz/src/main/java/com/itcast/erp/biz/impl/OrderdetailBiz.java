package com.itcast.erp.biz.impl;
import java.util.Date;
import java.util.List;

import com.itcast.erp.biz.IOrderdetailBiz;
import com.itcast.erp.dao.IOrderdetailDao;
import com.itcast.erp.dao.IStoredetailDao;
import com.itcast.erp.dao.IStoreoperDao;
import com.itcast.erp.dao.ISupplierDao;
import com.itcast.erp.entity.Orderdetail;
import com.itcast.erp.entity.Orders;
import com.itcast.erp.entity.Storedetail;
import com.itcast.erp.entity.Storeoper;
import com.itcast.erp.entity.Supplier;
import com.itcast.erp.exception.ErpException;
import com.redsum.bos.ws.impl.IWaybillWs;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	private IStoredetailDao storedetailDao;
	
	private IWaybillWs waybillWs;
	private ISupplierDao supplierDao;
	
	
	
	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	private IStoreoperDao storeoperDao;
	
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		super.setBaseDao(this.orderdetailDao);
	}
	
	/**
	 * 入库
	 */
	public void doInStore(Long uuid,Long storeuuid, Long empuuid){
		//******** 第1步 更新商品明细**********
		//1. 获取明细信息
		Orderdetail detail = orderdetailDao.get(uuid);
		//2. 判断明细的状态，一定是未入库的
		if(!Orderdetail.STATE_NOT_IN.equals(detail.getState())){
			throw new ErpException("不能重复入库");
		}
		//3. 修改状态为已入库
		detail.setState(Orderdetail.STATE_IN);
		//4. 入库时间
		detail.setEndtime(new Date());
		//5. 库管员
		detail.setEnder(empuuid);
		//6. 入到哪个仓库
		detail.setStoreuuid(storeuuid);
		
		//*******第2 步 更新商品仓库库存*********
		//1. 构建查询的条件
		Storedetail storedetail = new Storedetail();
		storedetail.setGoodsuuid(detail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		//2. 通过查询 检查是否存在库存信息
		List<Storedetail> storeList = storedetailDao.getList(storedetail, null, null);
		if(storeList.size()>0){
			//存在的话，则应该累加它的数量
			long num = 0;
			if(null != storeList.get(0).getNum()){
				num = storeList.get(0).getNum().longValue();
			}
			storeList.get(0).setNum(num + detail.getNum());
		}else{
			//不存在，则应该插入库存的记录
			storedetail.setNum(detail.getNum());
			storedetailDao.add(storedetail);
		}
		
		//****** 第3步 添加操作记录*****
		//1. 构建操作记录
		Storeoper log = new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(detail.getGoodsuuid());
		log.setNum(detail.getNum());
		log.setOpertime(detail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType(Storeoper.TYPE_IN);
		//2. 保存到数据库中
		storeoperDao.add(log);
		
		//**** 第4步 是否需要更新订单的状态********
	
		//1. 构建查询条件
		Orderdetail queryParam = new Orderdetail();
		Orders orders = detail.getOrders();
		queryParam.setOrders(orders);
		//2. 查询订单下是否还存在状态为0的明细
		queryParam.setState(Orderdetail.STATE_NOT_IN);
		//3. 调用 getCount方法，来计算是否存在状态为0的明细
		long count = orderdetailDao.getCount(queryParam, null, null);
		if(count == 0){
			//4. 所有的明细都已经入库了，则要更新订单的状态，关闭订单
			orders.setState(Orders.STATE_END);
			orders.setEndtime(detail.getEndtime());
			orders.setEnder(empuuid);
		}
		
	}

	
	public void doOutStore(Long uuid, Long storeuuid, Long empuuid) {
		Orderdetail orderdetail=orderdetailDao.get(uuid);
		if(!"0".equals(orderdetail.getState())){
			throw new ErpException("该明细已经出库了，不能重复出库");
		}
		
		//更新订单明细
		orderdetail.setEnder(empuuid);
		orderdetail.setEndtime(new Date());
		orderdetail.setState("1");
		orderdetail.setStoreuuid(storeuuid);
		
		//查询库存
		Storedetail storedetail=new Storedetail();
		storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
		storedetail.setStoreuuid(storeuuid);
		List<Storedetail> list = storedetailDao.getList(storedetail, null, null);
	
		long num=-1L;
		if(null != list && list.size()>0){
			storedetail=list.get(0);
			num=storedetail.getNum().longValue()-orderdetail.getNum().longValue();
		}
		if(num >0){
			//库存充足,更新库存数量
			storedetail.setNum(num);
			
		}else{
			throw new ErpException("库存不足");
		}
		//添加库存变更操作纪录
		Storeoper log=new Storeoper();
		log.setEmpuuid(empuuid);
		log.setGoodsuuid(orderdetail.getGoodsuuid());
		log.setNum(orderdetail.getNum());
		log.setOpertime(orderdetail.getEndtime());
		log.setStoreuuid(storeuuid);
		log.setType("2");
		storeoperDao.add(log);
		
		//检查所有订单是否出库了  则更新订单状态 已经出库
		Orderdetail query=new Orderdetail();
		Orders orders=orderdetail.getOrders();
		query.setOrders(orders);
		query.setState("0");
		Long cnt=orderdetailDao.getCount(query, null, null);
		if(cnt==0){
			//所有明细已经出库
			orders.setState("1");
			orders.setEnder(empuuid);
			orders.setEndtime(orderdetail.getEndtime());
			//客户
			Supplier supplier = supplierDao.get(orders.getSupplieruuid());
			//在线预约下单,获取运单号
			Long waybillsn = waybillWs.addWaybill(1l, supplier.getAddress(), supplier.getContact(), supplier.getTele(), "--");
			//更新运单号
			orders.setWaybillsn(waybillsn);
		}
	}
	
}















