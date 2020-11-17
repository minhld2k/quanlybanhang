package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Giohang;

public interface GioHangRepository extends JpaRepository<Giohang, Long>{
	
	@Query(value="select * from qlsp_giohangs WHERE customerid = ? AND trangthai = 0 ",nativeQuery =true)
	List<Giohang> findAll(long customerid,int limit, int offset);
	
	@Query(value = "select count(*) from qlsp_giohangs WHERE trangthai = 0 AND customerid = ? ",nativeQuery = true)
	long count(long customerid);
	
	@Query(value="select * from qlsp_giohangs where trangthai = 0 AND id = ?1 ",nativeQuery = true)
	Giohang findById(long id);
}
