package com.itcast.erp.biz;
import java.util.List;

import com.itcast.erp.entity.Role;
import com.itcast.erp.entity.Tree;
/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
	/**
	 * 读取角色菜单
	 */
	List<Tree> readRoleMenus(Long uuid);
	
	/**
	 * 保存角色权限设置
	 */
	void updateRoleMenus(Long uuid,String checkedStr);
}

