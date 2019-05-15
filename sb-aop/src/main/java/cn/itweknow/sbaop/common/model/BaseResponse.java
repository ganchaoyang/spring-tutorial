package cn.itweknow.sbaop.common.model;

import cn.itweknow.sbaop.common.enums.ErrorCodeEnum;

import java.io.Serializable;

/**
 * @author ganchaoyang
 * @date 2019/5/1215:36
 */
public class BaseResponse implements Serializable {

    private int code;


    private String message;


    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static BaseResponse addError(ErrorCodeEnum errorCodeEnum) {
        return new BaseResponse(errorCodeEnum.getCode(), errorCodeEnum.getDesc());
    }

    public static BaseResponse addError(ErrorCodeEnum errorCodeEnum, String message) {
        return new BaseResponse(errorCodeEnum.getCode(), message);
    }


    public static BaseResponse addResult() {
        return new BaseResponse(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getDesc());
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
