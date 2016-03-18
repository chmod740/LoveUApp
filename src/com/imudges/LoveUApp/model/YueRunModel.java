package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/18.
 */
public class YueRunModel {
    private String PostUser;
    private String RunInformation;
    private String RunTime;
    private Integer state;
    private String PostImage;

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
    }

    public String getRunInformation() {
        return RunInformation;
    }

    public void setRunInformation(String runInformation) {
        RunInformation = runInformation;
    }

    public String getRunTime() {
        return RunTime;
    }

    public void setRunTime(String runTime) {
        RunTime = runTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }
}
