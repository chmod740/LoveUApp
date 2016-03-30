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
 * Created by dy on 2016/3/30.
 */
public class UpDataService {
    private RequestParams params;
    private String reponseStr;
    private String url;
    public void updata(Context context, String AppState, Listener listener) {
        url = "bean/config.php";
        params = new RequestParams();
        params.add("nowversion",AppState);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try{
                    HelpModel model=new Gson().fromJson(reponseStr,HelpModel.class);
                    if(model.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(model.getMsg());
                    }
                }catch (Exception e){
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }
}
