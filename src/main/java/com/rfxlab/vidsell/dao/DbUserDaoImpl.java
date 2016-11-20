package com.rfxlab.vidsell.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rfxlab.model.db.DbUser;

import rfx.data.util.sql.CommonSpringDAO;
import rfx.data.util.sql.SqlTemplateString;
import rfx.data.util.sql.SqlTemplateUtil;

public class DbUserDaoImpl extends CommonSpringDAO implements DbUserDao {

	@SqlTemplateString
	String SQL_insert = SqlTemplateUtil.getSql("SQL.user.insert");
	
	@SqlTemplateString
	String SQL_getIdByEmail = SqlTemplateUtil.getSql("SQL.user.get_id_by_email");
	
	private final int INSERT_BATCH_SIZE = 20;
	
	@Override
	public long getIdByEmail(String email){
		Long id = jdbcTpl.query(SQL_getIdByEmail, new ResultSetExtractor<Long>(){
			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.first()){
					return rs.getLong("user_id");	
				}
				return 0L;
			}
		}, email);
		return id;
	}

	@Override
	public long save(DbUser u) {
		long id = getIdByEmail(u.getEmail());
		if(id == 0){
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTpl.update(con -> {
				PreparedStatement ps = con.prepareStatement(SQL_insert, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, u.getName());
				ps.setString(2, u.getEmail());
				ps.setInt(3, u.getAge());
				ps.setInt(4, u.getGender());
				ps.setString(5, u.getLocation());
				ps.setDate(6, new Date(u.getCreationDate().getTime()));
				ps.setDate(7, new Date(u.getModification().getTime()));

				return ps;
			}, keyHolder);
			id = keyHolder.getKey().longValue();
	
		}
		return id;
	}

	public void save(List<DbUser> users) {
		for (int i = 0; i < users.size(); i += INSERT_BATCH_SIZE) {
			final List<DbUser> batchList = users.subList(i,
					i + INSERT_BATCH_SIZE > users.size() ? users.size() : i + INSERT_BATCH_SIZE);

			jdbcTpl.batchUpdate(SQL_insert, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int j) throws SQLException {
					DbUser u = batchList.get(j);
					ps.setString(1, u.getName());
					ps.setString(2, u.getEmail());
					ps.setInt(3, u.getAge());
					ps.setInt(4, u.getGender());
					ps.setString(5, u.getLocation());
					ps.setTimestamp(6, new Timestamp(u.getCreationDate().getTime()));
					ps.setTimestamp(7, new Timestamp(u.getModification().getTime()));
				}

				@Override
				public int getBatchSize() {
					return batchList.size();
				}
			});
		}
	}
	
	public DbUser getRandomUser(){
		String sql = "SELECT * FROM user ORDER BY RAND() LIMIT 1";		
		try {
			return jdbcTpl.query(sql, new DbUserRowMapper()).get(0);
		} catch (Exception e) {}
		return null;
	}

	public static class DbUserRowMapper implements RowMapper<DbUser> {
		@Override
		public DbUser mapRow(ResultSet rs, int rowNum) throws SQLException {			
			DbUser u = new DbUser(rs.getLong("user_id"), 
					rs.getString("name"), 
					rs.getString("email"), 
					rs.getInt("age"), 
					rs.getInt("gender"),
					rs.getString("location"), 
					rs.getDate("creation_date"), 
					rs.getDate("modification"));
			return u;
		}
	}
}
