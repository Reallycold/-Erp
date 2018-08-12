package com.itcast.erp.action;
import com.itcast.erp.biz.IStoreBiz;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Store;

/**
 * 仓库Action 
 * @author Administrator
 *
 */
public class StoreAction extends BaseAction<Store> {

	private IStoreBiz storeBiz;

	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		super.setBaseBiz(this.storeBiz);
	}
	
	public void myList(){
		System.out.println("hellowo");
		if(null == getT1()){
			//构建查询条件
			setT1(new Store());
		}
		Emp loginUser= getLoginUser();
		//查找当前登录用户下的仓库
		getT1().setEmpuuid(loginUser.getUuid());
		super.list();
	}
}








