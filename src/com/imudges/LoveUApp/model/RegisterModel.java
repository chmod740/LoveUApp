package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/9.
 */
public class RegisterModel {
    private String UserName;
    private String Password;
    private String TrueName;
    private Integer UserSex;
    private Integer UserGrade;
    private String UserMajor;
    private String UserPhone;
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public Integer getUserGrade() {
        return UserGrade;
    }

    public void setUserGrade(Integer userGrade) {
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
}
