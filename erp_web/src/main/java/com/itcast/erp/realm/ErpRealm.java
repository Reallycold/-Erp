package com.itcast.erp.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.itcast.erp.biz.IEmpBiz;
import com.itcast.erp.biz.IMenuBiz;
import com.itcast.erp.entity.Emp;
import com.itcast.erp.entity.Menu;

public class ErpRealm extends AuthorizingRealm{

	private IEmpBiz empBiz;
	private IMenuBiz menuBiz;
	
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
	}



	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	

	/**
	 * 认证方法  authentication  CA 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("登录");
		UsernamePasswordToken upt= (UsernamePasswordToken) token;
		String pwd=new String(upt.getPassword());
		Emp emp=empBiz.findByUsernameAndPwd(upt.getUsername(), pwd);
		//验证成功
		if(null != emp){
			return new SimpleAuthenticationInfo(emp, pwd,getName());
		}
		return null;
	}

	/**
	 * 授权方法
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		//得到当前登录的用户
		Emp emp=(Emp) principals.getPrimaryPrincipal();
		//获取登录用户对应的菜单权限
		List<Menu> menu=menuBiz.getMenusByEmpuuid(emp.getUuid());
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		for (Menu menu2 : menu) {
			info.addStringPermission(menu2.getMenuname());
		}
		
		return info;
	}

}


















