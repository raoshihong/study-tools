package com.rao.study.tools.jdk8function;

import lombok.Data;

@Data
public class Response<T> {

    private String code = ResponseCodeTypeEnum.SUCCESS.getCode();
    private String message = ResponseCodeTypeEnum.SUCCESS.getMessage();
    private T result;

}
