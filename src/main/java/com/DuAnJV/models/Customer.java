package com.DuAnJV.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="qtht_customers")
public class Customer extends Common{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.DuAnJV.common.IDGenerator")
	private Long id;
	
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String fullname;
	
	@Column
	private String address;
	
	@Column
	private String phone;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column
	private Byte gender;

	public Customer(Byte isdelete, String creatby, Date creatday, String updateby, Date updateday, Long id,
			String email, String password, String fullname, String address, String phone, Date birthday, Byte gender) {
		super(isdelete, creatby, creatday, updateby, updateday);
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
	}

	public Customer() {
		super();
	}

	public Customer(Long id, String email, String password, String fullname, String address, String phone,
			Date birthday, Byte gender) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}
	
}
