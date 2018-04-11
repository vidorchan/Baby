package com.vidor.Baby.utils;

import com.vidor.Baby.entity.Baby;
import com.vidor.Baby.enums.ExceptionEnum;
import com.vidor.Baby.result.Result;

public class ResultUtil {

    public static Result<Baby> ok(Baby baby) {
        Result<Baby> result = new Result<>();
        result.setData(baby);
        result.setCode(ExceptionEnum.OK.getCode());
        result.setMsg(ExceptionEnum.OK.getMsg());

        return result;
    }

    public static Result<Baby> fail(String msg) {
        return fail(ExceptionEnum.UNKNOW_EXCEPTION.getCode(), msg);
    }

    public static Result<Baby> fail(Integer code, String msg) {
        Result<Baby> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
