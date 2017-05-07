package org.lhs.wx.msg;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;

/**
 * Created by longhuashen on 17/5/2.
 */
public abstract class WxMsg {

    /**
     * 开发者微信号
     */
    private String ToUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private String CreateTime;
    /**
     * 消息创建时间 text
     */
    private String MsgType;
    public static String MsgType_text ="text";
    public static String MsgType_image="image";
    public static String MsgType_image_text="news";
    public static String MsgType_voice="voice";
    public static String MsgType_video="video";
    public static String MsgType_shortvideo="shortvideo";
    public static String MsgType_location="location";
    public static String MsgType_link="link";
    public static String MsgType_event="event";
    /**
     * 消息id，64位整型
     */
    private String MsgId;


    public WxMsg(String msgType) {
        MsgType = msgType;
    }

    public WxMsg() {
    }

    public String getToUserName() {
        return ToUserName;
    }

    public WxMsg setToUserName(String toUserName) {
        ToUserName = toUserName;
        return this;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public WxMsg setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
        return this;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public WxMsg setCreateTime(String createTime) {
        CreateTime = createTime;
        return this;
    }

    public String getMsgType() {
        return MsgType;
    }

    public WxMsg setMsgType(String msgType) {
        MsgType = msgType;
        return this;
    }

    public String getMsgId() {
        return MsgId;
    }

    public WxMsg setMsgId(String msgId) {
        MsgId = msgId;
        return this;
    }

    public WxMsg setDocument(Element root) {
        return this.setToUserName(root.elementText("ToUserName"))
                .setCreateTime(root.elementText("CreateTime"))
                .setFromUserName(root.elementText("FromUserName"));
    }

    private Document buildDocument(){
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");

        //ToUserName
        append(root, "ToUserName", ToUserName);

        //fromUserName
        append(root, "FromUserName", FromUserName);

        //MsgId
        append(root, "MsgId", MsgId);

        //MsgType
        append(root, "MsgType", MsgType);

        //createTime
        append(root,"CreateTime",CreateTime,false);

        //other field
        appendElements(root);

        return doc;

    }

    protected abstract void appendElements(Element root);

    public static void append(Element root,String fieldName,String fieldValue){
        append(root, fieldName, fieldValue, true);
    }
    public static void append(Element root,String fieldName,String fieldValue,boolean cdata){
        //准备参数
        Element filedElement = DocumentHelper.createElement(fieldName);
        if(cdata){
            filedElement.add(new DefaultCDATA(fieldValue));
        }else{
            filedElement.setText(fieldValue);
        }
        root.add(filedElement);
    }

    /**
     *
     * @return 生成xml
     */
    public String xml(){
        return buildDocument().asXML();
    }

}
