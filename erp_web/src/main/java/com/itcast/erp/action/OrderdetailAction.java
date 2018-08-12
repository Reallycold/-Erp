package com.itcast.erp.action;
import com.itcast.erp.biz.IOrderdetailBiz;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Orderdetail;
import com.itcast.erp.exception.ErpException;

/**
 * 订单明细Action 
 * @author Administrator
 *
 */
public class OrderdetailAction extends BaseAction<Orderdetail> {

	private IOrderdetailBiz orderdetailBiz;

	public void setOrderdetailBiz(IOrderdetailBiz orderdetailBiz) {
		this.orderdetailBiz = orderdetailBiz;
		super.setBaseBiz(this.orderdetailBiz);
	}

	private Long storeuuid;

	public Long getStoreuuid() {
		return storeuuid;
	}

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}
	public void doInStore(){
		Emp loginUser =getLoginUser();
		if(null==loginUser){
			ajaxReturn(false, "亲，请先登录");
			return;
		}
		try {
			orderdetailBiz.doInStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "成功入库");
		} catch(ErpException e){
			ajaxReturn(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "入库失败");
		}
	}
	
	
	public void doOutStore(){
		Emp loginUser =getLoginUser();
		if(null==loginUser){
			ajaxReturn(false, "亲，请先登录");
			return;
		}
		try {
			orderdetailBiz.doOutStore(getId(), storeuuid, loginUser.getUuid());
			ajaxReturn(true, "成功出库");
		} catch(ErpException e){
			ajaxReturn(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "入库失败");
		}
	}
}














