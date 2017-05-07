package org.lhs.wx.service.impl;

import org.lhs.wx.entity.constant.WxConstant;
import org.lhs.wx.exception.WxException;
import org.lhs.wx.exception.WxRuntimeException;
import org.lhs.wx.service.IWxPnConfigService;
import org.lhs.wx.service.IWxTokenService;
import org.lhs.wx.service.IWxTokenStore;
import org.lhs.wx.util.WxTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by longhuashen on 17/4/13.
 */
public class WxTokenServiceImpl implements IWxTokenService<String> {

    private Logger logger = LoggerFactory.getLogger(WxTokenServiceImpl.class);

    private IWxTokenStore wxTokenStore;

    private IWxPnConfigService wxPnConfigService;


    private Lock lock = new ReentrantLock();// 锁对象


    @Override
    public String getOrUpdateIfPresent(String appId, String type) {

        String token = wxTokenStore.get(appId, type);
        if (token != null) {
            return token;
        }


        lock.lock();
        try {

            token = wxTokenStore.get(appId, type);
            if (token != null) {
                return token;
            }

            //
            String appSecret = wxPnConfigService.getAppSecret(appId);

            for (int i = 0; i < 3; i++) {
                if (WxConstant.TOKEN_GENERAL.equals(type)) {


                    try {
                        token = WxTokenUtil.getNativeToken(appId, appSecret);
                    } catch (WxException e) {
                        throw new WxRuntimeException("", e);
                    }


                } else {
                    throw new UnsupportedOperationException("type :" + type + " not support");
                }

                if (token != null) {
                    break;
                }

                //休息一下
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException 1000", e);
                }
            }


            wxTokenStore.put(appId, type, token);
            return token;

        } finally {
            lock.unlock();
        }

    }

    @Override
    public void remove(String appId, String type) {
        wxTokenStore.remove(appId, type);
    }


    public void setWxTokenStore(IWxTokenStore wxTokenStore) {
        this.wxTokenStore = wxTokenStore;
    }

    public void setWxPnConfigService(IWxPnConfigService wxPnConfigService) {
        this.wxPnConfigService = wxPnConfigService;
    }
}
