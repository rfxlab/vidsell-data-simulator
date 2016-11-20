package com.rfxlab.vidsell.dao;


import java.util.List;

import com.rfxlab.model.db.DbProduct;

import rfx.data.util.cache.CacheConfig;


@CacheConfig( type = CacheConfig.LOCAL_CACHE_ENGINE, keyPrefix = "product:", expireAfter = 6 )
public interface DbProductDao {

    public String save(DbProduct pro);
    public void save(List<DbProduct> products);
    public DbProduct getRandomProduct();
    public List<DbProduct> getRandomProduct(int num);
    public DbProduct getProductById(String id);

}
