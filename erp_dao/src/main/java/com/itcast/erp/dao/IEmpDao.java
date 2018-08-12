package com.itcast.erp.dao;

import java.util.List;

import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Menu;
/**
 * 员工数据访问接口
 * @author Administrator
 *
 */
public interface IEmpDao extends IBaseDao<Emp>{

	/**
	 * 员工根据用户名和密码登录
	 */
	Emp findByUsernameAndPwd(String username,String pwd);
	
	/**
	 * 修改密码
	 */
	void updatePwd(Long uuid,String newPwd);

	
}
