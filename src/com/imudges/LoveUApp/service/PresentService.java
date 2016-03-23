package com.imudges.LoveUApp.service;

/**
 * 赠送专用类
 */
import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.PresentModel;
import com.imudges.LoveUApp.model.RunModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
public class PresentService {
    private RequestParams params;
    private String reponseStr;
    private String url;

    public void userPost(String usernme,String secretkey,String information,Context context,Listener listener){
        url="giveservice/UpGiveService.php";
        params=new RequestParams();
        params.add("UserName",usernme);
        params.add("GiveInformation",information);
        params.add("SecretKey",secretkey);

        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    PresentModel presentModel= new Gson().fromJson(reponseStr,PresentModel.class);
                    if (presentModel.getState()==1){
                        listener.onSuccess();
                    }else {
                        listener.onFailure(presentModel.getMsg());
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
    public void userGet(String key,Context context,String name,Listener listener){
        url="giveservice/UpGiveService.php";
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
