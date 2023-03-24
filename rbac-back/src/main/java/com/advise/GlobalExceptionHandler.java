package com.advise;

import com.common.Result;
import com.enums.ResultEnums;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huiyuan080
 * @date 2023/3/21
 * @apiNote
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        return new Result(ResultEnums.ERROR.getCode(), e.getMessage());
    }

}
