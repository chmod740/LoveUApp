package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/15.
 */
public class UserModel {
    private String UserPhone;
    private String NickName;
    private Integer state;
    private String mag;
    private String UserPhoto;
    private String TrueName;
    private Integer UserSex;
    private String UserGrade;
    private String UserMajor;
    private String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String trueName) {
        TrueName = trueName;
    }

    public Integer getUserSex() {
        return UserSex;
    }

    public void setUserSex(Integer userSex) {
        UserSex = userSex;
    }

    public String getUserGrade() {
        return UserGrade;
    }

    public void setUserGrade(String userGrade) {
        UserGrade = userGrade;
    }

    public String getUserMajor() {
        return UserMajor;
    }

    public void setUserMajor(String userMajor) {
        UserMajor = userMajor;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }
}
