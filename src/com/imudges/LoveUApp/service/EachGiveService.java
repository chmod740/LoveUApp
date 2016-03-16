package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.EachGaveModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by dy on 2016/3/16.
 * 互赠类
 */
public class EachGiveService {
    private String url;
    private String responStr;
    private RequestParams params;
    public void give(Context context, String UserName, String SecretKey, String Time,
                     String Information, Listener listener){
        url="";
        params=new RequestParams();
        params.add("UserName",UserName);
        params.add("SecretKey",SecretKey);
        params.add("GetTime",Time);
        params.add("UserInformation",Information);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    EachGaveModel gaveModel= new Gson().fromJson(responStr,EachGaveModel.class);
                    if(gaveModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(gaveModel.getMsg());
                    }
                }catch(Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络异常");
            }
        });
    }

    public void getWay(Context context,String name,String key,Listener listener){
        url="";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    EachGaveModel gaveModel=new Gson().fromJson(responStr,EachGaveModel.class);
                    if(gaveModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(gaveModel.getMsg());
                    }
                }catch(Exception e){
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
