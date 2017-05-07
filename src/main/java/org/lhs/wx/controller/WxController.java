package org.lhs.wx.controller;

import com.google.common.base.Strings;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.lhs.wx.exception.WxException;
import org.lhs.wx.msg.WxMsg;
import org.lhs.wx.msg.WxTextMsg;
import org.lhs.wx.msg.event.WxEvent;
import org.lhs.wx.service.IWxBizService;
import org.lhs.wx.util.HttpUtil;
import org.lhs.wx.util.WxDomUtil;
import org.lhs.wx.util.WxMsgUtil;
import org.lhs.wx.util.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by longhuashen on 17/4/13.
 */
@Controller
@RequestMapping("/weixin")
public class WxController {

    protected Logger logger = LoggerFactory.getLogger("wx-api");

    @Autowired
    private IWxBizService wxBizService;

    @RequestMapping(value = "/api/{originId}", produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    public String api(@PathVariable("originId") String originId, HttpServletRequest req) throws WxException, IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("api-req:{}", HttpUtil.requestInfo(req));
        }

        String echostr = WxUtil.verifySignature(req, originId);
        //第一次配置
        if (!Strings.isNullOrEmpty(echostr)) {
            return echostr;
        }
        //其他
        else {

            String xmlBody = WxUtil.getRequestBody(req);
            if (logger.isDebugEnabled()) {
                logger.debug("xmlBody from weixin server:\t\n{}", WxDomUtil.format(xmlBody));
            }

            WxMsg wxMsg = WxMsgUtil.parse(xmlBody);
            if (logger.isDebugEnabled()) {
                logger.debug("xmlBody wxMsg:\t\n{}", wxMsg);
            }


            logger.info("msg:{}", ToStringBuilder.reflectionToString(wxMsg, ToStringStyle.MULTI_LINE_STYLE));



            //微信文本消息
            if (wxMsg instanceof WxTextMsg) {
                String xml = wxBizService.doAction((WxTextMsg) wxMsg).xml();

                if (logger.isDebugEnabled()) {
                    logger.debug("response xml:\t\n{}", WxDomUtil.format(xmlBody));
                }
                return xml;
            }

            //微信事件
            else if (wxMsg instanceof WxEvent) {

                if (logger.isDebugEnabled()) {
                    logger.debug("do event handle");
                }

                wxBizService.doEventAction((WxEvent) wxMsg);
                if (logger.isDebugEnabled()) {
                    logger.debug("event finish");
                }
                return "";
            }

            return "";
        }
    }
}
