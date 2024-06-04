package com.sunbird.serve.volunteering.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    private static ResponseResult resultData(Integer code, String msg, Object data) {
        ResponseResult res = new ResponseResult();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    /**
     * success
     */
    public static ResponseResult success() {
        return resultData(200, "Successful operation", null);
    }

    /**
     * success
     */
    public static ResponseResult success(String msg) {
        return resultData(200, msg, null);
    }

    /**
     * success
     */
    public static ResponseResult success(Object data) {
        return resultData(200, "Successful operation", data);
    }

    /**
     * success
     */
    public static ResponseResult success(String msg, Object data) {
        return resultData(200, msg, data);
    }

    /**
     * failure
     */
    public static ResponseResult error() {
        return resultData(500, "Operation failed", null);
    }

    /**
     * failure
     */
    public static ResponseResult error(Integer code) {
        return resultData(code, null, null);
    }

    /**
     * failure
     */
    public static ResponseResult error(Integer code, String msg) {
        return resultData(code, msg, null);
    }

    /**
     * failure
     */
    public static ResponseResult error(String msg) {
        return resultData(500, msg, null);
    }

    /**
     * failure
     */
    public static ResponseResult error(Object data) {
        return resultData(500, "operation failed", data);
    }

    /**
     * failure
     */
    public static ResponseResult error(Integer code, String msg, Object data) {
        return resultData(code, msg, data);
    }
}