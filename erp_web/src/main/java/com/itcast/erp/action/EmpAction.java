package com.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itcast.erp.biz.IEmpBiz;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Tree;
import com.itcast.erp.exception.ErpException;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;

	private String checkedStr;
	
	


	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	/**
	 * 属性驱动  接受页面传进来的新旧密码值
	 * @param empBiz
	 */
	private String oldPwd;
	private String newPwd;
	
	
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}
	
	/**
	 * 对密码修改的方法也进行重写
	 * 如果没登录 则要提示登录
	 */
	public void updatePwd(){
		Emp loginUser=getLoginUser();
		if(null == loginUser){
			ajaxReturn(false, "请先登录");
			return;
		}
		try {
			empBiz.update(loginUser.getUuid(), oldPwd, newPwd);
			ajaxReturn(true, "修改密码成功");
		} catch (ErpException e) {
			
			e.printStackTrace();
			ajaxReturn(false, "修改密码失败");
		}
	}
	
	public void updatePwd_reset(){
		try {
			empBiz.updatePwd_reset(getId(), newPwd);
			ajaxReturn(true, "重置密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "重置密码失败");
		}
	}
	
	/**
	 * 获取用户角色信息
	 */
	public void readEmpRoles(){
		List<Tree> list = empBiz.readEmpRoles(getId());
		write(JSON.toJSONString(list));
	}
	
	/**
	 * 更新角色信息
	 */
	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(getId(), checkedStr);
			ajaxReturn(true, "更新用户角色成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新用户角色失败");
			e.printStackTrace();
		}
	}
}
