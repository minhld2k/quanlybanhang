package com.DuAnJV.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class Common implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private Byte isdelete;
	
	@Column
	private String creatby;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date creatday;
	
	@Column
	private String updateby;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date updateday;
	
	public Common(Byte isdelete, String creatby, Date creatday, String updateby, Date updateday) {
		super();
		this.isdelete = isdelete;
		this.creatby = creatby;
		this.creatday = creatday;
		this.updateby = updateby;
		this.updateday = updateday;
	}

	public Common() {
		super();
	}

	public Byte getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Byte isdelete) {
		this.isdelete = isdelete;
	}

	public String getCreatby() {
		return creatby;
	}

	public void setCreatby(String creatby) {
		this.creatby = creatby;
	}

	public Date getCreatday() {
		return creatday;
	}

	public void setCreatday(Date creatday) {
		this.creatday = creatday;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Date getUpdateday() {
		return updateday;
	}

	public void setUpdateday(Date updateday) {
		this.updateday = updateday;
	}
	
	
}
