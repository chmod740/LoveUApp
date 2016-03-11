package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/10.
 */
public class MealModel {
    private String Username;
    private String Area;
    private String Information;
    private String Friendname;
    private String mealWay;
    private Timestamp mealTime;
    private String msg;
    private Integer state;

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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getFriendname() {
        return Friendname;
    }

    public void setFriendname(String friendname) {
        Friendname = friendname;
    }

    public String getMealWay() {
        return mealWay;
    }

    public void setMealWay(String mealWay) {
        this.mealWay = mealWay;
    }

    public Timestamp getMealTime() {
        return mealTime;
    }

    public void setMealTime(Timestamp mealTime) {
        this.mealTime = mealTime;
    }
}
