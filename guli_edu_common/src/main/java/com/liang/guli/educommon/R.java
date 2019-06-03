package com.liang.guli.educommon;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    //定义结果里面需要的属性
    private boolean success;//是后成功
    private int code;//状态码
    private String message;//返回消息
    private Map<String, Object> data = new HashMap<>();

    //私有构造器，只允许外部的调用该类中的方法，不能new对象
    private R() {
    }


    //成功的方法
    public static R success() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.OK);
        r.setMessage("方法执行成功");
        return r;
    }

    //失败的方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("方法执行出错误");
        return r;
    }

    //链式编程
    //R.ok().data().message();

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }


}
