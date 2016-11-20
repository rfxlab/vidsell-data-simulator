package com.rfxlab.vidsell.dao;


import java.util.List;

import com.rfxlab.model.db.DbUser;

import rfx.data.util.cache.CacheConfig;


@CacheConfig( type = CacheConfig.LOCAL_CACHE_ENGINE, keyPrefix = "user:", expireAfter = 6 )
public interface DbUserDao {
    public long save(DbUser user);
    public void save(List<DbUser> users);
    public long getIdByEmail(String email);
    
    public DbUser getRandomUser();
    
}
