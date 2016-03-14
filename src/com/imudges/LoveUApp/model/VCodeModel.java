package com.imudges.LoveUApp.model;

/**
 * Created by caolu on 2016/3/14.
 */
public class VCodeModel  {
    private String UserPhone,Vcode,msg;
    private Integer state;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserPhone() {

        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getVcode() {
        return Vcode;
    }

    public void setVcode(String vcode) {
        Vcode = vcode;
    }
}
