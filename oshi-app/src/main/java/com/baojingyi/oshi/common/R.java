package com.baojingyi.oshi.common;


import lombok.Data;

@Data
public class R {

    private Integer code;
    private String msg;
    private Object data;

    public R(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static R ok(Object data){
        return new R(200,"操作成功",data);
    }

    public static R ok(){
        return new R(200,"操作成功",null);
    }

    public static R error(String msg){
        return new R(500,msg,null);
    }

    public static R error(){
        return new R(500,"操作失败",null);
    }

    public static R error(Integer code,String msg){
        return new R(code,msg,null);
    }


}
