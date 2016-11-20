package com.rfxlab.vidsell.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.rfxlab.model.db.DbVideo;

import rfx.data.util.sql.CommonSpringDAO;
import rfx.data.util.sql.SqlTemplateString;
import rfx.data.util.sql.SqlTemplateUtil;

public class DbVideoDaoImpl extends CommonSpringDAO implements DbVideoDao {


	@SqlTemplateString
	String SQL_insert = SqlTemplateUtil.getSql("SQL.video.insert");
	private final int INSERT_BATCH_SIZE = 20;

	@Override
	public String save(DbVideo v) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int n = 0;
		try {
			n = jdbcTpl.update(con -> {
				PreparedStatement ps = con.prepareStatement(SQL_insert, Statement.RETURN_GENERATED_KEYS);

				ps.setString(1, v.getVideo_id());
				ps.setString(2, v.getUrl() );
				ps.setString(3, v.getCategory());
				ps.setString(4, v.getTitle());
				ps.setString(5, v.getDescription());
				ps.setString(6, v.getThumbnailImage());			
				ps.setTimestamp(7, new Timestamp(v.getPublishedTime().getTime()) );
				ps.setString(8, v.getChannelId());
				ps.setString(9, v.getChannelTitle());
				ps.setString(10, v.getProductId());			

				return ps;
			}, keyHolder);
		} catch (Exception e) {}

		return n > 0 ? v.getVideo_id() : "";
	}

	public void save(List<DbVideo> videos) {
		for (int i = 0; i < videos.size(); i += INSERT_BATCH_SIZE) {
			final List<DbVideo> batchList = videos.subList(i,
					i + INSERT_BATCH_SIZE > videos.size() ? videos.size() : i + INSERT_BATCH_SIZE);

			jdbcTpl.batchUpdate(SQL_insert, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int j) throws SQLException {
					DbVideo v = batchList.get(j);
					ps.setString(1, v.getVideo_id());
					ps.setString(2, v.getUrl() );
					ps.setString(3, v.getCategory());
					ps.setString(4, v.getTitle());
					ps.setString(5, v.getDescription());
					ps.setString(6, v.getThumbnailImage());			
					ps.setTimestamp(7, new Timestamp(v.getPublishedTime().getTime()) );
					ps.setString(8, v.getChannelId());
					ps.setString(9, v.getChannelTitle());
					ps.setString(10, v.getProductId());		
				}

				@Override
				public int getBatchSize() {
					return batchList.size();
				}
			});
		}
	}
	
	public static class DbVideoRowMapper implements RowMapper<DbVideo> {
		@Override
		public DbVideo mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return new DbVideo(rs.getString("video_id"),rs.getString("url"), rs.getString("product_id"));
		}
	}
	
	public List<DbVideo> getRandomVideos(int num){
		if(num <= 0){
			num = 10;
		}
		String sql = "SELECT * FROM video ORDER BY RAND() LIMIT "+num;
		try {
			return jdbcTpl.query(sql, new DbVideoRowMapper());
		} catch (Exception e) {}
		return new ArrayList<>(0);
	}

}
