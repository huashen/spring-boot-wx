package org.lhs.wx.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.lhs.wx.exception.WxRuntimeException;
import org.lhs.wx.msg.*;
import org.lhs.wx.msg.custom.WxCustomMsg;
import org.lhs.wx.msg.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxMsgUtil {

    private static final Logger logger = LoggerFactory.getLogger(WxMsgUtil.class);


    public static WxMsg parse(String xmlBody) {
        Preconditions.checkNotNull(xmlBody);

        Element root = null;
        try {
            root = DocumentHelper.parseText(xmlBody).getRootElement();
        } catch (DocumentException e) {
            Throwables.propagateIfInstanceOf(e, IllegalArgumentException.class);
        }

        //noinspection ConstantConditions
        String msgType = root.element("MsgType").getText();
        if (Strings.isNullOrEmpty(msgType)) {
            throw new IllegalArgumentException("msg type is null or empty");
        }

        //text
        else if (WxMsg.MsgType_text.equals(msgType)) {
            return new WxTextMsg()
                    .setContent(root.element("Content").getText())
                    .setToUserName(root.element("ToUserName").getText())
                    .setCreateTime(root.element("CreateTime").getText())
                    .setFromUserName(root.element("FromUserName").getText())
                    .setMsgId(root.element("MsgId").getText());

        }
        //image
        else if (WxMsg.MsgType_image.equals(msgType)) {
            return new WxImageMsg()
                    .setPicUrl(root.element("PicUrl").getText())
                    .setMediaId(root.element("MediaId").getText())
                    .setToUserName(root.element("MsgType").getText())
                    .setCreateTime(root.element("CreateTime").getText())
                    .setFromUserName(root.element("FromUserName").getText())
                    .setMsgId(root.element("MsgId").getText());
        }
        //video
        else if (WxMsg.MsgType_video.equals(msgType)) {
            return new WxVoiceMsg()
                    .setMediaId(root.element("MediaId").getText())
                    .setFormat(root.element("Format").getText())
                    .setToUserName(root.element("MsgType").getText())
                    .setCreateTime(root.element("CreateTime").getText())
                    .setFromUserName(root.element("FromUserName").getText())
                    .setMsgId(root.element("MsgId").getText());
        }

        //event
        else if (WxMsg.MsgType_event.equals(msgType)) {
            return getEventMsg(root);
        }

        throw new UnsupportedOperationException("msg type:" + msgType);
    }


    /**
     * @param root 解析document
     * @return
     * @see {http://mp.weixin.qq.com/wiki/2/5baf56ce4947d35003b86a9805634b1e.html}
     * 解析event 事件
     */
    public static WxEvent getEventMsg(Element root) {
        Preconditions.checkArgument(root != null, "root can not be null");
        //noinspection ConstantConditions
        String event = root.element("Event").getText();
        if (WxEvent.Event_subscribe.equals(event)) {
            String eventKey = root.elementText("EventKey");
            //关注
            if (Strings.isNullOrEmpty(eventKey)) {
                return (WxEvent)new WxSubscribeEvent() {
                    @Override
                    protected void appendEventElement(Element root) {
                        //do nothing
                    }
                }.setDocument(root);
            }
            //用户未关注公众号,扫描二维码
            else {
                return (WxEvent)new WxSubscribeEvent()
                        .setEventKey(eventKey)
                        .setTicket(root.elementText("Ticket"))
                        .setDocument(root);
            }

        }
        //未关注
        else if (WxEvent.Event_unsublsscribe.equals(event)) {
            return (WxEvent)new WxEvent(event) {
                @Override
                protected void appendEventElement(Element root) {
                    //do nothing
                }
            }
                    .setDocument(root);
        }
        //二维码扫描
        else if (WxEvent.Event_Scan.equals(event)) {
            return (WxEvent)new WxScanEvent()
                    .setEventKey(root.elementText("EventKey"))
                    .setTicket(root.elementText("Ticket"))
                    .setDocument(root);
        }
        //跳转URL
        else if (WxEvent.Event_VIEW.equals(event)) {
            return (WxEvent)new WxViewEvent()
                    .setEventKey(root.elementText("EventKey"))
                    .setDocument(root);
        }
        //点击事件
        else if (WxEvent.Event_CLICK.equals(event)) {
            return (WxEvent)new WxClickEvent()
                    .setEventKey(root.elementText("EventKey"))
                    .setDocument(root);
        }
        //地理位置定位
        else if (WxEvent.Event_LOCATION.equals(event)) {
            return (WxEvent)new WxLocationEvent()
                    .setLatitude(root.elementText("Latitude"))
                    .setLongitude(root.elementText("Longitude"))
                    .setPrecision(root.elementText("Precision"))
                    .setDocument(root);

        }
        throw new UnsupportedOperationException("msg event:" + event);
    }


    /**
     * 创建一个文本回复
     *
     * @param requestMsg   请求消息
     * @param replyContent 回复内容
     * @return 微信文本消息
     */
    public static WxTextMsg createTextReply(WxMsg requestMsg, String replyContent) {
        if (requestMsg == null) {
            throw new WxRuntimeException("requestMsg is null");
        }

        if (replyContent == null) {
            replyContent = "";
        }

        return (WxTextMsg) new WxTextMsg()
                .setContent(replyContent)
                .setMsgId(requestMsg.getMsgId())
                .setFromUserName(requestMsg.getToUserName())
                .setToUserName(requestMsg.getFromUserName())
                .setCreateTime(System.currentTimeMillis() + "")
                ;
    }


    public static void sendCustomMsg(WxCustomMsg wxCustomMsg, String token) {

        String json = JsonUtil.toJson(wxCustomMsg);

        HttpUriRequest req = RequestBuilder
                .post()
                .setUri("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token)
                .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
                .build();
        String response = HttpClients.execute(req);


        if (logger.isDebugEnabled()) {
            logger.debug("send msg:{},response:{}", wxCustomMsg, response);
        }
    }

    public static void sendNewsMsg(WxNewsMsg wxNewsMsg, String token) {

        String json = JsonUtil.toJson(wxNewsMsg);

        logger.info("=======sendNewsMsg========>json:{}", json);

        HttpUriRequest req = RequestBuilder
                .post()
                .setUri("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token)
                .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
                .build();
        String response = HttpClients.execute(req);


        if (logger.isDebugEnabled()) {
            logger.debug("send msg:{},response:{}", wxNewsMsg, response);
        }
    }
}
