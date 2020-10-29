package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Hangsx;
import com.DuAnJV.repositories.HangsxRepository;

@Service
public class HangsxServiceImpl implements HangsxService{
	@Autowired
	HangsxRepository hangsxRepository;

	@Override
	public void add(Hangsx hangsx) {
		this.hangsxRepository.save(hangsx);
	}

	@Override
	public void update(Hangsx hangsx) {
		this.hangsxRepository.save(hangsx);
	}

	@Override
	@CachePut(value = "findAllHangsx", key = "#offset")
	public List<Hangsx> findAll(int limit, int offset) {
		return this.hangsxRepository.findAll(limit, offset);
	}

	@Override
	@CachePut(value = "countAllHangsx")
	public long countAll() {
		return this.hangsxRepository.countAll();
	}

	@Override
	@CachePut(value = "findHangsxById", key = "#id")
	public Hangsx findById(long id) {
		return this.hangsxRepository.findById(id);
	}

	@Override
	@CachePut(value = "findHangsxByKey", key = "#key")
	public Hangsx findByKey(String key) {
		return this.hangsxRepository.findByKey(key);
	}

	@Override
	@CachePut(value = "findCategoryByHangsxId", key = "#id")
	public List<Long> findCategoryById(Long id) {
		return this.hangsxRepository.findCategoryById(id);
	}

	@Override
	public List<Hangsx> findAllByName(String name, int limit, int offset) {
		return this.hangsxRepository.findAllByName(name, limit, offset);
	}

	@Override
	public long countAllByName(String name) {
		return this.hangsxRepository.countAllByName(name);
	}

	@Override
	public List<Hangsx> findAllByDM(List<Long> cateroryid, int limit, int offset) {
		return this.hangsxRepository.findAllByDM(cateroryid, limit, offset);
	}

	@Override
	public long countAllByDM(List<Long> cateroryid) {
		return this.hangsxRepository.countAllByDM(cateroryid);
	}

	@Override
	public List<Hangsx> findAllByNameAndDM(String name, List<Long> cateroryid, int limit, int offset) {
		return this.hangsxRepository.findAllByNameAndDM(name, cateroryid, limit, offset);
	}

	@Override
	public long countAllByNameAndDM(String name, List<Long> cateroryid) {
		return this.hangsxRepository.countAllByNameAndDM(name, cateroryid);
	}
	
	
}
