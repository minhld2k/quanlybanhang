package com.DuAnJV.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qtht_categories")
public class Category extends Common {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@Column
	private String categoryname;
	
	@Column
	private String categorykey;

	public Category() {
		super();
	}	
	
	public Category(Byte isdelete, String creatby, Date creatday, String updateby, Date updateday, Long id,
			String categoryname, String categorykey) {
		super(isdelete, creatby, creatday, updateby, updateday);
		this.id = id;
		this.categoryname = categoryname;
		this.categorykey = categorykey;
	}

	public Category(Long id, String categoryname, String categorykey) {
		super();
		this.id = id;
		this.categoryname = categoryname;
		this.categorykey = categorykey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getCategorykey() {
		return categorykey;
	}

	public void setCategorykey(String categorykey) {
		this.categorykey = categorykey;
	}

}
