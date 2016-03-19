package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.StudyModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by dy on 2016/3/9.
 * 约学习专用类
 */
public class StudyService {
    private RequestParams params;
    private String reponseStr;
    private String url;

    /**
     * 向表传输约学习的信息
     * @param context
     * @param listener
     * @param username
     * @param information
     * @param studyArea
     * @param studyTime
     */
    public void userPost(Context context, Listener listener,String username,String information,String studyArea,
                         String studyTime, String secretkey){
        url="xueservice/UpXueService.php";
        params=new RequestParams();
        params.add("UserName",username);
        params.add("XueInformation",information);
        params.add("XueArea",studyArea);
        params.add("XueTime",studyTime);
        params.add("SecretKey",secretkey);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try{
                    System.out.println(reponseStr);
                    StudyModel studyModel=new Gson().fromJson(reponseStr,StudyModel.class);
                    if(studyModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(studyModel.getMsg());
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
     * 获取学习表内所有信息
     * @param name
     * @param context
     * @param listener
     */
    public void userGet(String key,String name,Context context,Listener listener){
        url="";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try{
                    StudyModel studyModel=new Gson().fromJson(reponseStr,StudyModel.class);
                    if(studyModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(studyModel.getMsg());
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
}
