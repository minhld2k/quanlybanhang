package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Category;
import com.DuAnJV.models.Hangsx;
import com.DuAnJV.repositories.HangsxRepository;

@Service
public class HangsxServiceImpl implements HangsxService{
	@Autowired
	HangsxRepository hangsxRepository;

	@Override
	public void add(Hangsx hangsx) {
		this.hangsxRepository.add(hangsx.getId(), hangsx.getName(), hangsx.getImage(), hangsx.getCreatday()
				, hangsx.getCreatby(), hangsx.getUpdateday(), hangsx.getUpdateby(), hangsx.getIsdelete(), hangsx.getKey());
		if (hangsx.getCategories() != null) {
			for (Category category : hangsx.getCategories()) {
				this.hangsxRepository.addCategory(category.getId(), hangsx.getId());
			}
		}
	}

	@Override
	public int update(Hangsx hangsx) {
		this.hangsxRepository.update(hangsx.getName(), hangsx.getImage(), hangsx.getUpdateday()
				, hangsx.getUpdateby(), hangsx.getIsdelete(), hangsx.getKey(), hangsx.getId());
		this.hangsxRepository.deleteCategory(hangsx.getId());
		if (hangsx.getCategories() != null) {
			for (Category category : hangsx.getCategories()) {
				this.hangsxRepository.addCategory(category.getId(), hangsx.getId());
			}
		}
		return 1;
	}

	@Override
	public List<Hangsx> findAll(int limit, int offset) {
		return this.hangsxRepository.findAll(limit, offset);
	}

	@Override
	public long countAll() {
		return this.hangsxRepository.countAll();
	}

	@Override
	public Hangsx findById(long id) {
		return this.hangsxRepository.findById(id);
	}

	@Override
	public Hangsx findByKey(String categorykey) {
		return this.hangsxRepository.findByKey(categorykey);
	}

	@Override
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
