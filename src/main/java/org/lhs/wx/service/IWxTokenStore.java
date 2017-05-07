package org.lhs.wx.service;

/**
 * Created by longhuashen on 17/4/13.
 */
public interface IWxTokenStore {
    /**
     * 获取Token
     *
     * @param appId
     * @param type
     * @return
     */
    String get(String appId, String type);


    /**
     * 存储token
     *
     * @param appId
     * @param type
     * @param value
     * @return
     */
    String put(String appId, String type, String value);


    /**
     * 删除 token
     *
     * @param appId
     * @param type
     * @return
     */
    String remove(String appId, String type);
}
