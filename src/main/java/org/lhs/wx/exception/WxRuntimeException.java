package org.lhs.wx.exception;

/**
 * Created by longhuashen on 17/5/2.
 */
public class WxRuntimeException extends RuntimeException {

    public WxRuntimeException() {
        super();
    }

    public WxRuntimeException(String message) {
        super(message);
    }

    public WxRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxRuntimeException(Throwable cause) {
        super(cause);
    }
}
