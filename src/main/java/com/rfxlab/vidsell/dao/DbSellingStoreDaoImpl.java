package com.rfxlab.vidsell.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rfxlab.model.db.DbSellingStore;

import rfx.data.util.sql.CommonSpringDAO;
import rfx.data.util.sql.SqlTemplateString;
import rfx.data.util.sql.SqlTemplateUtil;

public class DbSellingStoreDaoImpl extends CommonSpringDAO implements DbSellingStoreDao{

	
	@SqlTemplateString
	String SQL_insert = SqlTemplateUtil.getSql("SQL.selling_store.insert");	

	@Override
	public long save(DbSellingStore s) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTpl.update(con -> {
			PreparedStatement ps = con.prepareStatement(SQL_insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, s.getName());
			ps.setLong(2, s.getUserId());
			ps.setString(3, s.getLocation());
			return ps;
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}	

}
