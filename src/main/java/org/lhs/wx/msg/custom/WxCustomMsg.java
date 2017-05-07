package org.lhs.wx.msg.custom;

/**
 * WxCustomMsg
 * <pre>
 *     http://mp.weixin.qq.com/wiki/7/12a5a320ae96fecdf0e15cb06123de9f.html
 * </pre>
 *
 * Created by longhuashen on 17/5/2.
 */
public abstract class WxCustomMsg {

    private String touser;

    private String msgtype;

    public static final String msg_type_text="text";
    public static final String msg_type_image="image";
    public static final String msg_type_voice="voice";
    public static final String msg_type_music="music";
    public static final String msg_type_news="news";

    public WxCustomMsg(String touser, String msgtype) {
        this.touser = touser;
        this.msgtype = msgtype;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
