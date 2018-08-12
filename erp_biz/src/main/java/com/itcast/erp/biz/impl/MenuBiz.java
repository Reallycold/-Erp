package com.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import com.itcast.erp.biz.IMenuBiz;
import com.itcast.erp.dao.IMenuDao;
import com.itcast.erp.dao.impl.EmpDao;
import com.itcast.erp.entity.Menu;
/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	private EmpDao empDao;
	
	
	public EmpDao getEmpDao() {
		return empDao;
	}


	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}


	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}

	@Override
	public List<Menu> getMenusByEmpuuid(Long uuid) {
		return menuDao.getMenusByEmpuuid(uuid);
	}

	@Override
	public Menu readMenusByEmpuuid(Long uuid) {
		//获取所有的菜单，做模板用的
		Menu root = menuDao.get("0");
		//用户下的菜单集合
		List<Menu> empMenus = menuDao.getMenusByEmpuuid(uuid);
		//根菜单
		Menu menu = cloneMenu(root);
		
		//循环匹配模板
		//一级菜单
		Menu _m1 = null;
		Menu _m2 = null;
		for(Menu m1 : root.getMenus()){
			_m1 = cloneMenu(m1);
			//二级菜单循环
			for(Menu m2 : m1.getMenus()){
				//用户包含有这个菜单
				if(empMenus.contains(m2)){
					//复制菜单
					_m2 = cloneMenu(m2);
					//加入到上级菜单下
					_m1.getMenus().add(_m2);
				}
			}
			//有二级菜单我们才加进来
			if(_m1.getMenus().size() > 0){
				//把一级菜单加入到根菜单下
				menu.getMenus().add(_m1);
			}
		}
		return menu;
	}
	
	/**
	 * 复制menu
	 * @param src
	 * @return
	 */
	private Menu cloneMenu(Menu src){
		Menu menu = new Menu();
		menu.setIcon(src.getIcon());
		menu.setMenuid(src.getMenuid());
		menu.setMenuname(src.getMenuname());
		menu.setUrl(src.getUrl());
		menu.setMenus(new ArrayList<Menu>());
		return menu;
	}

	
	
}
