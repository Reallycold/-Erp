package com.itcast.erp.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class ErpAuthorizationFilter extends AuthorizationFilter{

	
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		//1 获取主题
		Subject subject=getSubject(request, response);
		String[] perms=(String[])mappedValue;
		boolean isPermitted=true;
		if(null==perms || perms.length==0){
			return isPermitted;
		}
		if(perms!=null && perms.length>0){
			for (String perm : perms) {
				if(subject.isPermitted(perm)){
					return true;
				}
			}
		}
		
		return false;
	}

}
