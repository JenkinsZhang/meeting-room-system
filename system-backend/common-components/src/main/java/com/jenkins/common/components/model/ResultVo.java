package com.jenkins.common.components.model;

import java.io.Serializable;

/**
 * result json
 */
public class ResultVo implements Serializable {


    private Integer code;

    private String msg;

    private Object data;

    public static ResultVo ok(String msg) {
        return new ResultVo(200, msg, null);
    }

    public static ResultVo ok(String msg, Object data) {
        return new ResultVo(200, msg, data);
    }

    public static ResultVo error(Integer code, String msg) {
        return new ResultVo(code, msg, null);
    }

    public static ResultVo error(String msg) {
        return new ResultVo(500, msg, null);
    }

    public ResultVo() {

    }

    public ResultVo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
