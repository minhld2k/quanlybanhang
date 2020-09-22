package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Hangsx;

public interface HangsxService {

	Hangsx findByKey(String categorykey);

	Hangsx findById(long id);

	long countAll();

	List<Hangsx> findAll(int limit, int offset);

	int update(Hangsx hangsx);

	void add(Hangsx hangsx);

	List<Long> findCategoryById(Long id);

	long countAllByNameAndDM(String name, List<Long> cateroryid);

	List<Hangsx> findAllByNameAndDM(String name, List<Long> cateroryid, int limit, int offset);

	long countAllByDM(List<Long> cateroryid);

	List<Hangsx> findAllByDM(List<Long> cateroryid, int limit, int offset);

	long countAllByName(String name);

	List<Hangsx> findAllByName(String name, int limit, int offset);

}
