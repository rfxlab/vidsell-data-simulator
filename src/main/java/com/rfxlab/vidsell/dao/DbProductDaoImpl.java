package com.rfxlab.vidsell.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rfxlab.model.db.DbProduct;

import rfx.data.util.sql.CommonSpringDAO;
import rfx.data.util.sql.SqlTemplateString;
import rfx.data.util.sql.SqlTemplateUtil;

public class DbProductDaoImpl extends CommonSpringDAO implements DbProductDao {

	@SqlTemplateString
	String SQL_insert = SqlTemplateUtil.getSql("SQL.product.insert");
	
	@SqlTemplateString
	String SQL_get_random = SqlTemplateUtil.getSql("SQL.product.get_random");
	
	@SqlTemplateString
	String SQL_get_by_id = SqlTemplateUtil.getSql("SQL.product.get_by_id");
	
	
	private final int INSERT_BATCH_SIZE = 20;

	@Override
	public String save(DbProduct p) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int n = 0;
		try {
			n= jdbcTpl.update(con -> {
				PreparedStatement ps = con.prepareStatement(SQL_insert, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, p.getProduct_id());
				ps.setString(2, p.getName());
				ps.setString(3, p.getDescription());
				ps.setInt(4, p.getStatus());
				ps.setTimestamp(5, new Timestamp(p.getCreationTime().getTime()));
				ps.setTimestamp(6, new Timestamp(p.getUpdate_time().getTime()));			
				ps.setInt(7, p.getPrice());			
				ps.setString(8, p.getProduct_type());
				ps.setString(9, p.getBrand());
				ps.setString(10, p.getSubcategory());
				ps.setString(11, p.getCategory());
				ps.setLong(12, p.getStore_id());
				return ps;
			}, keyHolder);
		} catch (Exception e) {
			if( ! (e instanceof DuplicateKeyException) ){
				e.printStackTrace();	
			}
		}

		return n > 0 ? p.getProduct_id() : "";
	}

	public void save(List<DbProduct> products) {
		for (int i = 0; i < products.size(); i += INSERT_BATCH_SIZE) {
			final List<DbProduct> batchList = products.subList(i,
					i + INSERT_BATCH_SIZE > products.size() ? products.size() : i + INSERT_BATCH_SIZE);

			try {
				jdbcTpl.batchUpdate(SQL_insert, new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int j) throws SQLException {
						DbProduct p = batchList.get(j);
						ps.setString(1, p.getProduct_id());
						ps.setString(2, p.getName());
						ps.setString(3, p.getDescription());
						ps.setInt(4, p.getStatus());
						ps.setTimestamp(5, new Timestamp(p.getCreationTime().getTime()));
						ps.setTimestamp(6, new Timestamp(p.getUpdate_time().getTime()));			
						ps.setInt(7, p.getPrice());			
						ps.setString(8, p.getProduct_type());
						ps.setString(9, p.getBrand());
						ps.setString(10, p.getSubcategory());
						ps.setString(11, p.getCategory());
						ps.setLong(12, p.getStore_id());
					}

					@Override
					public int getBatchSize() {
						return batchList.size();
					}
				});
			} catch (Exception e) {
				if( ! (e instanceof DuplicateKeyException) ){
					e.printStackTrace();	
				}
			}
		}
	}
	
	public static class DbProductRowMapper implements RowMapper<DbProduct> {
		@Override
		public DbProduct mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return new DbProduct(rs.getString("product_id"),rs.getInt("price"), rs.getString("name"),rs.getString("category"),rs.getLong("owner_id"));
		}
	}
	
	public DbProduct getRandomProduct(){
		List<DbProduct>  rs = getRandomProduct(1);
		return rs.size()>0 ? rs.get(0) : null;
	}
	
	public List<DbProduct> getRandomProduct(int num){
		if(num<=0){
			num = 1;
		}		
		try {
			return jdbcTpl.query(SQL_get_random, new DbProductRowMapper(), num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>(0);
	}
	
	public DbProduct getProductById(String id){
		try {
			return jdbcTpl.queryForObject(SQL_get_by_id, new DbProductRowMapper(), id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
