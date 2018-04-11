package com.vidor.Baby.handler;

import com.vidor.Baby.enums.ExceptionEnum;
import com.vidor.Baby.exception.BabyException;
import com.vidor.Baby.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BabyExceptionHandler {

    @ExceptionHandler(BabyException.class)
    @ResponseBody
    public Object handleBabyException(BabyException e) {
            return ResultUtil.fail(((BabyException) e).getCode(), ((BabyException) e).getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        return ResultUtil.fail(e.getMessage());
    }
}
