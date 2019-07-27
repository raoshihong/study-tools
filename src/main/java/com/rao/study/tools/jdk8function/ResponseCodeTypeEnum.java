package com.rao.study.tools.jdk8function;

public enum ResponseCodeTypeEnum {
    SUCCESS("200","成功"),
    PARAMETER_ERROR("400","参数错误"),
    SERVER_ERROR("500","服务繁忙");

    private String code;
    private String message;

    ResponseCodeTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
