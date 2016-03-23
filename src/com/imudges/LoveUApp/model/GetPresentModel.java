package com.imudges.LoveUApp.model;

/**
 * Created by caolu on 2016/3/23.
 */
public class GetPresentModel {
    private String PostUser;
    private String PostTime;
    private String PresentInformation;
    private String PostTitle;
    private Integer state;
    private String PostImage;

    public String getPostTitle() {
        return PostTitle;
    }

    public void setPostTitle(String postTitle) {
        PostTitle = postTitle;
    }
    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }

    private Integer PresentId;

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
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

    public String getPresentInformation() {
        return PresentInformation;
    }

    public void setPresentInformation(String presentInformation) {
        PresentInformation = presentInformation;
    }

    public Integer getPresentId() {
        return PresentId;
    }

    public void setPresentId(Integer presentId) {
        PresentId = presentId;
    }
}
