package com.imudges.LoveUApp.model;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/10.
 */
public class MealModel {
    private String Username;
    private String FoodArea;
    private String FoodInformation;
    private String FoodWay;
    private String FoodTime ;
    private String msg;
    private Integer state;
    private Integer FoodId;
    private String PostUser;
    private String PostImage;
    private String GetUser;

    public String getGetUser() {
        return GetUser;
    }

    public void setGetUser(String getUser) {
        GetUser = getUser;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFoodArea() {
        return FoodArea;
    }

    public void setFoodArea(String foodArea) {
        FoodArea = foodArea;
    }

    public String getFoodInformation() {
        return FoodInformation;
    }

    public void setFoodInformation(String foodInformation) {
        FoodInformation = foodInformation;
    }

    public String getFoodWay() {
        return FoodWay;
    }

    public void setFoodWay(String foodWay) {
        FoodWay = foodWay;
    }

    public String getFoodTime() {
        return FoodTime;
    }

    public void setFoodTime(String foodTime) {
        FoodTime = foodTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getState() { return state; }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFoodId() { return FoodId; }

    public void setFoodId(Integer FoodId) {
        this.FoodId = FoodId;
    }

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }
}
