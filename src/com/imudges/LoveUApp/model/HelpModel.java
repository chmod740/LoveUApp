package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/27.
 */
public class HelpModel {
    private String HelpInformation;
    private String HelpMoney;
    private String HelpInfo;
    private Integer HelpId;
    private String DownTime;
    private String PayPassword;
    private String msg;
    private String HelpImage;
    private String PostUser;
    private Integer state;

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
    }

    public String getHelpImage() {
        return HelpImage;
    }

    public void setHelpImage(String helpImage) {
        HelpImage = helpImage;
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

    public String getHelpInformation() {
        return HelpInformation;
    }

    public void setHelpInformation(String helpInformation) {
        HelpInformation = helpInformation;
    }

    public String getHelpMoney() {
        return HelpMoney;
    }

    public void setHelpMoney(String helpMoney) {
        HelpMoney = helpMoney;
    }

    public String getHelpInfo() {
        return HelpInfo;
    }

    public void setHelpInfo(String helpInfo) {
        HelpInfo = helpInfo;
    }

    public String getDownTime() {
        return DownTime;
    }

    public void setDownTime(String downTime) {
        DownTime = downTime;
    }

    public Integer getHelpId() {
        return HelpId;
    }

    public void setHelpId(Integer helpId) {
        HelpId = helpId;
    }

    public String getPayPassword() {
        return PayPassword;
    }

    public void setPayPassword(String payPassword) {
        PayPassword = payPassword;
    }
}
