package com.imudges.LoveUApp.model;



public class PresentModel {
    private Integer state;
    private String msg;
    private String GiveInformation;
    private String UserName;
    private String SecretKey;
    private String upinfomation;


    public String getUpinfomation() {
        return upinfomation;
    }

    public void setUpinfomation(String upinfomation) {
        this.upinfomation = upinfomation;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGiveInformation() {
        return GiveInformation;
    }

    public void setGiveInformation(String giveInformation) {
        GiveInformation = giveInformation;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }
}
