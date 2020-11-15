package com.DuAnJV.dto;

import java.util.List;

public class CategoryDTO {
	private long id;
	private String categorykey;
	private String categoryname;
	private List<Object[]> lsProduct;
	public CategoryDTO(long id, String categorykey, String categoryname, List<Object[]> lsProduct) {
		super();
		this.id = id;
		this.categorykey = categorykey;
		this.categoryname = categoryname;
		this.lsProduct = lsProduct;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategorykey() {
		return categorykey;
	}
	public void setCategorykey(String categorykey) {
		this.categorykey = categorykey;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public List<Object[]> getLsProduct() {
		return lsProduct;
	}
	public void setLsProduct(List<Object[]> lsProduct) {
		this.lsProduct = lsProduct;
	}
	
}
