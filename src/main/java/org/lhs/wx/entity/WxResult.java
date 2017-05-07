package org.lhs.wx.entity;

import java.io.Serializable;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxResult implements Serializable {

    /**
     * 返回码
     */
    private int errcode;
    /**
     * 说明
     */
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
