package com.rfxlab.vidsell.dao;


import java.util.List;

import com.rfxlab.model.db.DbSellingOrder;
import com.rfxlab.model.db.DbUser;

import rfx.data.util.cache.CacheConfig;


@CacheConfig( type = CacheConfig.LOCAL_CACHE_ENGINE, keyPrefix = "order:", expireAfter = 6 )
public interface DbSellingOrderDao {
    public long save(DbSellingOrder order);
    public void save(List<DbSellingOrder> orders);
    
}
