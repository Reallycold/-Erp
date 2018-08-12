package com.itcast.erp.biz;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.itcast.erp.entity.Supplier;
/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{
	/**
	 * 导出到execl
	 */
	public void export(OutputStream os,Supplier t1);
	
	/**
	 * 导入 文件
	 * @param is
	 * @throws IOException
	 */
	public void doImport(InputStream is)throws IOException;
}

