package org.lhs.wx.service;


import org.lhs.wx.entity.config.WxPnConfig;

/**
 * Created by longhuashen on 17/4/13.
 */
public interface IWxPnConfigService {

    /**
     * 获得服务器配置 token(令牌)
     *
     * @param key
     * @return
     */
    String getServerToken(String key);

    String getAppSecret(String appId);

    /**
     * 通过微信appId 获得微信公众号配置
     *
     * @param type
     * @param value
     * @return wxPnConfig
     */
    WxPnConfig get(String type, String value);

}
