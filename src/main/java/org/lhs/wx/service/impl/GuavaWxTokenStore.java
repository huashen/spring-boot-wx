package org.lhs.wx.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.lhs.wx.service.IWxTokenStore;
import org.lhs.wx.util.WxTokenUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by longhuashen on 17/4/13.
 */
public class GuavaWxTokenStore implements IWxTokenStore {

    private Cache<String, String> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(7100, TimeUnit.SECONDS)//比腾讯的7200 少100秒
            .maximumSize(10)
            .build();

    @Override
    public String get(String appId, String type) {
        String key = WxTokenUtil.buildKey(appId, type);
        return cache.getIfPresent(key);
    }

    @Override
    public String put(String appId, String type, String value) {
        String key = WxTokenUtil.buildKey(appId, type);
        cache.put(key, value);
        return key;
    }

    @Override
    public String remove(String appId, String type) {
        String key = WxTokenUtil.buildKey(appId, type);
        cache.invalidate(key);
        return key;
    }

}
