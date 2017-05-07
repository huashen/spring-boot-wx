package org.lhs.wx.service.impl;

import org.joda.time.DateTime;
import org.lhs.wx.entity.config.WxPnConfig;
import org.lhs.wx.entity.constant.WxConstant;
import org.lhs.wx.msg.WxMsg;
import org.lhs.wx.msg.WxTextMsg;
import org.lhs.wx.msg.custom.WxCustomTextMsg;
import org.lhs.wx.msg.event.WxEvent;
import org.lhs.wx.msg.event.WxSubscribeEvent;
import org.lhs.wx.service.IWxBizService;
import org.lhs.wx.service.IWxPnConfigService;
import org.lhs.wx.service.IWxTokenService;
import org.lhs.wx.util.WxMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by longhuashen on 17/5/2.
 */
@Service
public class WxBizServiceImpl implements IWxBizService {

    private Logger logger = LoggerFactory.getLogger(WxBizServiceImpl.class);

    @Autowired
    private IWxPnConfigService wxPnConfigService;

    @Autowired
    private IWxTokenService<String> wxTokenService;


    @Override
    public WxMsg doAction(WxTextMsg weixinMsg) {

        String content = weixinMsg.getContent();
        if (content.equals("时间")) {

            return WxMsgUtil.createTextReply(weixinMsg, "北京时间:" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        } else {
            return WxMsgUtil.createTextReply(weixinMsg, "雯雯");
        }
    }

    @Override
    public void doEventAction(WxEvent msg) {

        String event = msg.getEvent();
        logger.info("msg[event]:" + msg.getEvent());

        //关注
        if (WxEvent.Event_subscribe.equals(event)) {
            doWxSubscribeEvent((WxSubscribeEvent) msg);
        }
        //取消关注
        else if (WxEvent.Event_unsublsscribe.equals(event)) {
            doWxUnSubscribeEvent(msg);
        }
        //取消关注
        else if (WxEvent.Event_LOCATION.equals(event)) {

            doWxLocationEvent(msg);
        } else {
            //其他事件
            if (logger.isInfoEnabled()) {
                logger.info("other event a user:{},originId:{},openId:{}", msg.getEvent(), msg.getToUserName(), msg.getFromUserName());
            }
        }
    }

    private void doWxLocationEvent(WxEvent event) {
        if (logger.isInfoEnabled()) {
            logger.info("location a user:{},originId:{},openId:{}", event.getEvent(), event.getToUserName(), event.getFromUserName());
        }

    }

    private void doWxUnSubscribeEvent(WxEvent event) {
        if (logger.isInfoEnabled()) {
            logger.info("lost a user:{},originId:{},openId:{}", event.getEvent(), event.getToUserName(), event.getFromUserName());
        }
    }


    private void doWxSubscribeEvent(WxSubscribeEvent event) {
        String openId = event.getFromUserName();
        String originId = event.getToUserName();


        WxPnConfig wxPn = wxPnConfigService.get("originId", originId);

        if (wxPn == null) {
            logger.error("can not found wxPnConfig by originId:{}", originId);
            return;
        }


        if (wxPn.getAppId() == null) {
            logger.error("appId is null by originId:{}", originId);
            return;
        }


        String token = wxTokenService.getOrUpdateIfPresent(wxPn.getAppId(), WxConstant.TOKEN_GENERAL);
        if (token == null) {
            logger.error("token is null by appId:{}", wxPn.getAppId());
            return;
        }

        String welcomeWord = wxPn.getWelcomeWord();

        WxMsgUtil.sendCustomMsg(new WxCustomTextMsg(openId, welcomeWord), token);
    }
}
