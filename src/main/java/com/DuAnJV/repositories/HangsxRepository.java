package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Hangsx;

public interface HangsxRepository extends JpaRepository<Hangsx, Long> {
			
	@Query(value="SELECT id, name, image, creatday, creatby, updateday, updateby, isdelete, key " + 
			"	FROM qtht_hangsx WHERE isdelete = 0 limit ?1 offset ?2",nativeQuery = true)
	List<Hangsx> findAll(int limit,int offset);
	
	@Query(value="SELECT count(*) FROM qtht_hangsx WHERE isdelete = 0",nativeQuery = true)
	long countAll();
	
	@Query(value="SELECT id, name, image, creatday, creatby, updateday, updateby, isdelete, key " + 
			"	FROM qtht_hangsx WHERE isdelete = 0 and id = ?",nativeQuery = true)
	Hangsx findById(long id);
	
	@Query(value="SELECT id, name, image, creatday, creatby, updateday, updateby, isdelete, key " + 
			"	FROM qtht_hangsx WHERE isdelete = 0 and key = ?",nativeQuery = true)
	Hangsx findByKey(String categorykey);
	
	@Query(value="SELECT categoryid FROM category_hangsx WHERE hangsxid = ?",nativeQuery = true)
	List<Long> findCategoryById(Long id);
	
	@Query(value="select id, name, image, creatday, creatby, updateday, updateby, isdelete, key from qtht_hangsx, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext_, query)desc limit ?2 offset ?3", nativeQuery = true)
	List<Hangsx> findAllByName(String name,int limit,int offset);
	
	@Query(value="select count(*) from qtht_hangsx, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0", nativeQuery = true)
	long countAllByName(String name);
	
	@Query(value="select distinct id, name, image, creatday, creatby, updateday, updateby, isdelete, key from qtht_hangsx hsx " + 
			"left join category_hangsx ch on hsx.id = ch.hangsxid where categoryid in (?1) and isdelete=0 limit ?2 offset ?3", nativeQuery = true)
	List<Hangsx> findAllByDM(List<Long> cateroryid,int limit,int offset);
	
	@Query(value="select distinct count(*) from qtht_hangsx hsx " + 
			"left join category_hangsx ch on hsx.id = ch.hangsxid where categoryid in (?1) and isdelete=0", nativeQuery = true)
	long countAllByDM(List<Long> cateroryid);
	
	@Query(value="select distinct id, name, image, creatday, creatby, updateday, updateby, isdelete, key " +
			"from qtht_hangsx hsx left join category_hangsx ch on hsx.id = ch.hangsxid, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and categoryid in (?2) limit ?3 offset ?4", nativeQuery = true)
	List<Hangsx> findAllByNameAndDM(String name,List<Long> cateroryid,int limit,int offset);
	
	@Query(value="select distinct count(*) from qtht_hangsx hsx left join category_hangsx ch on hsx.id = ch.hangsxid, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and categoryid in (?2)", nativeQuery = true)
	long countAllByNameAndDM(String name,List<Long> cateroryid);

}
