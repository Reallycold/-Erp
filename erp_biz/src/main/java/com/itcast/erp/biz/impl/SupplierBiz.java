package com.itcast.erp.biz.impl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.itcast.erp.biz.ISupplierBiz;
import com.itcast.erp.dao.ISupplierDao;
import com.itcast.erp.entity.Supplier;
import com.itcast.erp.exception.ErpException;
/**
 * 供应商业务逻辑类
 * 
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}

	
	public void export(OutputStream os, Supplier t1) {
		//获取要导出的数据列表
		List<Supplier> list = supplierDao.getList(t1, null, null);
		//创建一个工作簿
		HSSFWorkbook wk = new HSSFWorkbook();
		
		String sheetName = "";
		if(Supplier.TYPE_CUSTOMER.equals(t1.getType())){
			sheetName = "客户";
		}
		if(Supplier.TYPE_SUPPLIER.equals(t1.getType())){
			sheetName = "供应商";
		}
		//创建一个工作表
		HSSFSheet sheet = wk.createSheet(sheetName);
		//写入表头
		HSSFRow row=sheet.createRow(0);
		//定义好每一列的标题
		String[] title={"名称","地址","联系人","电话","email"};
		//指定每一列的宽度
		int[] columnWidths={4000,8000,2000,3000,8000};
		HSSFCell cell=null;
		for(int i=0;i<title.length;i++){
			cell=row.createCell(i);
			cell.setCellValue(title[i]);
			sheet.setColumnWidth(i, columnWidths[i]);
		}
		//写入内容
		int i=1;
		for (Supplier sp:list) {
			row=sheet.createRow(i);
			row.createCell(0).setCellValue(sp.getName());
			row.createCell(1).setCellValue(sp.getAddress());
			row.createCell(2).setCellValue(sp.getContact());
			row.createCell(3).setCellValue(sp.getTele());
			row.createCell(4).setCellValue(sp.getEmail());
			i++;
		}
		try {
			wk.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				wk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	public void doImport(InputStream is) throws IOException {
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			System.out.println(sheet.getSheetName());
			String type = "";
			if("供应商".equals(sheet.getSheetName())){
				type = Supplier.TYPE_SUPPLIER;
			}else if("客户".equals(sheet.getSheetName())){
				type = Supplier.TYPE_CUSTOMER;
			}else{
				throw new ErpException("工作表名称不正确");
			}
			
			//读取数据
			//最后一行的行号
			int lastRow = sheet.getLastRowNum();
			Supplier supplier = null;
			for(int i = 1; i <= lastRow; i++){
				supplier = new Supplier();
				supplier.setName(sheet.getRow(i).getCell(0).getStringCellValue());//供应商名称
				//判断是否已经存在，通过名称来判断
				List<Supplier> list = supplierDao.getList(null, supplier, null);
				if(list.size() > 0){
					supplier = list.get(0);
				}
				System.out.println((list.size()==0)+" list没有值了吗");
				supplier.setAddress(sheet.getRow(i).getCell(1).getStringCellValue());//地址
				supplier.setContact(sheet.getRow(i).getCell(2).getStringCellValue());//联系人
				// 这里电话是纯数字  Cannot get a text value from a numeric cell
				// 纯数字 读进来报错了 纯数字作为String类型读进来了：
				sheet.getRow(i).getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				supplier.setTele(sheet.getRow(i).getCell(3).getStringCellValue());//电话
				supplier.setEmail(sheet.getRow(i).getCell(4).getStringCellValue());//Email
				if(list.size() == 0){
					//新增
					supplier.setType(type);
					supplierDao.add(supplier);
				}
			}			
		} finally{
			if(null != wb){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
