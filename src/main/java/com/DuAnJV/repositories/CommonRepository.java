package com.DuAnJV.repositories;

import java.util.ArrayList;		
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.DuAnJV.common.replaceDemo;

@Repository
public class CommonRepository {
	@Autowired
	public JdbcTemplate _jdbcTemplate;
	
	public List<Object[]> findAllProduct(String tensanpham,List<Long> category,List<Long> hangsx,List<Integer> trangthai,int limit ,int offset){
		List<Object[]> lsProduct = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		str.append(" SELECT ")
		   .append("	pro.id, pro.image, pro.tensanpham, pro.soluong, pro.giatien, ")
		   .append("	pro.categoryid, pro.hangsxid, pro.trangthai,hsx.name,cate.categoryname	")
		   .append(" FROM qlsp_products pro ")
		   .append("	LEFT JOIN qtht_hangsx hsx on pro.hangsxid = hsx.id ")
		   .append("	LEFT JOIN qtht_categories cate on cate.id = pro.categoryid ")
		   .append(" WHERE pro.isdelete = 0 ");
		if (!tensanpham.isEmpty()) {
			str.append(" AND to_tsquery('"+tensanpham+"') @@ pro.fulltext_ ");
		}
		
		if (category != null) {
			str.append(" 	AND pro.categoryid IN ("+replaceDemo.convertFromListLongToString(category)+") ");
		}
		if (hangsx != null) {
			str.append(" 	AND pro.hangsxid IN ("+replaceDemo.convertFromListLongToString(hangsx)+") ");
		}
		if (trangthai != null) {
			str.append(" 	AND pro.trangthai IN ("+replaceDemo.convertFromListIntToString(trangthai)+") ");
		}
		if (!tensanpham.isEmpty()) {
			str.append(" ORDER BY ts_rank_cd(pro.fulltext_, to_tsquery('"+tensanpham+"'))desc ");
		}else {
			str.append(" ORDER BY pro.creatday desc ");
		}
		str.append(" LIMIT "+limit+" OFFSET "+offset);
		
		List<Map<String, Object>> rows = _jdbcTemplate.queryForList(str.toString());
		for (Map<String, Object> map : rows) {
			Object[] objects = new Object[10];
			objects[0] = Integer.parseInt(map.get("id").toString());
			objects[1] = map.get("image").toString();
			objects[2] = map.get("tensanpham").toString();
			objects[3] = map.get("soluong").toString();
			objects[4] = map.get("giatien").toString();
			objects[5] = map.get("categoryid").toString();
			objects[6] = map.get("hangsxid").toString();
			objects[7] = map.get("trangthai").toString();
			objects[8] = map.get("name").toString();
			objects[9] = map.get("categoryname").toString();
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
			str.append(" 	AND categoryid IN ("+replaceDemo.convertFromListLongToString(category)+") ");
		}
		if (hangsx != null) {
			str.append(" 	AND hangsxid IN ("+replaceDemo.convertFromListLongToString(hangsx)+") ");
		}
		if (trangthai != null) {
			str.append(" 	AND trangthai IN ("+replaceDemo.convertFromListIntToString(trangthai)+") ");
		}
		
		return _jdbcTemplate.queryForObject(str.toString(), Long.class);
	}
	
}
