package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.HelpModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by dy on 2016/3/28.
 */
public class HelpService {
    private RequestParams params;
    private String reponseStr;
    private String url;
    public void helppost(Context context, String secretkey, String username,String money,
                         String information, String password, String time, Listener listener){
        url="helpservice/DoHelpService.php";
        params=new RequestParams();
        params.add("SecretKey",secretkey);
        params.add("UserName",username);
        params.add("HelpMoney",money);
        params.add("HelpInformation",information);
        params.add("DownTime",time);
        params.add("PayPassword",password);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    HelpModel helpModel=new Gson().fromJson(reponseStr,HelpModel.class);
                    if(helpModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(helpModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络异常");
            }
        });
    }
    public void Help(Context context,String name,String key,String money,Listener listener){
        url="moneyservice/CheckService.php";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
        params.add("Money",money);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try{
                    HelpModel helpModel=new Gson().fromJson(reponseStr,HelpModel.class);
                    if(helpModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(helpModel.getMsg());
                    }
                }catch(Exception e){
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
