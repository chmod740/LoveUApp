package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.VCodeModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;


public class VCodeService {
    private String phonenumber,vcode;
    private RequestParams params;
    private String url;
    private String reponseStr;


    public void applyVcode(String phonenumber,Context context,Listener listener){
        url = "service/PhoneService.php";
        params = new RequestParams();
        params.add("UserPhone",phonenumber);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr = new String(bytes);
                try {
                    VCodeModel vCodeModel = new Gson().fromJson(reponseStr,VCodeModel.class);
                    if (vCodeModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(vCodeModel.getMsg());
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

    public void vCode(String phonenumber, String vcode, Context context, Listener listener){

        url = "service/CheckCode.php";
        params = new RequestParams();
        params.add("UserPhone",phonenumber);
        params.add("Vcode",vcode);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr = new String(bytes);
                try {
                VCodeModel vCodeModel = new Gson().fromJson(reponseStr,VCodeModel.class);
                if (vCodeModel.getState()==1){
                    listener.onSuccess();
                }else {
                    listener.onFailure(vCodeModel.getMsg());
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
}
