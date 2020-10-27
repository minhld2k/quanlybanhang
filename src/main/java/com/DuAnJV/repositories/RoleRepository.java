package com.DuAnJV.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DuAnJV.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRolekeyAndIsdelete(String roleKey, Byte isDelete);
	
	@Query(value = "select * from qtht_roles where isdelete = 0 limit ?1 offset ?2",nativeQuery = true)
	List<Role> findAll(Integer pagesize, Integer offset);
	
	@Query(value = "select count(*) from qtht_roles where isdelete = 0",nativeQuery = true)
	long count();
	
	@Query(value = "select * from qtht_roles, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0" + 
			"order by ts_rank_cd(fulltext_, query)desc limit ?2 offset ?3",nativeQuery = true)
	List<Role> findByRolename(String roleName, Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_roles, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0",nativeQuery = true)
	long countByRolename(String roleName);
	
	@Query(value="select * from qtht_roles where rolekey like ?1 and isdelete = 0 limit ?2 offset ?3",nativeQuery = true)
	List<Role> findAllByRolekey(String rolekey,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_roles where rolekey like ?1 and isdelete = 0",nativeQuery = true)
	long countByRolekey(String rolekey);
	
	@Query(value="select * from qtht_roles, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and rolekey like ?2 " + 
			"order by ts_rank_cd(fulltext_, query)desc limit ?3 offset ?4",nativeQuery = true)
	List<Role> findAllByRolenameAndRolekey(String rolename, String rolekey,Integer pagesize, Integer offset);
	
	@Query(value="select count(*) from qtht_roles, to_tsquery(?1) query " + 
			"where query @@ fulltext_ and isdelete = 0 and rolekey like ?2",nativeQuery = true)
	long countByNameAndKey(String rolename, String rolekey);
	
	@Query(value="select chucnangid from vaitrovachucnang where roleid = ?1",nativeQuery = true)
	List<Long> findCNSById(Long id);
	
	@Query(value="select * from qtht_roles where id =?1",nativeQuery = true)
	Role findByid(Long id);
	
	@Query(value="select r.id,r.rolekey,r.rolename,r.creatday,r.creatby,r.updateby,r.updateday,r.isdelete from qtht_roles r " + 
			"	inner join user_role ur on ur.roleid = r.id " + 
			"where ur.userid = ? and r.isdelete = 0", nativeQuery =true)
	List<Role> findAllRoleByUserId(long id);
}
