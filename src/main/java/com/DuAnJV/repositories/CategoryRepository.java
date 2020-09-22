package com.DuAnJV.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.DuAnJV.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO qtht_categories (id,categoryname,categorykey,creatday,"
			+ "creatby,updateday,updateby,isdelete) VALUES(?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
	void add(Long category_id,String category_name,String category_key, Date creat_day,
			String creat_by,Date update_day,String update_by,Byte is_delete);
	
	@Modifying
	@Transactional
	@Query(value = "update qtht_categories set categoryname=?1,categorykey=?2,updateday=?3,"
			+ "updateby=?4,isdelete=?5 where id=?6",nativeQuery = true)
	int update(String category_name,String category_key,Date update_day,
			String update_by,Byte is_delete,Long category_id);
	
	@Query(value="SELECT id, categorykey, categoryname, creatby, creatday, updateby, updateday, isdelete " + 
			"	FROM qtht_categories WHERE isdelete = 0 limit ?1 offset ?2",nativeQuery = true)
	List<Category> findAll(int limit,int offset);
	
	@Query(value="SELECT count(*) FROM qtht_categories WHERE isdelete = 0",nativeQuery = true)
	long countAll();
	
	@Query(value="SELECT id, categorykey, categoryname, creatby, creatday, updateby, updateday, isdelete " + 
			"	FROM qtht_categories WHERE isdelete = 0 and id = ?",nativeQuery = true)
	Category findById(long id);
	
	@Query(value="SELECT id, categorykey, categoryname, creatby, creatday, updateby, updateday, isdelete " + 
			"	FROM qtht_categories WHERE isdelete = 0 and categorykey = ?",nativeQuery = true)
	Category findByKey(String categorykey);
	
	@Query(value="SELECT id, categorykey, categoryname, creatby, creatday, updateby, updateday, isdelete " + 
			"	FROM qtht_categories WHERE isdelete = 0 and categorykey like ?1 limit ?2 offset ?3 ",nativeQuery = true)
	List<Category> findAllByKey(String categorykey,int limit,int offset);
	
	@Query(value="SELECT count(*) FROM qtht_categories WHERE isdelete = 0 and categorykey like ? ",nativeQuery = true)
	long countByKey(String categorykey);
	
	@Query(value="SELECT id, categorykey, categoryname, creatby, creatday, updateby, updateday, isdelete from qtht_categories, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext_, query)desc limit ?2 offset ?3",nativeQuery = true)
	List<Category> findAllByName(String categoryname,int limit,int offset);
	
	@Query(value="SELECT count(*) from qtht_categories, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0",nativeQuery = true)
	long countByName(String categoryname);
	
	@Query(value="SELECT id, categorykey, categoryname, creatby, creatday, updateby, updateday, isdelete from qtht_categories, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and categorykey like ?2 " + 
			"order by ts_rank_cd(fulltext_, query)desc limit ?3 offset ?4",nativeQuery = true)
	List<Category> findAllByNameAndKey(String categoryname,String categorykey,int limit,int offset);
	
	@Query(value="SELECT count(*) from qtht_categories, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and categorykey like ?2",nativeQuery = true)
	long countByNameAndKey(String categoryname,String categorykey);
}
