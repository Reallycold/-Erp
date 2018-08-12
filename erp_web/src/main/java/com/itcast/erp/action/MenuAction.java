package com.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itcast.erp.biz.IMenuBiz;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Menu;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private IMenuBiz menuBiz;

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}
	
	/**
	 * 获取菜单数据
	 */
	public void getMenuTree(){
		//通过获取主菜单，自关联就会带其下所有的菜单
		Menu menu = menuBiz.readMenusByEmpuuid(getLoginUser().getUuid());
		write(JSON.toJSONString(menu));
	}

	/**
	 * 根据员工编号获取菜单
	 */
	public void getMenusByEmpuuid(){
		Emp emp=getLoginUser();
		List<Menu> list = menuBiz.getMenusByEmpuuid(emp.getUuid());
		write(JSON.toJSONString(list));
	}
	
}
