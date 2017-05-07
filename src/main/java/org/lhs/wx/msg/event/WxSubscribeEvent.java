package org.lhs.wx.msg.event;

import com.google.common.base.Strings;
import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxSubscribeEvent extends WxEvent {
    /**事件KEY值，qrscene_为前缀，后面为二维码的参数值*/
    private String EventKey;

    /***二维码的ticket，可用来换取二维码图片*/
    private String Ticket;

    public WxSubscribeEvent() {
        super(Event_subscribe);
    }


    public String getEventKey() {
        return EventKey;
    }

    public WxSubscribeEvent setEventKey(String eventKey) {
        EventKey = eventKey;
        return this;
    }

    public String getTicket() {
        return Ticket;
    }

    public WxSubscribeEvent setTicket(String ticket) {
        Ticket = ticket;
        return this;
    }

    @Override
    protected void appendEventElement(Element root) {
        //EventKey
        if(!Strings.isNullOrEmpty(EventKey)){
            append(root,"EventKey",EventKey);
        }
        //Ticket
        if(!Strings.isNullOrEmpty(Ticket)){
            append(root,"Ticket",Ticket);
        }
    }
}
