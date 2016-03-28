package com.imudges.LoveUApp.model;

/**
 * Created by dy on 2016/3/10.
 */
public class MealModel {
    private Integer FoodId;
    private String PostUser;
    private String FoodArea;
    private String FoodInformation;
    private String GetUser ;
    private String FoodTime;
    private Integer state;
    private String FoodWay;
    private String PostImage;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getFoodId() {
        return FoodId;
    }

    public void setFoodId(Integer foodId) {
        FoodId = foodId;
    }

    public String getPostUser() {
        return PostUser;
    }

    public void setPostUser(String postUser) {
        PostUser = postUser;
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

    public String getGetUser() {
        return GetUser;
    }

    public void setGetUser(String getUser) {
        GetUser = getUser;
    }

    public String getFoodTime() {
        return FoodTime;
    }

    public void setFoodTime(String foodTime) {
        FoodTime = foodTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFoodWay() {
        return FoodWay;
    }

    public void setFoodWay(String foodWay) {
        FoodWay = foodWay;
    }

    public String getPostImage() {
        return PostImage;
    }

    public void setPostImage(String postImage) {
        PostImage = postImage;
    }
}
