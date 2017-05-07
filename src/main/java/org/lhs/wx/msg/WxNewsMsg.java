package org.lhs.wx.msg;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;
import org.lhs.wx.msg.custom.WxCustomTextMsg;
import org.lhs.wx.util.JsonUtil;

/**
 * Created by longhuashen on 17/5/4.
 */
public class WxNewsMsg extends WxMsg {

    private NewsDto news;

    public WxNewsMsg() {
        super(WxMsg.MsgType_image_text);
    }



    @Override
    protected void appendElements(Element root) {
        append(root,"MediaId","1234");
    }

    public NewsDto getNews() {
        return news;
    }

    public void setNews(NewsDto news) {
        this.news = news;
    }
}
