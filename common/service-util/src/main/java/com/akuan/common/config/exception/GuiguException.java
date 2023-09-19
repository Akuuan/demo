package com.akuan.common.config.exception;


import com.akuan.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class GuiguException extends RuntimeException{

    private Integer code;
    private String msg;

    public GuiguException(Integer code , String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    @Override
    public String toString(){
        return "GuliException{" +
                "code=" + code +
                ",message=" + this.getMessage() +
                "}";
    }
}
