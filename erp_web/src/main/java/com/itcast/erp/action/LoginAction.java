package com.itcast.erp.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.itcast.erp.biz.IEmpBiz;
import com.itcast.erp.entity.Emp;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends BaseAction<Emp>{

   /**
    * 其实loginAction是员工登录的方法 
    * 不需要继承baseAction
    * 需要注入 username and pwd(属性驱动）
    * 
    */
	
	
	
	private String username;
	private String pwd;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * 登录验证请求
	 */
/*	public void checkUser(){
		try {
			Emp loginUser=empBiz.findByUsernameAndPwd(username, pwd);
			if(null == loginUser){
				ajaxReturn(false,"用户名和密码错误");
				return;
			}else{
				//如果不为空 保存到session里面
				ActionContext.getContext().getSession().put("loginUser", loginUser);
				ajaxReturn(true, "");//直接转到 index
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			ajaxReturn(false, "登录失败");
		}
	}*/
	
	public void checkUser(){
		try {
			//创建令牌
			UsernamePasswordToken upt=new UsernamePasswordToken(username,pwd);
			//获得主题
			Subject subject=SecurityUtils.getSubject();
			//执行login
			subject.login(upt);
			ajaxReturn(true, "");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			ajaxReturn(false, "登录失败");
		}
	}
	
	
	
	/**
	 * 在 index.html页面 show LoginUser
	 */
	
	public void showName(){
		//Emp loginUser=(Emp) ActionContext.getContext().getSession().get("loginUser");
		
		Emp emp=(Emp) SecurityUtils.getSubject().getPrincipal();
		
		if(null != emp){
			ajaxReturn(true, emp.getName());
		}else{
			ajaxReturn(false, "");//登录失败，则什么都不显示
		}
	}
	
	/**
	 * 登出 方法
	 */
	public void loginOut(){
		SecurityUtils.getSubject().logout();
	}
	
}
