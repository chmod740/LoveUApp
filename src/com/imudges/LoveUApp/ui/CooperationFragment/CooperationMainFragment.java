package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.HelpModel;

import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.ReFresh.ReFreshId;
import com.imudges.LoveUApp.ui.ReFresh.RefreshableView;

import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

import java.util.List;


/**
 * Created by 1111 on 2016/3/14.
 */
public class CooperationMainFragment extends Fragment {

    private RefreshableView refreshableView;

    private String url;
    private String responStr;
    private RequestParams params;
    private List<HelpModel> helpModels;
    private int Length;

    private ListView listView;
    private CooperationAdapter adpter;

    public List<String> URL;
    public List<String> postname;
    public List<String> info;
    public List<String> time;
    public List<String> money;
    public List<String> HelpId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cooperation_3_1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        URL = new ArrayList<String>();
        postname=new ArrayList<String>();
        time=new ArrayList<String>();
        info=new ArrayList<String>();
        money=new ArrayList<String>();
        HelpId=new ArrayList<String>();
        GetSell();
        listView = (ListView) getView().findViewById(R.id.cooperation_3_1);
        adpter = new CooperationAdapter(getActivity().getApplicationContext(), URL,info,time,money,postname,listView);
        listView.setAdapter(adpter);

        refreshableView = (RefreshableView) getView().findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    next();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, ReFreshId.Cooperation_Main);
    }

    public void GetSell(){
        url="helpservice/DownHelpService.php";
        params=new RequestParams();
        Get get=new Get("User",getActivity().getApplicationContext());
        Get get1=new Get("UserKey",getActivity().getApplicationContext());
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));

        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    Gson gson=new Gson();
                    helpModels = gson.fromJson(responStr,new TypeToken<List<HelpModel>>(){}.getType());
                    Length=helpModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        URL.add(helpModels.get(j).getHelpImage());
                        postname.add(helpModels.get(j).getPostUser());
                        time.add(helpModels.get(j).getDownTime());
                        money.add(helpModels.get(j).getHelpMoney());
                        info.add(helpModels.get(j).getHelpInformation());
                        HelpId.add(helpModels.get(j).getHelpId()+"");
                    }
                }catch(Exception e){
                    //Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity().getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void next(){
        new Thread(){
            @Override
            public void run() {
                handler.sendEmptyMessage(0x9529);
            }
        }.start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9529) {
                onActivityCreated(null);
            }
        }
    };
}
