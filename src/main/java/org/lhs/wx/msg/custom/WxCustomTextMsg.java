package org.lhs.wx.msg.custom;

import lombok.Data;

/**
 * WxCustomTextMsg
 *
 *Created by longhuashen on 17/4/13.
 */
public class WxCustomTextMsg extends WxCustomMsg {

    public WxCustomTextMsg(String touser, String content) {
        super(touser, WxCustomMsg.msg_type_text);

        text = new TextMsg();
        text.setContent(content);
    }

    private TextMsg text;

    @Data
    public static class TextMsg {
        private String content;
    }


}
