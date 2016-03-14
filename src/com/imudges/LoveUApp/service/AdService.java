package com.imudges.LoveUApp.service;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.internal.ConstructorConstructor;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.AdModel;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by dy on 2016/3/14.
 * get广告
 */
public class AdService {
    private RequestParams params;
    private String url;
    private String reponseStr;
    public void GetAdurl(Context context, Listener listener){
        url = "service/AdService.php";
        params = new RequestParams();
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                reponseStr=new String(bytes);
                try {
                    AdModel adModel = new Gson().fromJson(reponseStr, AdModel.class);
                    if (adModel.getState() == 1) {
                        listener.onSuccess();
                        Save save=new Save("Ad",context);
                    } else {
                        listener.onFailure(adModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("");
            }
        });
    }

}
