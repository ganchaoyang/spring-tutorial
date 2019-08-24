package cn.itweknow.sbelkstart.common.enums;

/**
 * @author ganchaoyang
 * @date 2019/5/57:59
 */
public enum ErrorCodeEnum {

    /**
     * 操作成功
     */
    SUCCESS(10000, "操作成功"),

    /**
     * 参数错误。
     */
    PARAM_ERROR(10001, "参数错误"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(10002, "系统异常"),

    /**
     * 操作失败
     */
    OPERATE_FAILED(10003, "操作失败");

    private int code;

    private String desc;

    ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
