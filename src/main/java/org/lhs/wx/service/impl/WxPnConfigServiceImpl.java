package org.lhs.wx.service.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.lhs.wx.entity.config.WxPnConfig;
import org.lhs.wx.exception.WxRuntimeException;
import org.lhs.wx.service.IWxPnConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Created by longhuashen on 17/4/13.
 */
public class WxPnConfigServiceImpl implements IWxPnConfigService, InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(WxPnConfigServiceImpl.class);


    private Table<String, String, WxPnConfig> configTable = HashBasedTable.create();

    private List<WxPnConfig> wxPnConfigs;

    @Override
    public String getServerToken(String originId) {

        configTable.get("originId", originId);

        //获取服务器配置
        WxPnConfig wxPnConfig = configTable.get("originId", originId);
        if (wxPnConfig == null) {
            logger.warn("get server wxPnConfig null,originId:{}", originId);
            throw new WxRuntimeException("server config error");
        }

        //获取服务器token
        String token = wxPnConfig.getServerToken();
        if (token == null) {
            logger.warn("get server token null,key:{}", originId);
        }

        return token;
    }

    @Override
    public String getAppSecret(String appId) {
        //获取服务器配置
        WxPnConfig wxPnConfig = configTable.get("appId", appId);
        if (wxPnConfig == null) {
            logger.warn("get server wxPnConfig null,appId:{}", appId);
            throw new WxRuntimeException("server config error");
        }

        //获取服务器appsecret
        String appsecret = wxPnConfig.getAppSecret();
        if (appsecret == null) {
            logger.warn("get server token null,appId:{}", appId);
        }

        return appsecret;
    }

    @Override
    public WxPnConfig get(String type, String value) {
        return configTable.get(type, value);
    }

    public void setWxPnConfigs(List<WxPnConfig> wxPnConfigs) {
        this.wxPnConfigs = wxPnConfigs;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (wxPnConfigs == null || wxPnConfigs.size() == 0) {
            return;
        }

        for (WxPnConfig wxPnConfig : wxPnConfigs) {
            configTable.put("appId", wxPnConfig.getAppId(), wxPnConfig);
            configTable.put("originId", wxPnConfig.getOriginId(), wxPnConfig);
        }

    }
}
