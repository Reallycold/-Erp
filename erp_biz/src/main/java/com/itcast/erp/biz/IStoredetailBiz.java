package com.itcast.erp.biz;
import java.util.List;

import javax.mail.MessagingException;

import com.itcast.erp.entity.Storealert;
import com.itcast.erp.entity.Storedetail;
/**
 * 仓库库存业务逻辑层接口
 * @author Administrator
 *
 */
public interface IStoredetailBiz extends IBaseBiz<Storedetail>{
	/**
	 * 获取库存预警列表
	 */
	public List<Storealert> getStorealertList();
	
	/**
	 * 发送库存预警邮件
	 */
	public void sendStoreAlertMail() throws MessagingException;
		
	
}

