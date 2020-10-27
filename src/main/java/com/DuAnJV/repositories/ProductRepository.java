package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(value="SELECT * FROM qlsp_products where isdelete = 0 limit ? offset ?",nativeQuery = true)
	List<Product> findAll(Integer limit,Integer offset);
	
	@Query(value=" SELECT count(*) from qlsp_products where isdelete = 0",nativeQuery = true)
	long countAll();
	
	@Query(value="select * from qlsp_products where id = ? and isdelete = 0",nativeQuery = true)
	Product findById(long id);
}
