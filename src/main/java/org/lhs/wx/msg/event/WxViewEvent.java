package org.lhs.wx.msg.event;

import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxViewEvent extends WxEvent {
    /**
     * 事件KEY值，设置的跳转URL
     */
    private String EventKey;

    public WxViewEvent() {
        super(Event_VIEW);
    }

    public String getEventKey() {
        return EventKey;
    }

    public WxViewEvent setEventKey(String eventKey) {
        EventKey = eventKey;
        return this;
    }

    @Override
    protected void appendEventElement(Element root) {
        append(root, "EventKey", EventKey);
    }
}
