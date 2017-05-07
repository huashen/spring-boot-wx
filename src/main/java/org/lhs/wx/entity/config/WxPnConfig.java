package org.lhs.wx.entity.config;

/**
 * Created by longhuashen on 17/4/13.
 */
public class WxPnConfig {

    private String appId;

    private String appSecret;

    private String originId;

    private String serverToken;

    private String serverEncodingAESKey;

    private String welcomeWord;


    /**
     * 商户号
     */
    private  String partner ;

    /**
     * 商户密钥
     */
    private  String partnerkey;

    //微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    private String notifyurl;

    // 订单生成的机器 IP
    private String spbill_create_ip;

    /**
     * 统一下单url
     */
    private String unifiedorderUrl;

    /**
     * 查询下单url
     */
    private String orderqueryUrl;

    /**
     * 关闭订单url
     */
    private String closeorderUrl;

    /**
     * 申请退款url
     */
    private String refundUrl;

    /**
     * 查询退款url
     */
    private String refundqueryUrl;

    /**
     * 下载对账单url
     */
    private String downloadbillUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    public String getServerEncodingAESKey() {
        return serverEncodingAESKey;
    }

    public void setServerEncodingAESKey(String serverEncodingAESKey) {
        this.serverEncodingAESKey = serverEncodingAESKey;
    }

    public String getWelcomeWord() {
        return welcomeWord;
    }

    public void setWelcomeWord(String welcomeWord) {
        this.welcomeWord = welcomeWord;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerkey() {
        return partnerkey;
    }

    public void setPartnerkey(String partnerkey) {
        this.partnerkey = partnerkey;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getUnifiedorderUrl() {
        return unifiedorderUrl;
    }

    public void setUnifiedorderUrl(String unifiedorderUrl) {
        this.unifiedorderUrl = unifiedorderUrl;
    }

    public String getOrderqueryUrl() {
        return orderqueryUrl;
    }

    public void setOrderqueryUrl(String orderqueryUrl) {
        this.orderqueryUrl = orderqueryUrl;
    }

    public String getCloseorderUrl() {
        return closeorderUrl;
    }

    public void setCloseorderUrl(String closeorderUrl) {
        this.closeorderUrl = closeorderUrl;
    }

    public String getRefundUrl() {
        return refundUrl;
    }

    public void setRefundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
    }

    public String getRefundqueryUrl() {
        return refundqueryUrl;
    }

    public void setRefundqueryUrl(String refundqueryUrl) {
        this.refundqueryUrl = refundqueryUrl;
    }

    public String getDownloadbillUrl() {
        return downloadbillUrl;
    }

    public void setDownloadbillUrl(String downloadbillUrl) {
        this.downloadbillUrl = downloadbillUrl;
    }
}
