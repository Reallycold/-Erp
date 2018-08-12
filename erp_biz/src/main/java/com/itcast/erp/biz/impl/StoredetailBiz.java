package com.itcast.erp.biz.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.itcast.erp.biz.IStoredetailBiz;
import com.itcast.erp.dao.IGoodsDao;
import com.itcast.erp.dao.IStoreDao;
import com.itcast.erp.dao.IStoredetailDao;
import com.itcast.erp.entity.Storealert;
import com.itcast.erp.entity.Storedetail;
import com.itcast.erp.exception.ErpException;
import com.itcast.erp.util.MailUtil;
/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	
	private MailUtil mailUtil;
	private String subject;
	private String text;
	private String to;
	
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public void setText(String text) {
		this.text = text;
	}


	public void setTo(String to) {
		this.to = to;
	}



	/**
	 * day07 6.27 
	 * 库存查询
	 */
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;
	
	
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}


	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}


	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		super.setBaseDao(this.storedetailDao);
	}
	
	/**
	 * 根据商品id --Map 封装对应的Map
	 * @param uuid
	 * @param goodsNameMap
	 * @return
	 */
	private String getGoodsName(Long uuid, Map<Long,String> goodsNameMap){
		if(null==uuid){
			return null;
		}
		String goodsName=goodsNameMap.get(uuid);
		if(null==goodsName){
			goodsName = goodsDao.get(uuid).getName();
			goodsNameMap.put(uuid, goodsName);
		}
		return goodsName;
	}
	
	private String getStoreName(Long uuid,Map<Long,String> storeNameMap){
		if(uuid==null){
			return null;
		}
		String storeName=storeNameMap.get(uuid);//根据id得到Name
		storeNameMap.put(uuid, storeName);
		return storeName;
	}


	@Override
	public List<Storedetail> getListByPage(Storedetail t1, Storedetail t2, Object param, int firstResult,
			int maxResults) {
		List<Storedetail> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		Map<Long,String> goodsNameMap =new HashMap<Long,String>();
		Map<Long,String> storeNameMap =new HashMap<Long,String>();
		for (Storedetail storedetail : list) {
			storedetail.setGoodsName(getGoodsName(storedetail.getGoodsuuid(), goodsNameMap));
			storedetail.setStoreName(getStoreName(storedetail.getStoreuuid(), storeNameMap));
		}
		
		return list;
	}


	/**
	 * 获取库存预警列表
	 */
	public List<Storealert> getStorealertList() {
		
		return storedetailDao.getStorealertList();
	}


	/**
	 * 发送库存预警邮件
	 */
	public void sendStoreAlertMail(){
		//获取库存预警列表
		List<Storealert> storealertList = storedetailDao.getStorealertList();
		//获取库存预警商品的个数
		int size = null == storealertList?0:storealertList.size();
		if(size > 0){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//subject = 库存预警_时间:[time] => 库存预警_时间:2017-05-04 10:42:45
				String sub = subject.replace("[time]", sdf.format(new Date()));			
				//库存预警_时间:%s
				//String sub = String.format(subject, sdf.format(new Date()));
				//text=尊敬的客户，有[count]种商品已经预警了，请登陆蓝云ERP3.1系统查看
				//text=尊敬的客户，有size种商品已经预警了，请登陆蓝云ERP3.1系统查看
				String.format(text, size);
				mailUtil.sendMail(to, sub, text.replace("[count]", String.valueOf(size)));
			} catch (MessagingException e) {
				e.printStackTrace();
				throw new ErpException("发送预警邮件失败");
			}
		}else{
			throw new ErpException("没有需要预警的商品");
		}
		
	}


	
		
}















