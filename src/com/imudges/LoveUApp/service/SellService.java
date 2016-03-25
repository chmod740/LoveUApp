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
     * @param information
     */
    public void userPost(Context context, String name, String secretkey, String title,
                         String information, Listener listener){
        url="paiservice/UpPaiService.php";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",secretkey);
        params.add("PaiTitle",title);
        params.add("PaiInformation",information);
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
    public void userGet(String key,String name,Context context,Listener listener){
        url="";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
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
