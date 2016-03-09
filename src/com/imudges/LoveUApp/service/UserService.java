package com.imudges.LoveUApp.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.LoginModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by HUPENG on 2016/3/9.
 */
public class UserService {
    private RequestParams params;
    private String reponseStr;
    private String url;
    public void login(String username, String password, Context context, Listener listener){
        url = "Service.php";
        params = new RequestParams();
        params.add("username",username);
        params.add("password",password);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr = new String(bytes);
                try {
                    LoginModel loginModel = new Gson().fromJson(reponseStr,LoginModel.class);
                    if (loginModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(loginModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure("网络异常");
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络请求失败："+ throwable.getLocalizedMessage());
            }
        });
    }
}
