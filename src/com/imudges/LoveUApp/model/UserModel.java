package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/15.
 */
public class UserModel {
    private String NickName;
    private Integer state;
    private String mag;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
}
