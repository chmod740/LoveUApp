package com.imudges.LoveUApp.service;

/**
 * Created by dy on 2016/3/9.
 */

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.RunModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * 约跑专用类
 */
public class RunService {
    private RequestParams params;
    private String reponseStr;
    private String url;
    /**
     * 向表上传发出约跑信息
     */
    public void userPost(String name, String information,String secretkey, String runtime ,String address,Context context, Listener listener){
        url="RunService/UpRunService.php";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("RunInformation",information);
        params.add("RunTime",runtime);
        params.add("SecretKey",secretkey);
        params.add("RunArea",address);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    RunModel runModel= new Gson().fromJson(reponseStr,RunModel.class);
                    if (runModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(runModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure("上传信息失败");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络异常");
            }
        });
    }

    /**
     * 获取当前表中所有信息
     * @param context
     * @param name
     * @param listener
     */
    public void userGet(String key,Context context,String name,Listener listener){
        url="";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    RunModel runModel= new Gson().fromJson(reponseStr,RunModel.class);
                    if (runModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(runModel.getMsg());
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
    public void deleteInfor(String key,Context context,String name,Listener listener){
        url="runservice/DeleteRunService.php";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    RunModel runModel= new Gson().fromJson(reponseStr,RunModel.class);
                    if (runModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(runModel.getMsg());
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
