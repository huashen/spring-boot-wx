package org.lhs.wx.exception;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxException extends Exception {

    private static final long serialVersionUID = 1L;

    private int errorCode;

    public WxException(int errorCode, String errorMsg) {
        super("weixin api errorCode:" + errorCode + ",error msg:" + errorMsg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
