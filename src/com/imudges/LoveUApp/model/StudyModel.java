package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/9.
 */
public class StudyModel {
    private String PostName;
    private String XueInformation;
    private String GetName;
    private String XueArea;
    private String XueTime;
    private Integer state;
    private String msg;

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    public String getXueInformation() {
        return XueInformation;
    }

    public void setXueInformation(String xueInformation) {
        XueInformation = xueInformation;
    }

    public String getGetName() {
        return GetName;
    }

    public void setGetName(String getName) {
        GetName = getName;
    }

    public String getXueArea() {
        return XueArea;
    }

    public void setXueArea(String xueArea) {
        XueArea = xueArea;
    }

    public String getXueTime() {
        return XueTime;
    }

    public void setXueTime(String xueTime) {
        XueTime = xueTime;
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
