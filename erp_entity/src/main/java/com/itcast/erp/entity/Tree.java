package com.itcast.erp.entity;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	private String id;//菜单id
	private String text;//菜单名称
	
	private boolean checked;
	private List<Tree> children;//夏季菜单
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * 修改实体类
	 * @return
	 */
	public List<Tree> getChildren() {
		if(null==children){
			children=new ArrayList<Tree>();
		}
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	
}
