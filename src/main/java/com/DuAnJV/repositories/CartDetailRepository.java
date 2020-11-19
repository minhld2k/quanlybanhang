package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>{
	@Query(value = "SELECT * FROM cart_detail WHERE cartid = ? ", nativeQuery = true)
	List<CartDetail> findAllByCartId(long cartid);
	
	@Query(value = "SELECT * from cart_detail where id = ? " , nativeQuery = true)
	CartDetail findById(long id);
}
