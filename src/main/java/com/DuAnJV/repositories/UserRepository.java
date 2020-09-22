package com.DuAnJV.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.DuAnJV.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO qtht_users (id,email,password,fullname,gender,birthday,address,phone,"
			+ "creatday,creatby,updateday,updateby,isdelete) "
			+ "VALUES(?1,?2,?3,?4,?5,(CAST (CAST(?6 AS character varying) AS date)),?7,?8,?9,?10,?11,?12,?13)", nativeQuery = true)
	void add(Long id,String email,String password,String fullname,Byte gender,Date birthday,String address,String phone,
			Date creatDay,String creatBy,Date updateDay,String updateBy,Byte isDelete);
	
	@Modifying
	@Transactional
	@Query(value = "Insert INTO user_role (userid,roleid) values(?1,?2)", nativeQuery = true)
	void addRoles(Long userid,Long roleid);
	
	@Modifying
	@Transactional
	@Query(value = "Insert INTO user_chucnang (userid,chucnangid) values(?1,?2)", nativeQuery = true)
	void addCNS(Long userid,Long chucnangid);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_role where userid = ?1", nativeQuery = true)
	void deleteRoles(Long userid);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user_chucnang where userid = ?1", nativeQuery = true)
	void deleteCNS(Long userid);
	
	@Modifying
	@Transactional
	@Query(value = "update qtht_users set email=?1,fullname=?2,gender=?3,birthday=(CAST (CAST(?4 AS character varying) AS date))"
			+ ",address=?5,phone=?6,updateday=?7,updateby=?8,isdelete=?9 where id=?10",nativeQuery = true)
	void update(String email,String fullname,Byte gender,Date birthday,String address,String phone,
			Date updateDay,String updateBy,Byte isDelete,Long id);
	
	@Modifying
	@Transactional
	@Query(value = "update qtht_users set updateday=?1,updateby=?2,isdelete=?3 where id=?4",nativeQuery = true)
	void deleteById(Date updateDay,String updateBy,Byte isDelete,Long id);
	
	@Query(value="select * from qtht_users where isdelete = 0 limit ?1 offset ?2",nativeQuery =true)
	List<User> findAll(Integer pagesize, Integer offset);
	
	@Query(value = "select count(*) from qtht_users where isdelete = 0",nativeQuery = true)
	long count();
	
	@Query(value="select * from qtht_users where id = ?1 and isdelete =0",nativeQuery = true)
	User findByid(Long id);
	
	@Query(value="select distinct chucnangid from vaitrovachucnang where roleid in (?1)",nativeQuery=true)
	List<Long> findCNByRole(List<Long> roleid);
	
	@Query(value="select chucnangid from user_chucnang where userid = ?1",nativeQuery = true)
	List<Long> findCNSById(Long id);
	
	@Query(value="select roleid from user_role where userid = ?1",nativeQuery = true)
	List<Long> findRoleById(Long id);
	
	@Query(value="select * from qtht_users where email=?1 and isdelete=0",nativeQuery=true)
	User findByEmail(String email);
	
	@Query(value="select * from qtht_users where email like ?1 and isdelete = 0 limit ?2 offset ?3",nativeQuery=true)
	List<User> findByEmails(String email,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users where email like ?1 and isdelete = 0",nativeQuery=true)
	long countByEmails(String email);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext1_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext1_, query)desc limit ?2 offset ?3",nativeQuery=true)
	List<User> findByFullname(String fullname,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext1_ and isdelete = 0",nativeQuery=true)
	long countByFullname(String fullname);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext2_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext2_, query)desc limit ?2 offset ?3",nativeQuery=true)
	List<User> findByAddress(String address,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext2_ and isdelete = 0",nativeQuery=true)
	long countByAddress(String address);
	
	@Query(value="select * from qtht_users where gender in (?1) and isdelete = 0 limit ?2 offset ?3",nativeQuery=true)
	List<User> findByGender(List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users where gender in (?1) and isdelete = 0",nativeQuery=true)
	long countByGender(List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext1_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext1_, query)desc limit ?3 offset ?4",nativeQuery=true)
	List<User> findByEmailsAndFullname(String fullname,String email,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext1_ and isdelete = 0",nativeQuery=true)
	long countByEmailsAndFullname(String fullname,String email);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext2_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext2_, query)desc limit ?3 offset ?4",nativeQuery=true)
	List<User> findByEmailsAndAddress(String address,String email,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext2_ and isdelete = 0",nativeQuery=true)
	long countByEmailsAndAddress(String address,String email);
	
	@Query(value="select * from qtht_users where email like ?1 and isdelete =0 and gender in (?2) limit ?3 offset ?4",nativeQuery=true)
	List<User> findByEmailsAndGender(String email,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users where email like ?1 and isdelete =0 and gender in (?2)",nativeQuery=true)
	long countByEmailsAndGender(String email,List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext1_, query), ts_rank_cd(fulltext2_, query1) desc limit ?3 offset ?4",nativeQuery=true)
	List<User> findByFullnameAndAddress(String fullname,String address,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0",nativeQuery=true)
	long countByFullnameAndAddress(String fullname,String address);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext1_ and isdelete = 0 and gender in (?2) " + 
			"order by ts_rank_cd(fulltext1_, query) desc limit ?3 offset ?4",nativeQuery=true)
	List<User> findByFullnameAndGender(String fullname,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext1_ and isdelete = 0 and gender in (?2)",nativeQuery=true)
	long countByFullnameAndGender(String fullname,List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext2_ and isdelete = 0 and gender in (?2) " + 
			"order by ts_rank_cd(fulltext2_, query) desc limit ?3 offset ?4",nativeQuery=true)
	List<User> findByAddressAndGender(String address,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where query @@ fulltext2_ and isdelete = 0 and gender in (?2)",nativeQuery=true)
	long countByAddressAndGender(String address,List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where email like ?3 and query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0 " + 
			"order by ts_rank_cd(fulltext1_, query), ts_rank_cd(fulltext2_, query1) desc limit ?4 offset ?5",nativeQuery=true)
	List<User> findByEmailsAndFullnameAndAddress(String fullname,String address,String email,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where email like ?3 and query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0",nativeQuery=true)
	long countByEmailsAndFullnameAndAddress(String fullname,String address,String email);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext1_ and isdelete = 0 and gender in (?3) " + 
			"order by ts_rank_cd(fulltext1_, query)desc limit ?4 offset ?5",nativeQuery=true)
	List<User> findByEmailsAndFullnameAndGender(String fullname,String email,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext1_ and isdelete = 0 and gender in (?3) ",nativeQuery=true)
	long countByEmailsAndFullnameAndGender(String fullname,String email,List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext2_ and isdelete = 0 and gender in (?3) " + 
			"order by ts_rank_cd(fulltext2_, query)desc limit ?4 offset ?5",nativeQuery=true)
	List<User> findByEmailsAndAddressAndGender(String address,String email,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query " + 
			"where email like ?2 and query @@ fulltext2_ and isdelete = 0 and gender in (?3)",nativeQuery=true)
	long countByEmailsAndAddressAndGender(String address,String email,List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0 and gender in (?3) " + 
			"order by ts_rank_cd(fulltext1_, query), ts_rank_cd(fulltext2_, query1) desc limit ?4 offset ?5",nativeQuery=true)
	List<User> findByFullnameAndAddressAndGender(String fullname,String address,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0 and gender in (?3)",nativeQuery=true)
	long countByFullnameAndAddressAndGender(String fullname,String address,List<Byte> gender);
	
	@Query(value="select * from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0 and email like ?3 and gender in (?4) " + 
			"order by ts_rank_cd(fulltext1_, query), ts_rank_cd(fulltext2_, query1) desc limit ?5 offset ?6",nativeQuery=true)
	List<User> findByEmailsAndFullnameAndAddressAndGender(String fullname,String address,String email,List<Byte> gender,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_users, to_tsquery(?1) query, to_tsquery(?2) query1 " + 
			"where query @@ fulltext1_ and query1 @@ fulltext2_ and isdelete = 0 and email like ?3 and gender in (?4)",nativeQuery=true)
	long countByEmailsAndFullnameAndAddressAndGender(String fullname,String address,String email,List<Byte> gender);
}
