package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/9.
 */
public class SellModel {
    private Integer state;
    private String msg;
    private String PostUser;
    private String PaiMoney;
    private String DownTime;
    private String PaiInformation;
    private String PaiImage;
    private String PaiTitle;
    private Integer PaiId;

    public Integer getPaiId() {
        return PaiId;
    }

    public void setPaiId(Integer paiId) {
        PaiId = paiId;
    }

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

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
    }

    public String getPaiMoney() {
        return PaiMoney;
    }

    public void setPaiMoney(String paiMoney) {
        PaiMoney = paiMoney;
    }

    public String getDownTime() {
        return DownTime;
    }

    public void setDownTime(String downTime) {
        DownTime = downTime;
    }

    public String getPaiInformation() {
        return PaiInformation;
    }

    public void setPaiInformation(String paiInformation) {
        PaiInformation = paiInformation;
    }

    public String getPaiImage() {
        return PaiImage;
    }

    public void setPaiImage(String paiImage) {
        PaiImage = paiImage;
    }

    public String getPaiTitle() {
        return PaiTitle;
    }

    public void setPaiTitle(String paiTitle) {
        PaiTitle = paiTitle;
    }
}
