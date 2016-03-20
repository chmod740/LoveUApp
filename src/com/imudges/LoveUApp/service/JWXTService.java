package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.JWXTModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by dy on 2016/3/20.
 */
public class JWXTService {
    private String url;
    private String responStr;
    private RequestParams params;
    public void setup(Context context, String name, String secretkey, String num, String pasd, Listener listener){
        url="JwxtService/UpJwxtService.php";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",secretkey);
        params.add("JwxtNumber",num);
        params.add("JwxtPassword",pasd);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                JWXTModel jwxtModel=new Gson().fromJson(responStr,JWXTModel.class);
                try{
                    if(jwxtModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(jwxtModel.getMsg());
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
