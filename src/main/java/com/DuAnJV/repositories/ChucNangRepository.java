package com.DuAnJV.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.DuAnJV.models.Chucnang;

public interface ChucNangRepository extends JpaRepository<Chucnang, Long> {
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO qtht_chucnangs (id,name,key,creatday,"
			+ "creatby,updateday,updateby,isdelete,chucnangchaid,url) VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
	void add(Long id, String name,String key, Date creatDay, String creatBy,
			Date updateDay, String updateBy, Byte isDelete, Long chucnangchaid,String url);

	@Modifying
	@Transactional
	@Query(value = "update qtht_chucnangs set name=?1,key=?2,updateday=?3,"
			+ "updateby=?4,isdelete=?5,chucnangchaid=?6,url=?7 where id=?8", nativeQuery = true)
	int update(String name,String key, Date updateDay, String updateBy, Byte isDelete,Long chucnangchaid,String url, Long id);
	
	@Query(value = "select * from qtht_chucnangs, to_tsquery(?1) query "
			+ "where query @@ fulltext_ and isdelete = ?2 "
			+ "order by ts_rank_cd(fulltext_, query)desc,"
			+ "CASE WHEN chucnangchaid < 0 THEN -2 ELSE -1END  limit ?3 offset ?4",nativeQuery = true)
	List<Chucnang> findByNameAndIsdelete(String name, Byte isdelete,Integer pagesize, Integer offset);
	
	@Query(value = "SELECT count(*) FROM qtht_chucnangs, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = ?2",nativeQuery = true)
	long countByName(String name, Byte isdelete);
	
	@Query(value = "select * from qtht_chucnangs where chucnangchaid < 0 or chucnangchaid is null "
			+ "and isdelete = 0 order by id",nativeQuery = true)
	List<Chucnang> findAllChucnangcha();
	
	@Query(value = "WITH RECURSIVE tree(id,name,chucnangchaid,url,key,isdelete,creatby,creatday,updateby,updateday,path,depth) AS \r\n" + 
			"(\r\n" + 
			"  	SELECT id,name,chucnangchaid,url,key,isdelete,creatby,creatday,updateby,updateday,array[id] as path, 1 as depth\r\n" + 
			"  	FROM qtht_chucnangs WHERE chucnangchaid < 0\r\n" + 
			"  	UNION ALL\r\n" + 
			"  	SELECT cn.id,cn.name,cn.chucnangchaid,cn.url,cn.key,cn.isdelete,cn.creatby,cn.creatday,cn.updateby,cn.updateday,tree.path || cn.id,tree.depth + 1 as depth\r\n" + 
			"  	FROM qtht_chucnangs cn INNER JOIN tree ON cn.chucnangchaid = tree.id\r\n" + 
			")\r\n" + 
			"SELECT id,CASE WHEN chucnangchaid > 0 THEN concat('--- ',name) ELSE name END,key,url,chucnangchaid,isdelete,creatby,creatday,updateby,updateday\r\n" + 
			"FROM tree where isdelete = 0 order by path limit ?1 offset ?2", nativeQuery = true)
	List<Chucnang> findAll(Integer pagesize, Integer offset);
	
	@Query(value = "select count(*) from qtht_chucnangs where not chucnangchaid is null and isdelete = 0",nativeQuery = true)
	long count();
	
	@Query(value = "select count(*) from qtht_chucnangs where chucnangchaid =? and isdelete = 0",nativeQuery = true)
	long count(Long id);
	
	@Query(value = "select distinct key from qtht_chucnangs where not key is null and isdelete = 0",nativeQuery = true)
	List<String> AllKey();
	
	@Query(value = "select * from qtht_chucnangs where key like ?1 and isdelete = 0 limit ?2 offset ?3",nativeQuery = true)
	List<Chucnang> findAllByKey(String key,Integer pagesize, Integer offset);
	
	@Query(value = "select count(*) from qtht_chucnangs where key like ?1 and isdelete = 0",nativeQuery = true)
	long countByKey(String key);
	
	@Query(value = "select * from qtht_chucnangs, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and key like ?2 "
			+ "order by ts_rank_cd(fulltext_, query)desc,"
			+ "CASE WHEN chucnangchaid < 0 THEN -2 ELSE -1 END  limit ?3 offset ?4",nativeQuery = true)
	List<Chucnang> findAllByNameAndKey(String name, String key,Integer pagesize, Integer offset);
	
	@Query(value = "select count(*) from qtht_chucnangs, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and key like ?2",nativeQuery = true)
	long countByNameAndKey(String name, String key);
	
	@Query(value="select * from qtht_chucnangs where key=?1 and isdelete = 0",nativeQuery = true)
	Chucnang findByKey(String key);
	
	@Query(value="WITH RECURSIVE tree(id,name,chucnangchaid,url,key,isdelete,creatby,creatday,updateby,updateday,path,depth) AS \r\n" + 
			"(\r\n" + 
			"	select distinct cn.id,cn.name,cn.chucnangchaid,cn.url,cn.key,cn.isdelete,cn.creatby\r\n" + 
			"		,cn.creatday,cn.updateby,cn.updateday,array[cn.id] as path, 1 as depth\r\n" + 
			"	FROM qtht_chucnangs cn \r\n" + 
			"	where cn.id in\r\n" + 
			"	(select distinct CASE WHEN cn.chucnangchaid < 0 then cn.id ELSE cn.chucnangchaid END " + 
			"		FROM qtht_chucnangs cn\r\n" + 
			"		LEFT JOIN vaitrovachucnang rcn ON cn.id = rcn.chucnangid\r\n" + 
			"		LEFT JOIN user_chucnang ucn ON cn.id = ucn.chucnangid \r\n" + 
			"		LEFT JOIN user_role ur ON ur.roleid = rcn.roleid\r\n" + 
			"		LEFT JOIN qtht_users u ON u.id=ucn.userid or u.id = ur.userid\r\n" + 
			"		WHere u.email = ?)\r\n" + 
			"	UNION ALL \r\n" + 
			"	SELECT distinct cn.id,cn.name,cn.chucnangchaid,cn.url,cn.key,cn.isdelete,cn.creatby\r\n" + 
			"		,cn.creatday,cn.updateby,cn.updateday,tree.path || cn.id,tree.depth + 1 as depth \r\n" + 
			"	FROM qtht_users u\r\n" + 
			"	LEFT JOIN user_role ur ON ur.userid=u.id\r\n" + 
			"	LEFT JOIN vaitrovachucnang rcn ON ur.roleid = rcn.roleid \r\n" + 
			"	LEFT JOIN user_chucnang ucn ON u.id = ucn.userid \r\n" + 
			"	LEFT JOIN qtht_chucnangs cn ON cn.id=rcn.chucnangid or ucn.chucnangid = cn.id\r\n" + 
			"	INNER JOIN tree ON cn.chucnangchaid = tree.id and u.email = ? \r\n" + 
			") \r\n" + 
			"SELECT id,name,key,url,chucnangchaid,isdelete,creatby,creatday,updateby,updateday\r\n" + 
			"FROM tree where isdelete = 0 order by path",nativeQuery = true)
	List<Chucnang> findAllChucnangByEmail(String email,String email1);
}
