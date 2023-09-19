package com.akuan.common.result;


import lombok.Data;

@Data
public class Result<T> {

    private Integer code; // 状态码
    private String message; //返回信息

    private T data; //数据

    //私有化构造
    private Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    //封装返回数据
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>();
        if(body != null){
            result.setData(body);}
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }
    //成功
    public static<T> Result<T> ok(){
        return build(null,ResultCodeEnum.SUCCESS);
    }

    //有数据 成功
    public static<T> Result<T> ok(T data){
        return build(data,ResultCodeEnum.SUCCESS);
    }

    //失败
    public static<T> Result<T> fail(){
        return build(null,ResultCodeEnum.FAIL);
    }
    //有数据失败
    public static<T> Result<T> fail(T data){
        return build(data,ResultCodeEnum.FAIL);
    }
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
