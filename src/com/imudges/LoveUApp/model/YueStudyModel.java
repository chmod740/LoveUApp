package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/17.
 */
public class YueStudyModel {
    private String PostUser;
    private String XueArea;
    private String XueInformation;
    private String XueTime;
    private Integer state;
    private String PostImage;
    private Integer XueId;

    public Integer getXueId() {
        return XueId;
    }

    public void setXueId(Integer xueId) {
        XueId = xueId;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
    }

    public String getXueArea() {
        return XueArea;
    }

    public void setXueArea(String xueArea) {
        XueArea = xueArea;
    }

    public String getXueInformation() {
        return XueInformation;
    }

    public void setXueInformation(String xueInformation) {
        XueInformation = xueInformation;
    }

    public String getXueTime() {
        return XueTime;
    }

    public void setXueTime(String xueTime) {
        XueTime = xueTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
