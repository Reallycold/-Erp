package com.itcast.erp.exception;

/**
 * 自定义异常 用于业务逻辑退出
 * @author Administrator
 *
 */
public class ErpException extends RuntimeException{
	public ErpException(String message){
		super(message);
	}
}
