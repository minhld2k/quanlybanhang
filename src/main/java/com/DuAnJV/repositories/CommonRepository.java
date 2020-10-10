package com.DuAnJV.repositories;

import java.util.ArrayList;		
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class CommonRepository {
	@Autowired
	public JdbcTemplate _jdbcTemplate;
	
	public List<Object[]> findAllProduct(String tensanpham,List<Long> category,List<Long> hangsx,List<Integer> trangthai,int limit ,int offset){
		List<Object[]> lsProduct = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		str.append(" SELECT ")
		   .append("	id, image, tensanpham, soluong, giatien, categoryid, hangsxid, trangthai ")
		   .append(" FROM qlsp_products ");
		   
		if (!tensanpham.isEmpty()) {
			str.append(" , to_tsquery('"+tensanpham+"') query ")
			   .append(" WHERE query @@ fulltext_ AND isdelete = 0 ");
		}else {
			str.append(" WHERE isdelete = 0 ");
		}
		
		if (category != null) {
			str.append(" 	AND categoryid IN ("+category+") ");
		}
		if (hangsx != null) {
			str.append(" 	AND hangsxid IN ("+hangsx+") ");
		}
		if (trangthai != null) {
			str.append(" 	AND trangthai IN ("+trangthai+") ");
		}
		if (!tensanpham.isEmpty()) {
			str.append(" ORDER BY ts_rank_cd(fulltext_, query)desc ");
		}else {
			str.append(" ORDER BY creatday desc ");
		}
		str.append(" LIMIT "+limit+" OFFSET "+offset);
		
		System.out.println("=> "+str.toString());
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(str.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[8];
			objects[0] = map.get("id").toString();
			objects[1] = map.get("image").toString();
			objects[2] = map.get("tensanpham").toString();
			objects[3] = map.get("soluong").toString();
			objects[4] = map.get("giatien").toString();
			objects[5] = map.get("categoryid").toString();
			objects[6] = map.get("hangsxid").toString();
			objects[7] = map.get("trangthai").toString();
			lsProduct.add(objects);
		}
		
		return lsProduct;
	}
	
	public long countAllProduct(String tensanpham,List<Long> category,List<Long> hangsx,List<Integer> trangthai) {
		StringBuilder str = new StringBuilder();
		str.append(" SELECT COUNT(*) ")
		   .append(" FROM qlsp_products ");
		   
		if (!tensanpham.isEmpty()) {
			str.append(" , to_tsquery('"+tensanpham+"') query ")
			   .append(" WHERE query @@ fulltext_ AND isdelete = 0 ");
		}else {
			str.append(" WHERE isdelete = 0 ");
		}
		
		if (category != null) {
			str.append(" 	AND categoryid IN ("+category+") ");
		}
		if (hangsx != null) {
			str.append(" 	AND hangsxid IN ("+hangsx+") ");
		}
		if (trangthai != null) {
			str.append(" 	AND trangthai IN ("+trangthai+") ");
		}
		
		return _jdbcTemplate.queryForObject(str.toString(), Long.class);
	}
	
}
