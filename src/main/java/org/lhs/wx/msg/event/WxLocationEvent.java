package org.lhs.wx.msg.event;

import org.dom4j.Element;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxLocationEvent extends WxEvent {
    /**
     * 地理位置纬度
     */
    private String Latitude;

    /**
     * 地理位置经度
     */
    private String Longitude;

    /**
     * 地理位置精度
     */
    private String Precision;

    public WxLocationEvent() {
        super(Event_LOCATION);
    }

    public String getLatitude() {
        return Latitude;
    }

    public WxLocationEvent setLatitude(String latitude) {
        Latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return Longitude;
    }

    public WxLocationEvent setLongitude(String longitude) {
        Longitude = longitude;
        return this;
    }

    public String getPrecision() {
        return Precision;
    }

    public WxLocationEvent setPrecision(String precision) {
        Precision = precision;
        return this;
    }

    @Override
    protected void appendEventElement(Element root) {
        append(root, "Latitude", Latitude,false);
        append(root, "Longitude", Longitude,false);
        append(root, "Precision", Precision,false);
    }
}
