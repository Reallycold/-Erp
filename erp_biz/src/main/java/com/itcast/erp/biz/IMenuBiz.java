package com.itcast.erp.biz;
import java.util.List;

import com.itcast.erp.entity.Menu;
/**
 * 菜单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IMenuBiz extends IBaseBiz<Menu>{
	/**
	 * 根据员工编号获取菜单
	 */
	List<Menu> getMenusByEmpuuid(Long uuid);
	
	
	Menu readMenusByEmpuuid(Long uuid);
}

