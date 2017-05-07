package org.lhs.wx.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.lhs.wx.entity.WxResult;
import org.lhs.wx.exception.WxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxUtil {

    private static Logger logger = LoggerFactory.getLogger("wx-util");
    private static final BigDecimal HUNDRED=new BigDecimal(100);

    public static org.joda.time.format.DateTimeFormatter wxTiemEndFormater = DateTimeFormat.forPattern("yyyyMMddHHmmss");


    /**
     * 微信验证签名
     *
     * @param req
     * @param configToken
     * @return
     * @throws WxException
     */
    public static String verifySignature(HttpServletRequest req, String configToken) throws WxException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");


        logger.info("\nsignature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);


        if (!signature(configToken, timestamp, nonce).equals(signature)) {
            throw new WxException(-90, "signature error");
        }

        return echostr;
    }


    public static String getRequestBody(HttpServletRequest req) throws WxException {
        try {
            StringBuilder inputLine = new StringBuilder();
            BufferedReader br = req.getReader();
            String line;
            while ((line = br.readLine()) != null) {
                inputLine.append(line);
            }
            br.close();
            return inputLine.toString();
        }
        catch (IOException e) {
            throw new WxException(-90, "weixin body read error");
        }
    }


    public static String signature(String... args) {
        Preconditions.checkNotNull(args);
        return DigestUtils.shaHex(dicSort(args));
    }

    /**
     * 字典排序
     *
     * @param strings 需要排序的字符串
     * @return 字典排序
     */
    public static String dicSort(String... strings) {
        ArrayList<String> list = Lists.newArrayList(strings);
        Collections.sort(list);

        StringBuilder stringBuffer = new StringBuilder();
        for (String str : list) {
            if (str == null) {
                continue;
            }
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }

    /**
     * 随机字符串
     * @return 随机串
     */
    public static String getNonceStr() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 随机数
        String currTime = outFormat.format(now);
        // 8位日期
        String strTime = currTime.substring(8, currTime.length());
        // 四位随机数
        String strRandom = buildRandom(4) + "";
        // 10位序列号,可以自行调整
        return strTime + strRandom;
    }

    /**
     * 取出一个指定长度大小的随机正整数
     * @param length 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    private static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 获取时间戳
     * @return 时间戳
     */
    public static  String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 元转换成分
     * @param amount 金额
     * @return
     */
    public static int getMoney(String amount) {
        return new BigDecimal(amount).multiply(HUNDRED).intValue();
    }

    /**
     * 创建签名
     */
    public static String paySign(SortedMap<String, String> packageParams, String accountKey) {
        /**
         * 微信支付文档
         *
         * 参数名ASCII码从小到大排序（字典序）；
         　◆　如果参数的值为空不参与签名；
         　◆　参数名区分大小写；
         　◆　验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
         　◆　微信接口可能增加字段，验证签名时必须支持增加的扩展字段
         *
         * 第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
         stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";

         第二步：拼接API密钥：
         stringSignTemp="stringA&key=192006250b4c09247ec02edce69f6a2d"
         sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7"
         */
        StringBuilder sb = new StringBuilder();

        Set<Map.Entry<String,String>> entitySet = packageParams.entrySet();
        for (Map.Entry<String,String> entry : entitySet) {
            String k = entry.getKey();
            String v = entry.getValue();

            if(v==null){
                continue;
            }

            if (!"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }

        sb.append("key=").append(accountKey);

        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    /**
     * 解析微信通知xml
     * @param xml
     * @return map
     */
    public static Map<String,String> parseXmlToMap(String xml) {
        Map<String,String> retMap = Maps.newHashMap();
        try {
            StringReader read = new StringReader(xml);
            InputSource in = new InputSource(read);
            SAXReader sb = new SAXReader();
            //得到Document对象
            Document document = sb.read(in);
            //获取根节点
            Element root = document.getRootElement();
            //获取所有的子节点
            //noinspection unchecked
            List<Element> es = root.elements();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getText());
                }
            }
        } catch (Exception e) {
            logger.error("when WxUtil-->parseXmlToMap() throw a Exception:",e);
            e.printStackTrace();
        }
        return retMap;
    }

    /**
     * 转为十六进制
     * @param hash
     * @return
     */
    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


    public static void checkInvokeSuccess(WxResult wxResult) throws WxException {

        if (wxResult.getErrcode() != 0) {
            throw new WxException(wxResult.getErrcode(), wxResult.getErrmsg());
        }
    }


    /**
     * 判断是否是微信浏览器
     * @param request	请求
     * @return
     */
    public static boolean isWxBrower(HttpServletRequest request) {
        String header = request.getHeader("user-agent");
        return !Strings.isNullOrEmpty(header) && header.toLowerCase().contains("micromessenger");


    }

    /**
     * 日期转时间 20150701160756
     * @param time
     * @return
     */
    public static Date convertTimeEnd(String time) {
        return DateTime.parse(time, wxTiemEndFormater).toDate();
    }

}
