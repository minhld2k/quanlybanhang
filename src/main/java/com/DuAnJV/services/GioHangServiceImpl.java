package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Giohang;
import com.DuAnJV.repositories.GioHangRepository;

@Service
public class GioHangServiceImpl implements GioHangService{
	
	@Autowired
	GioHangRepository gioHangRepository;

	@Override
	public Giohang save(Giohang entity) {
		return this.gioHangRepository.save(entity);
	}

	@Override
	public void delete(Giohang entity) {
		this.gioHangRepository.delete(entity);
	}

	@Override
	public List<Giohang> findAll(long customerid,int limit, int offset) {
		return this.gioHangRepository.findAll(customerid,limit, offset);
	}

	@Override
	public long count(long customerid) {
		return this.gioHangRepository.count(customerid);
	}

	@Override
	public Giohang findById(long id) {
		return this.gioHangRepository.findById(id);
	}
	
	
	
}
