package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.SellModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.sql.Timestamp;

/**
 * Created by dy on 2016/3/9
 * 拍卖post与get请求类.
 */
public class SellService {
    private RequestParams params;
    private String reponseStr;
    private String url;

    /**
     * 拍卖上传post请求
     * @param context
     * @param listener
     * @param name
     * @param money
     * @param uptime
     * @param information
     */
    public void userPost(Context context, Listener listener, String name, String money, Timestamp uptime,
                         String information){
        url="";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("UserMoney",money);
        params.add("UpTime",uptime+"");
        params.add("UserInformation",information);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    SellModel sellModel= new Gson().fromJson(reponseStr,SellModel.class);
                    if (sellModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(sellModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络错误");
            }
        });
    }
    /**
     * 拍卖获取get
     */
    public void userGet(String name,Context context,Listener listener){
        url="";
        params=new RequestParams();
        params.add("UserName",name);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    SellModel sellModel= new Gson().fromJson(reponseStr,SellModel.class);
                    if (sellModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(sellModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure("获取信息失败");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络错误");
            }
        });
    }
}
