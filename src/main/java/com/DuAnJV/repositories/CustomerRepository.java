package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long >{
	
	@Query(value="select * from qtht_customers where isdelete = 0 limit ?1 offset ?2",nativeQuery =true)
	List<Customer> findAll(Integer pagesize, Integer offset);
	
	@Query(value = "select count(*) from qtht_customers where isdelete = 0",nativeQuery = true)
	long count();
	
	@Query(value="select * from qtht_customers where id = ?1 and isdelete =0",nativeQuery = true)
	Customer findByid(Long id);
	
	@Query(value="select * from qtht_customers where email=?1 and isdelete=0",nativeQuery=true)
	Customer findByEmail(String email);
}
