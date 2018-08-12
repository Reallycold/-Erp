package com.itcast.erp.action;
import java.util.List;

import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;
import com.itcast.erp.biz.IStoredetailBiz;
import com.itcast.erp.entity.Storealert;
import com.itcast.erp.entity.Storedetail;
import com.itcast.erp.exception.ErpException;

/**
 * 仓库库存Action 
 * @author Administrator
 *
 */
public class StoredetailAction extends BaseAction<Storedetail> {

	private IStoredetailBiz storedetailBiz;

	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
		super.setBaseBiz(this.storedetailBiz);
	}

	public void storealertList(){
		List<Storealert> list = storedetailBiz.getStorealertList();
		write(JSON.toJSONString(list));
	}
	
	/**
	 * 发送邮件预警
	 */
	public void sendStorealertMail(){
		try {
			storedetailBiz.sendStoreAlertMail();
			ajaxReturn(true, "发送预警邮件成功");
		} catch (MessagingException e) {
			e.printStackTrace();
			ajaxReturn(false, "构建预警邮件失败");
		}catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			ajaxReturn(false, "发送预警邮件失败");
			e.printStackTrace();
		}
	}
	
}















