package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/14.
 */
public class AdModel {
    private String msg;
    private Integer state;
    private String PhotoUrl;

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

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
}
