package com.rfxlab.vidsell.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rfxlab.model.db.DbSellingOrder;

import rfx.data.util.sql.CommonSpringDAO;
import rfx.data.util.sql.SqlTemplateString;
import rfx.data.util.sql.SqlTemplateUtil;

public class DbSellingOrderDaoImpl extends CommonSpringDAO implements DbSellingOrderDao {


	@SqlTemplateString
	String SQL_insert = SqlTemplateUtil.getSql("SQL.selling_order.insert");
	private final int INSERT_BATCH_SIZE = 20;

	@Override
	public long save(DbSellingOrder o) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTpl.update(con -> {
			PreparedStatement ps = con.prepareStatement(SQL_insert, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, o.getProductId());
			ps.setLong(2, o.getCustomerId());
			ps.setDate(3, new Date(o.getOrderDate().getTime()));
			ps.setInt(4, o.getQuantity());
			ps.setLong(5, o.getRevenue());
			ps.setFloat(6, o.getDiscount());
			return ps;
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	public void save(List<DbSellingOrder> orders) {
		for (int i = 0; i < orders.size(); i += INSERT_BATCH_SIZE) {
			final List<DbSellingOrder> batchList = orders.subList(i,
					i + INSERT_BATCH_SIZE > orders.size() ? orders.size() : i + INSERT_BATCH_SIZE);

			jdbcTpl.batchUpdate(SQL_insert, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int j) throws SQLException {
					DbSellingOrder o = batchList.get(j);
					ps.setString(1, o.getProductId());
					ps.setLong(2, o.getCustomerId());
					ps.setDate(3, new Date(o.getOrderDate().getTime()));
					ps.setInt(4, o.getQuantity());
					ps.setLong(5, o.getRevenue());
					ps.setFloat(6, o.getDiscount());
				}

				@Override
				public int getBatchSize() {
					return batchList.size();
				}
			});
		}
	}
	
}
