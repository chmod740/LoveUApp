package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.LoginModel;
import com.imudges.LoveUApp.model.RegisterModel;
import com.imudges.LoveUApp.model.UserModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

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
                        Save save=new Save("UserKey",context);
                        save.savein("secretkey",loginModel.getSecretKey());
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
    public void register(Context context,String username,String password,Integer usersex,
                        String phone,Listener listener){
        url="service/RegisterService.php";
        params = new RequestParams();
        //params.add("username",username);
        params.add("UserName",username);
        params.add("PassWord",password);
        //params.add("TrueName",truename);
        params.add("UserSex",usersex+"");
        //params.add("UserGrade",usergrade+"");
        //params.add("UserMajor",usermajor);
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

    public void SureName(Context context,Listener listener,String Key,String name){
        url = "service/ReLoginService.php";
        params = new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",Key);
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
                listener.onFailure("网络请求失败！");
            }
        });

    }

    /**
     * 获取昵称
     */

    public void getNickP(Context context,String Phone,Listener listener){
        url="service/NumberService.php";
        params=new RequestParams();
        params.add("UserPhone",Phone);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try{
                    UserModel userModel=new Gson().fromJson(reponseStr,UserModel.class);
                    if(userModel.getState()==1){
                        listener.onSuccess();
                        Save save=new Save("Nick",context);
                        save.savein(Phone,userModel.getNickName());
                        save.savein("Photo",userModel.getUserPhoto());
                    }else{
                        listener.onFailure(userModel.getMag());
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
    public void getNickU(Context context,String Name,Listener listener){
        url="service/UserNameService.php";
        params=new RequestParams();
        params.add("UserName",Name);
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try{
                    UserModel userModel=new Gson().fromJson(reponseStr,UserModel.class);
                    if(userModel.getState()==1){
                        listener.onSuccess();
                        Save save=new Save("Nick",context);
                        save.savein(Name,userModel.getNickName());
                        save.savein("Photo",userModel.getUserPhoto());
                    }else{
                        listener.onFailure(userModel.getMag());
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
