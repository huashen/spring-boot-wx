package org.lhs.wx.msg.event;

import org.dom4j.Element;
import org.lhs.wx.msg.WxMsg;

/**
 * Created by longhuashen on 17/5/2.
 */
public abstract class WxEvent extends WxMsg {

    private String Event;

    public static final String Event_subscribe = "subscribe";//订阅
    public static final String Event_unsublsscribe = "unsubscribe";//订阅
    public static final String Event_Scan = "SCAN";//二维码扫描
    public static final String Event_LOCATION = "LOCATION";//定位
    public static final String Event_CLICK = "CLICK";//点击
    public static final String Event_VIEW = "VIEW";//点击跳转URL

    public WxEvent(String event) {
        super(WxMsg.MsgType_event);
        Event = event;
    }

    @Override
    protected void appendElements(Element root) {
        append(root, "Event", Event);
        appendEventElement(root);
    }

    protected abstract void appendEventElement(Element root);

    public String getEvent() {
        return Event;
    }

    public WxEvent setEvent(String event) {
        Event = event;
        return this;
    }
}
