package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	
	@Query(value = "SELECT * FROM carts WHERE isdelete = 0 LIMIT ? OFFSET ? ", nativeQuery = true)
	List<Cart> findAll(int limit, int offset);
	
	@Query(value = " SELECT COUNT(*) FROM carts where isdelete = 0 " , nativeQuery = true)
	long countAll();
	
	@Query(value = "SELECT * from carts where isdelete = 0 and id = ? " , nativeQuery = true)
	Cart findById(long id);

}
