package com.DuAnJV.services;

import java.util.List;
import java.util.Optional;

import com.DuAnJV.models.Chucnang;

public interface ChucnangService {

	long count();

	Optional<Chucnang> findById(Long id);

	List<Chucnang> findByNameAndIsdelete(String roleName, Byte isDelete,Integer pagesize, Integer offset);

	int update(Chucnang chucnang);

	void add(Chucnang chucnang);

	List<Chucnang> findAllChucnangcha();

	List<Chucnang> findAllChucnang(Integer pagesize, Integer offset);

	long countByName(String name, Byte isdelete);

	long count(Long id);

	List<String> AllKey();

	long countByKey(String key);

	List<Chucnang> findAllByKey(String key, Integer pagesize, Integer offset);

	long countByNameAndKey(String name, String key);

	List<Chucnang> findAllByNameAndKey(String name, String key, Integer pagesize, Integer offset);

	Chucnang findByKey(String key);

	List<Chucnang> findAllChucnangByEmail(String email);

}
