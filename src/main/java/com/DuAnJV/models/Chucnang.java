package com.DuAnJV.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="qtht_chucnangs")
public class Chucnang {

	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.DuAnJV.common.IDGenerator")
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String key;
	
	@Column
	private String url;
	
	@Column
	private String creatby;
	
	@Column
	private String updateby;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date creatday;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date updateday;
	
	@Column
	private byte isdelete;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chucnangchaid")
	private Chucnang chucnangcha;

	public Chucnang() {
		super();
	}

	public Chucnang(Long id, String name, String key, String url, String creatby, String updateby, Date creatday,
			Date updateday, byte isdelete, Chucnang chucnangcha) {
		super();
		this.id = id;
		this.name = name;
		this.key = key;
		this.url = url;
		this.creatby = creatby;
		this.updateby = updateby;
		this.creatday = creatday;
		this.updateday = updateday;
		this.isdelete = isdelete;
		this.chucnangcha = chucnangcha;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCreatby() {
		return creatby;
	}

	public void setCreatby(String creatby) {
		this.creatby = creatby;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public Date getCreatday() {
		return creatday;
	}

	public void setCreatday(Date creatday) {
		this.creatday = creatday;
	}

	public Date getUpdateday() {
		return updateday;
	}

	public void setUpdateday(Date updateday) {
		this.updateday = updateday;
	}

	public byte getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(byte isdelete) {
		this.isdelete = isdelete;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public Chucnang getChucnangcha() {
		return chucnangcha;
	}


	public void setChucnangcha(Chucnang chucnangcha) {
		this.chucnangcha = chucnangcha;
	}

}
