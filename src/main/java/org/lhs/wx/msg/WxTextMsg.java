package org.lhs.wx.msg;

import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxTextMsg extends WxMsg {

    private String Content;

    public WxTextMsg() {
        super(WxMsg.MsgType_text);
    }

    @Override
    protected void appendElements(Element root) {
        append(root,"Content",Content);
    }

    public String getContent() {
        return Content;
    }

    public WxTextMsg setContent(String content) {
        this.Content = content;
        return this;
    }
}
