package cn.itweknow.sbelkstart.exception;


import cn.itweknow.sbelkstart.common.enums.ErrorCodeEnum;

import java.io.Serializable;

/**
 * @author ganchaoyang
 * @date 2019/5/57:54
 */
public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1L;

    private ErrorCodeEnum errorCode;

    private String message;

    public BaseException(ErrorCodeEnum errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseException(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getDesc();
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
