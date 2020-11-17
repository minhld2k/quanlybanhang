package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Giohang;

public interface GioHangService {

	void delete(Giohang entity);

	Giohang save(Giohang entity);

	Giohang findById(long id);

	long count(long customerid);

	List<Giohang> findAll(long customerid,int limit, int offset);

}
