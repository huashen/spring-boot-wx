package org.lhs.wx.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信凭证access_token
 * <p>
 * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。
 *
 * Created by longhuashen on 17/4/13.
 */
public class WxAccessToken extends WxResult {

    /**
     * 凭证
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 凭证有效时间，单位：秒
     */
    @JsonProperty("expires_in")
    private String expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
