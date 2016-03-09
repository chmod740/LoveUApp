package com.imudges.LoveUApp.model;

/**
 * Created by caolu on 2016/3/9.
 */
public class LoginModel {
    private Integer state;
    private String msg;
    private String secretKey;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
