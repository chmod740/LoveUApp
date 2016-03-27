package com.imudges.LoveUApp.model;

/**
 * Created by caolu on 2016/3/23.
 */
public class GetPresentModel {
    private String GiveUser;
    private String GiveInformation;
    private Integer state;
    private String GiveImage;
    private Integer GiveId;
    private String msg;
    private String UserId;
    private String GetInformation;
    private String UserName;
    private String GetUser;

    public String getGetUser() {
        return GetUser;
    }

    public void setGetUser(String getUser) {
        GetUser = getUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGetInformation() {
        return GetInformation;
    }

    public void setGetInformation(String getInformation) {
        GetInformation = getInformation;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getGiveUser() {
        return GiveUser;
    }

    public void setGiveUser(String giveUser) {
        GiveUser = giveUser;
    }

    public String getGiveInformation() {
        return GiveInformation;
    }

    public void setGiveInformation(String giveInformation) {
        GiveInformation = giveInformation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getGiveImage() {
        return GiveImage;
    }

    public void setGiveImage(String giveImage) {
        GiveImage = giveImage;
    }

    public Integer getGiveId() {
        return GiveId;
    }

    public void setGiveId(Integer giveId) {
        GiveId = giveId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
