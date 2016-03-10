package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/9.
 */
public class SellModel {
    private Integer state;
    private String msg;
    private String username;
    private String sellMoney;
    private Timestamp upTime;
    private Timestamp downTime;
    private String information;
    private String Buyer;

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

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(String sellMoney) {
        this.sellMoney = sellMoney;
    }

    public Timestamp getUpTime() {
        return upTime;
    }

    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    public Timestamp getDownTime() {
        return downTime;
    }

    public void setDownTime(Timestamp downTime) {
        this.downTime = downTime;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
