package com.vikily.okhttp.net.exception;

/**
 * Created by jiang on 2017/2/23.
 */

public enum ApiError {
    ERROR_REQUEST_PARAM("0", "登录错误"),
    USER_NOT_EXIST("1", "用户不存在"),
    ERROR_CHARGE("2", "充值失败"),
    ERROR_REQUEST("3", "非法请求"),
    ERROR_NET_CONNECTION("4", "注册错误");
    private String code;
    private String msg;

    ApiError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsg(String code) {
        return "系统错误";
    }

}
