package com.itcast.erp.biz.impl;




import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.itcast.erp.biz.IEmpBiz;
import com.itcast.erp.dao.IEmpDao;
import com.itcast.erp.dao.IMenuDao;
import com.itcast.erp.dao.IRoleDao;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Menu;
import com.itcast.erp.entity.Role;
import com.itcast.erp.entity.Tree;
import com.itcast.erp.exception.ErpException;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private int hashIterations = 2;
	
	private IEmpDao empDao;
	
	


	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}

	
	public Emp findByUsernameAndPwd(String username, String pwd) {
		String encrypted=encryt(pwd, username);
		return empDao.findByUsernameAndPwd(username, encrypted);
	}

	/**
	 * md5加密密码 
	 * source ： 原来的密码
	 * salt 盐值
	 * 返回加密后的字符串
	 * @param source
	 * @param salt
	 * @return
	 */
	private String encryt(String source,String salt){
		Md5Hash md5=new Md5Hash(source, salt, hashIterations);
		return md5.toString();
	}
	
	/**
	 * 重写的add方法
	 * 在添加密码的时候 在业务层对密码进行一个加密，然后存到数据库
	 */
	public void add(Emp emp) {
		
		//设置初始密码
		String salt=emp.getUsername();//用username 当盐值
		emp.setPwd(encryt(emp.getUsername(), salt));
		System.out.println(encryt(emp.getUsername(), salt));
		super.add(emp);
	}


	/**
	 *  根据员工uuuid进行密码修改  这里操作 旧密码
	 */
	public void update(Long uuid, String oldPwd, String newPwd) throws ErpException {
		Emp emp =empDao.get(uuid);
		String encrytedOldPwd = encryt(oldPwd, emp.getUsername());
		if(!encrytedOldPwd.equals(emp.getPwd())){
			throw new ErpException("原密码不正确");
		}else{
			String newpassword=encryt(newPwd, emp.getUsername());
			empDao.updatePwd(uuid, newpassword);
		}
		
	}


	/**
	 * 重置密码
	 */
	public void updatePwd_reset(Long uuid, String newPwd) {
		Emp emp=this.empDao.get(uuid);
		//设置密码触发update
		empDao.updatePwd(uuid, encryt(newPwd, emp.getUsername()));
		
	}

	private IRoleDao roleDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}


	public List<Tree> readEmpRoles(Long uuid) {
		List<Tree> treeList=new ArrayList<Tree>();
		Emp emp=empDao.get(uuid);
		List<Role> empRoles=emp.getRoles();
		//获取所有的角色
		List<Role> roleList=roleDao.getList(null, null, null);
		Tree t1=null;
		for (Role role : roleList) {
			t1=new Tree();
			t1.setId(String.valueOf(role.getUuid()));
			t1.setText(role.getName());
			if(empRoles.contains(role)){
				t1.setChecked(true);
			}
			treeList.add(t1);
		}
		return treeList;
	}


	/**
	 * 更新用户角色
	 */
	public void updateEmpRoles(Long uuid, String checkedStr) {
		Emp emp=empDao.get(uuid);
		emp.setRoles(new ArrayList<Role>());
		String[] ids=checkedStr.split(",");
		Role role=null;
		for (String id : ids) {
			role=roleDao.get(Long.valueOf(id));
			emp.getRoles().add(role);
			
		}
	}


	


	
	
}
