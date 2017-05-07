package org.lhs.wx.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultCDATA;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxDomUtil {

    private WxDomUtil() {
    }

    public static WxDom create() {
        return new WxDom();
    }


    public static class WxDom {
        private Document wxDocument;

        private Element root;

        public WxDom() {
            wxDocument = DocumentHelper.createDocument();
            root = wxDocument.addElement("xml");
        }


        public WxDom append(String fieldName, String fieldValue, boolean cdata) {
            //准备参数
            Element filedElement = DocumentHelper.createElement(fieldName);
            if (cdata) {
                filedElement.add(new DefaultCDATA(fieldValue));
            }
            else {
                filedElement.setText(fieldValue);
            }
            root.add(filedElement);
            return this;
        }

        public WxDom append(String fieldName, String fieldValue) {
            //准备参数
            return append(fieldName, fieldValue, false);
        }

        public String asXML() {
            return wxDocument.asXML();
        }
    }


    private static OutputFormat formatter = OutputFormat.createPrettyPrint();

    public static String format(String xml) {

        StringWriter out = new StringWriter();
        StringReader in = new StringReader(xml);

        try {
            Document doc = new SAXReader().read(in);
            formatter.setEncoding("utf-8");

            XMLWriter writer = new XMLWriter(out, formatter);
//            writer.setEscapeText(false);
            writer.write(doc);
            writer.close();
        }
        catch (DocumentException | IOException e) {
            throw new RuntimeException("print xml error");
        }
        return out.toString();
    }

    /**
     * 获取xml字符串中的指定内容
     *
     * @param xmlBody xml字符串
     * @param key     xml字符串中的关键字
     * @return 关键字节点中的文本内容
     */
    public static String getSubElementValue(String xmlBody, String key) {

        if (xmlBody == null || key == null) {
            throw new IllegalArgumentException("xmlBody is null[" + xmlBody + "] or key is [" + key + "]");
        }

        try {
            return DocumentHelper.parseText(xmlBody).getRootElement().element(key).getTextTrim();
        }
        catch (DocumentException e) {
            throw new IllegalArgumentException(xmlBody);
        }
    }
}
