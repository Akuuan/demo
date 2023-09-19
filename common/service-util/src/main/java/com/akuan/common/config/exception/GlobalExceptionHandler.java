package com.akuan.common.config.exception;

import com.akuan.common.result.Result;
import com.akuan.common.result.ResultCodeEnum;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class GlobalExceptionHandler {


    //全局异常处理的方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("全局异常处理：");

    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public  Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("特定异常抛出：");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.build(null,   ResultCodeEnum.PERMISSION);
    }
}
