package com.rfxlab.vidsell.dao;


import java.util.List;

import com.rfxlab.model.db.DbVideo;

import rfx.data.util.cache.CacheConfig;


@CacheConfig( type = CacheConfig.LOCAL_CACHE_ENGINE, keyPrefix = "video:", expireAfter = 6 )
public interface DbVideoDao {

    public String save(DbVideo v);
    public void save(List<DbVideo> v);
    public List<DbVideo> getRandomVideos(int num);

}
