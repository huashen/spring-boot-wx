package org.lhs.wx.msg;

import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxVoiceMsg extends WxMsg {

    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    private String MediaId;
    /**
     * 语音格式，如amr，speex等
     */
    private String Format;

    public WxVoiceMsg() {
        super(WxMsg.MsgType_video);
    }

    @Override
    protected void appendElements(Element root) {
        append(root,"MediaId",MediaId);
        append(root,"Format",Format);
    }

    @Override
    public String xml() {
        return null;
    }

    public String getMediaId() {
        return MediaId;
    }

    public WxVoiceMsg setMediaId(String mediaId) {
        MediaId = mediaId;
        return this;
    }

    public String getFormat() {
        return Format;
    }

    public WxVoiceMsg setFormat(String format) {
        Format = format;
        return this;
    }
}
