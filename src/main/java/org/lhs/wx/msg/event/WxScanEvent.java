package org.lhs.wx.msg.event;

import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxScanEvent extends WxEvent {

    /**事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id*/
    private String EventKey;

    /***二维码的ticket，可用来换取二维码图片*/
    private String Ticket;

    public WxScanEvent() {
        super(Event_Scan);
    }



    public String getEventKey() {
        return EventKey;
    }

    public WxScanEvent setEventKey(String eventKey) {
        EventKey = eventKey;
        return this;
    }

    public String getTicket() {
        return Ticket;
    }

    public WxScanEvent setTicket(String ticket) {
        Ticket = ticket;
        return this;
    }

    @Override
    protected void appendEventElement(Element root) {
        append(root,"EventKey",EventKey);
        append(root,"Ticket",Ticket);
    }
}
