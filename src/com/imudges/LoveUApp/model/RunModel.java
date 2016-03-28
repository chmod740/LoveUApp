package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/9.
 */
public class RunModel {
    private Integer state;
    private String msg;
    private String information;
    private Timestamp runtime;
    private String username;
    private String friendname;
    private String SecretKey;

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Timestamp getRuntime() {
        return runtime;
    }

    public void setRuntime(Timestamp runtime) {
        this.runtime = runtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String friendname) {
        this.friendname = friendname;
    }

    public Integer getState() { return state; }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
