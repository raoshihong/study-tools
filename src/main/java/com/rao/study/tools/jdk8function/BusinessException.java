package com.rao.study.tools.jdk8function;

import lombok.Data;

import java.util.Date;

@Data
public class BusinessException extends RuntimeException {

    private String code = "200";
    private Date date = new Date();
    private String version = "1.0";

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, Date date, String version) {
        this.code = code;
        this.date = date;
        this.version = version;
    }

    public BusinessException(String code,String message, Date date, String version) {
        super(message);
        this.code = code;
        this.date = date;
        this.version = version;
    }

    public BusinessException(String code,String message, Throwable cause, Date date, String version) {
        super(message, cause);
        this.code = code;
        this.date = date;
        this.version = version;
    }

    public BusinessException(String code, Throwable cause, Date date, String version) {
        super(cause);
        this.code = code;
        this.date = date;
        this.version = version;
    }

    public BusinessException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Date date, String version) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.date = date;
        this.version = version;
    }
}
