package org.lhs.wx.service;

/**
 * Created by longhuashen on 17/4/13.
 */
public interface IWxTokenService<T> {

    /**
     * 通过 微信公众号 获得Access Token
     *
     * @param appId     appId
     * @return 微信AccessToken
     */
    T getOrUpdateIfPresent(String appId, String type);

    /**
     * 删除token
     * @param appId
     * @param type
     */
    void remove(String appId, String type);
}
