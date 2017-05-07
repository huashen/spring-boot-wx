package org.lhs.wx.msg;

import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxImageMsg extends WxMsg {

    /**
     * 通过上传多媒体文件，得到的id
     */
    private String MediaId;

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    private String PicUrl;

    public WxImageMsg() {
        super(WxMsg.MsgType_image);
    }

    @Override
    protected void appendElements(Element root) {
        append(root,"MediaId",MediaId);
        append(root,"PicUrl",PicUrl);
    }


    public String getPicUrl() {
        return PicUrl;
    }

    public WxImageMsg setPicUrl(String picUrl) {
        PicUrl = picUrl;
        return this;
    }

    public String getMediaId() {
        return MediaId;
    }

    public WxImageMsg setMediaId(String mediaId) {
        MediaId = mediaId;
        return this;
    }
}
