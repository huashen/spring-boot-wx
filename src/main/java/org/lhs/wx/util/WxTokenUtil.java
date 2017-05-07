package org.lhs.wx.util;

import com.google.common.base.Strings;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.lhs.wx.entity.WxAccessToken;
import org.lhs.wx.exception.WxException;
import org.lhs.wx.exception.WxRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by longhuashen on 17/4/13.
 */
public class WxTokenUtil {

    public static final Logger logger = LoggerFactory.getLogger("wxTokenUtil");

    protected static final String GRAB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    public static String getNativeToken(String appId, String appSecret) throws WxException {

        //noinspection SpellCheckingInspection
        HttpUriRequest get = RequestBuilder.get()
                .setUri(GRAB_ACCESS_TOKEN_URL)
                .addParameter("grant_type", "client_credential")
                .addParameter("appid", appId)
                .addParameter("secret", appSecret)
                .build();

        String json= HttpClients.execute(get);

        WxAccessToken wxAccessToken = JsonUtil.fromString(json, WxAccessToken.class);

        WxUtil.checkInvokeSuccess(wxAccessToken);

        return wxAccessToken.getAccessToken();

    }


    public static WxAccessToken parse2wxToken(String jsonString) throws WxException {
        WxAccessToken accessToken = JsonUtil.fromString(jsonString, WxAccessToken.class);
        WxUtil.checkInvokeSuccess(accessToken);
        return accessToken;
    }


    public static String buildKey(String appId, String tokenType) {
        if (Strings.isNullOrEmpty(appId)) {
            throw new WxRuntimeException("appId is null");
        }

        return tokenType + ":" + appId;
    }
}
