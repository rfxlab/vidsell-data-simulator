package com.rfxlab.vidsell.dao;


import com.rfxlab.model.db.DbSellingStore;

import rfx.data.util.cache.CacheConfig;


@CacheConfig( type = CacheConfig.LOCAL_CACHE_ENGINE, keyPrefix = "store:", expireAfter = 6 )
public interface DbSellingStoreDao {
    public long save(DbSellingStore s);
}
