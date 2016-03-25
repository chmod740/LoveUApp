package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.MealModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/10.
 * 约饭类
 */
public class MealService {
    private RequestParams params;
    private String reponseStr;
    private String url;
    /**
     * post请求
     */
    public void userPost(Context context, String secretkey, String username, String Area ,
                         String information, String mealway, String time,Listener listener){
        url="foodservice/UpFoodService.php";
        params=new RequestParams();
        params.add("SecretKey",secretkey);
        params.add("UserName",username);
        params.add("FoodArea",Area);
        params.add("FoodInformation",information);
        params.add("FoodWay",mealway);
        params.add("FoodTime",time+"");
        System.out.println("************"+information);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    MealModel mealModel=new Gson().fromJson(reponseStr,MealModel.class);
                    if(mealModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(mealModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络异常");
            }
        });
    }
    /**
     * get请求
     */
    public void userGet(Context context,Listener listener,String name,String Sceretkey){
        url="";
        params.add("UserName",name);
        params.add("SecretKey",Sceretkey);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    MealModel mealModel=new Gson().fromJson(reponseStr,MealModel.class);
                    if(mealModel.getState()==1){
                        listener.onSuccess();
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络异常");
            }
        });
    }

}
