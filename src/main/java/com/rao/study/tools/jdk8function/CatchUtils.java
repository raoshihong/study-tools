package com.rao.study.tools.jdk8function;

import com.baomidou.mybatisplus.extension.api.R;

import java.util.function.Consumer;
import java.util.function.Function;

public class CatchUtils {
    //有参数,没结果返回
    public static <T> Response<R> tryDoConsumer(T t, Consumer<T> consumer){
        Response response = new Response();
        try {
            consumer.accept(t);
            return response;
        } catch (Exception e) {
            response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
            response.setMessage( e.getMessage());
            return response;
        }
    }

    //有参数,有结果返回
    public static <T,R> Response<R> tryDoFunction(T t, Function<T, R> function){
        Response response = new Response();
        try {
            R result = function.apply(t);
            response.setResult(result);
            return response;
        } catch (BusinessException e) {
            response.setCode(ResponseCodeTypeEnum.PARAMETER_ERROR.getCode());
            response.setMessage( e.getMessage());
            return response;
        } catch (Exception e) {
            response.setCode(ResponseCodeTypeEnum.SERVER_ERROR.getCode());
            response.setMessage( e.getMessage());
            return response;
        }
    }

}
