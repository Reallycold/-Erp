package com.itcast.erp.biz;

import java.util.List;

import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Menu;
import com.itcast.erp.entity.Tree;
import com.itcast.erp.exception.ErpException;

/**
 * 员工业务逻辑层接口
 * 
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp> {

	/**
	 * service 的登录
	 */
	public Emp findByUsernameAndPwd(String username, String pwd);

	/**
	 * 添加修改密码的方法
	 */
	public void update(Long uuid, String oldPwd, String newPwd) throws ErpException;

	public void updatePwd_reset(Long uuid, String newPwd);
	
	/**
	 * 读取用户角色
	 */
	List<Tree> readEmpRoles(Long uuid);
	
	/**
	 * 更新用户角色
	 */
	public void updateEmpRoles(Long uuid, String checkedStr);

}
