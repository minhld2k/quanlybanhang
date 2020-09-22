package com.DuAnJV.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.DuAnJV.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO qlsp_products " + 
			"	(id, tensanpham, soluong, giatien, ram, manhinh, trangthai, creatby, creatday, updateby, updateday, isdelete, hangsxid, categoryid, image, mota) " + 
			"	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",nativeQuery = true)
	void add(Long id,String tensanpham,Integer soluong,double giatien,String ram,String manhinh
			,Integer trangthai,String creatby,Date creatday,String updateby,Date updateDay,Byte isdelete
			,Long hangsxid,Long categoryid,String image, String mota);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE qlsp_products SET "
			+ " tensanpham=?, soluong=?, giatien=?, ram=?, manhinh=?, trangthai=?,updateby=?, updateday=?, isdelete=?, hangsxid=?, categoryid=?, image=?, mota=? "
			+ "WHERE id=?",nativeQuery = true)
	void update(String tensanpham,Integer soluong,double giatien,String ram,String manhinh
			,Integer trangthai,String updateby,Date updateDay,Byte isdelete
			,Long hangsxid,Long categoryid,String image, String mota,Long id);
	
	@Query(value="SELECT * FROM qlsp_products where isdelete = 0 limit ? offset ?",nativeQuery = true)
	List<Product> findAll(Integer limit,Integer offset);
	
	@Query(value=" SELECT count(*) from qlsp_products where isdelete = 0",nativeQuery = true)
	long countAll();
	
	@Query(value="select * from qlsp_products where id = ? and isdelete = 0",nativeQuery = true)
	Product findById(long id);
}
