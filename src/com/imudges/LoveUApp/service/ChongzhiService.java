package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.UserModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by dy on 2016/4/6.
 */
public class ChongzhiService {

    private String url;
    private String responStr;
    private RequestParams params;
    public void chong(Context context, String UserName, String SecretKey,String money, Listener listener) {
        url = "";
        params = new RequestParams();
        params.add("UserName",UserName);
        params.add("SecretKey",SecretKey);
        params.add("Money",money);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    UserModel model=new Gson().fromJson(responStr,UserModel.class);
                    if(model.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(model.getMag());
                    }
                }catch(Exception e) {
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
