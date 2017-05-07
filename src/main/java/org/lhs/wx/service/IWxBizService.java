package org.lhs.wx.service;

import org.lhs.wx.msg.WxMsg;
import org.lhs.wx.msg.WxTextMsg;
import org.lhs.wx.msg.event.WxEvent;

/**
 * Created by longhuashen on 17/5/2.
 */
public interface IWxBizService {

    /**
     * 微信文本消息
     *
     * @param wxTextMsg 文本消息
     * @return
     */
    WxMsg doAction(WxTextMsg wxTextMsg);


    /**
     * 微信事件处理
     *
     * @param wxEvent
     * @return
     */
    void doEventAction(WxEvent wxEvent);
}
