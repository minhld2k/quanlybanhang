package com.DuAnJV.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Chucnang;
import com.DuAnJV.repositories.ChucNangRepository;

@Service
public class ChucnangServiceImpl implements ChucnangService {

	@Autowired
	ChucNangRepository chucNangRepository;

	@Override
	public void add(Chucnang chucnang) {
		this.chucNangRepository.add(chucnang.getId(), chucnang.getName(),chucnang.getKey(), chucnang.getCreatday()
				, chucnang.getCreatby(), chucnang.getUpdateday(), chucnang.getUpdateby()
				,chucnang.getIsdelete(),chucnang.getChucnangcha().getId(), chucnang.getUrl());
	}

	@Override
	public int update(Chucnang chucnang) {
		return this.chucNangRepository.update(chucnang.getName(),chucnang.getKey(), chucnang.getUpdateday(), chucnang.getUpdateby()
				, chucnang.getIsdelete(),chucnang.getChucnangcha().getId(), chucnang.getUrl(), chucnang.getId());
	}

	@Override
	public List<Chucnang> findByNameAndIsdelete(String roleName, Byte isDelete,Integer pagesize, Integer offset) {
		return this.chucNangRepository.findByNameAndIsdelete(roleName, isDelete,pagesize,offset);
	}

	@Override
	public Optional<Chucnang> findById(Long id) {
		return this.chucNangRepository.findById(id);
	}

	@Override
	public long count() {
		return this.chucNangRepository.count();
	}

	@Override
	public List<Chucnang> findAllChucnangcha() {
		return this.chucNangRepository.findAllChucnangcha();
	}

	@Override
	public List<Chucnang> findAllChucnang(Integer pagesize, Integer offset) {
		return this.chucNangRepository.findAll(pagesize, offset);
	}

	@Override
	public long countByName(String name, Byte isdelete) {
		return this.chucNangRepository.countByName(name, isdelete);
	}

	@Override
	public long count(Long id) {
		return this.chucNangRepository.count(id);
	}

	@Override
	public List<String> AllKey() {
		return this.chucNangRepository.AllKey();
	}

	@Override
	public List<Chucnang> findAllByKey(String key, Integer pagesize, Integer offset) {
		return this.chucNangRepository.findAllByKey("%"+key+"%", pagesize, offset);
	}

	@Override
	public long countByKey(String key) {
		return this.chucNangRepository.countByKey("%"+key+"%");
	}

	@Override
	public List<Chucnang> findAllByNameAndKey(String name, String key, Integer pagesize, Integer offset) {
		return this.chucNangRepository.findAllByNameAndKey(name,"%"+key+"%", pagesize, offset);
	}

	@Override
	public long countByNameAndKey(String name, String key) {
		return this.chucNangRepository.countByNameAndKey(name, "%"+key+"%");
	}

	@Override
	public Chucnang findByKey(String key) {
		return this.chucNangRepository.findByKey(key);
	}

	@Override
	public List<Chucnang> findAllChucnangByEmail(String email) {
		return this.chucNangRepository.findAllChucnangByEmail(email,email);
	}
	
	
}
