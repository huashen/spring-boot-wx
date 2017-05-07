package org.lhs.wx.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longhuashen on 17/5/2.
 */
public class HttpUtil {

    protected HttpUtil() {
    }

    protected static Logger logger = LoggerFactory.getLogger("HttpUtil");


    /**
     * 对于非null非“”的请求参数使用utf8解码
     *
     * @param request
     * @param name
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getDecodedParameter(HttpServletRequest request, String name) {
        String encoded = request.getParameter(name);
        if (Strings.isNullOrEmpty(encoded)) {
            return encoded;
        }

        try {
            return URLDecoder.decode(encoded, "utf8");
        } catch (UnsupportedEncodingException e) {
            return URLDecoder.decode(encoded);
        }
    }


    /**
     * 获取客户端的IP，可能有一组IP，所以最好只用来记录日志
     *
     * @param request HttpServletRequest
     * @return 获取的IP, 如果获取不到返回 null
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static void writeJSON(HttpServletResponse response, final String jsonString) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonString);
        }
        //null
        catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("fmtResponse json error object:{}", jsonString, e);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 打印请求字符信息
     *
     * @param req
     * @return
     */
    public static String requestInfo(HttpServletRequest req) {

        StringBuilder builder = new StringBuilder("\n");
        builder.append("################[request]############################").append("\n");
        builder.append("requestUrl:").append(req.getRequestURL().toString()).append("\n");
        builder.append("queryString:").append(req.getQueryString()).append("\n");
        builder.append("method:").append(req.getMethod()).append("\n");
        builder.append("remote:").append(req.getRemoteHost()).append(":").append(req.getRemotePort()).append("\n");
        builder.append("remoteAddr:").append(req.getRemoteAddr()).append("\n");
        builder.append("remoteUser:").append(req.getRemoteUser()).append("\n");

        builder.append("################[parameters]############################").append("\n");
        Enumeration parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            Object name = parameterNames.nextElement();
            String content = req.getParameter(String.valueOf(name));
            try {
                content = new String(content.getBytes(), "utf-8");
            } catch (UnsupportedEncodingException ignore) {
            }
            builder.append(name).append(":").append(content).append("\n");
        }
        builder.append("################[header]############################").append("\n");
        Enumeration enumeration = req.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            Object headerName = enumeration.nextElement();
            String content = req.getHeader(String.valueOf(headerName));
            builder.append(headerName).append(":").append(content).append("\n");
        }

        builder.append("###################[cookie]#########################").append("\n");
        Cookie[] cookies = req.getCookies();
        if (cookies == null) cookies = new Cookie[]{};
        for (Cookie cookie : cookies) {
            builder
                    .append(cookie.getName()).append("=").append(cookie.getValue())
                    .append(",domain=").append(cookie.getDomain())
                    .append(",path=").append(cookie.getPath())
                    .append(",maxAge=" + cookie.getMaxAge())
                    .append(",version="+cookie.getVersion())
                    .append(",httpOnly=" + cookie.isHttpOnly())
                    .append(",secure=" + cookie.getSecure())
                    .append("\n");
        }

        return builder.toString();
    }


    public static class QueryParams {
        private Map<String, String> queryParamMap = new HashMap<>();


        public QueryParams put(String key, String value) {
            queryParamMap.put(key, value);
            return this;
        }

        public Map<String, String> asMap() {
            return queryParamMap;
        }
    }


    public static QueryParams createQueryParams() {
        return new QueryParams();
    }
}
