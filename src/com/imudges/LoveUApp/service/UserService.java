package com.imudges.LoveUApp.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.LoginModel;
import com.imudges.LoveUApp.model.RegisterModel;
import com.imudges.LoveUApp.model.SimpleModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by HUPENG on 2016/3/9.
 */

/**
 * 用户类
 * 注册和登录
 */
public class UserService {
    private RequestParams params;
    private String reponseStr;
    private String url;
    public void login(String username, String password, Context context, Listener listener){
        url = "service/LoginService.php";
        params = new RequestParams();
        params.add("Name",username);
        params.add("PassWord",password);
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
                    listener.onFailure(e.getLocalizedMessage());
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络请求失败："+ throwable.getLocalizedMessage());
            }
        });
    }
    public void register(Context context,String username,String password,String truename,Integer usersex,
                         Integer usergrade,String usermajor,String phone,Listener listener){
        url="service/RegisterService.php";
        params = new RequestParams();
        //params.add("username",username);
        params.add("UserName",username);
        params.add("PassWord",password);
        params.add("TrueName",truename);
        params.add("UserSex",usersex+"");
        params.add("UserGrade",usergrade+"");
        params.add("UserMajor",usermajor);
        params.add("UserPhone",phone);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr = new String(bytes);
                try {
                    RegisterModel registerModel = new Gson().fromJson(reponseStr,RegisterModel.class);
                    if (registerModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(registerModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络请求失败："+ throwable.getLocalizedMessage());
            }
        });
    }


    /**
     * 找回密码的业务逻辑
     * */
    public void forgrtPasswd(Context context,String userId,Listener listener){
        //判断userI格式的合法性
        if (userId == null  || userId.length() <5){
            listener.onFailure("请先输入用户名");
            return;
        }

        url = "sendForgetEmail.action";
        params = new RequestParams();
        params.add("userId",userId);

        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                //获取返回的Json串
                reponseStr = new String(bytes);
                //构造实体类
                try {
                    SimpleModel simpleModel = new Gson().fromJson(reponseStr,SimpleModel.class);
                    if (simpleModel.getStatus() == 1){
                        //验证成功
                        listener.onSuccess();
                    }else{
                        listener.onFailure("找回密码失败，原因："+ simpleModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure("找回密码失败，原因：" + "服务器发生错误");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("找回密码失败，原因：" + throwable.getLocalizedMessage());
            }
        });
    }
}
