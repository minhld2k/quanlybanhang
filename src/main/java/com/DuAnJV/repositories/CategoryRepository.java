package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

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
