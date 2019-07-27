package com.rao.study.tools.jdk8function;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class ResponseUtils {

    public static Object getResult(Response response){
        if (ResponseCodeTypeEnum.SUCCESS.getCode().equals(response.getCode())) {
            return response.getResult();
        }else{
            throw new BusinessException(response.getCode(),response.getMessage());
        }
    }

    public static <T> Response<T> buildResult(String exceptionInfoTitle, Supplier<T> fun) {
        log.info("ResponseUtils.buildResult");
        Response<T> response = new Response<>();
        try {
            T result = fun.get();
            response.setResult(result);
        } catch (BusinessException e) {
            log.warn(exceptionInfoTitle, e);
            response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
            response.setMessage(e.getMessage());
        } catch (Exception e){
            log.warn(exceptionInfoTitle, e);
            response.setCode(ResponseCodeTypeEnum.SERVER_ERROR.getCode());
            response.setMessage(ResponseCodeTypeEnum.SERVER_ERROR.getMessage());
        }
        return response;
    }

}
