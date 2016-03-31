package com.imudges.LoveUApp.ui.YueFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.YueRunModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.service.Myadapter;
import com.imudges.LoveUApp.ui.ReFresh.ReFreshId;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.ReFresh.RefreshableView;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/16.
 */
public class YueThirdFragment2 extends Fragment{

    private RefreshableView refreshableView;
    private ListView lv;

    private int Length;

    private String url;
    private String responStr;
    private RequestParams params;
    public List<String> URL;
    public List<String> name;
    public List<String> info;
    public List<String> area;
    public List<String> time;
    public List<String> state;
    Myadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.run_3_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        GetStudy();

        lv = (ListView) getView().findViewById(R.id.run3_3_list2);

        URL = new ArrayList<String>();
        name=new ArrayList<String>();
        info=new ArrayList<String>();
        time=new ArrayList<String>();
        area=new ArrayList<String>();
        state=new ArrayList<String>();


        adapter = new Myadapter(getActivity().getApplicationContext(), URL,name,info,area,time,state,lv);
        lv.setAdapter(adapter);
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
        }, ReFreshId.Yue_Third_Get);
    }

    public void GetStudy(){
        url="xueservice/DownXServiceme.php";
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
                    List<YueStudyModel> studyModels = gson.fromJson(responStr,new TypeToken<List<YueStudyModel>>(){}.getType());

                    Length=studyModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        URL.add(studyModels.get(j).getPostImage());
                        name.add(studyModels.get(j).getPostUser());
                        area.add(studyModels.get(j).getXueArea());
                        time.add(studyModels.get(j).getXueTime());
                        info.add(studyModels.get(j).getXueInformation());
                        state.add("学");
                    }
                }catch(Exception e){
                   // Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity().getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        url="runservice/DownRServiceme.php";
        params=new RequestParams();
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    //System.out.println(responStr);
                    Gson gson=new Gson();
                    List<YueRunModel> studyModels = gson.fromJson(responStr,new TypeToken<List<YueRunModel>>(){}.getType());

                    Length=studyModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        URL.add(studyModels.get(j).getPostImage());
                        name.add(studyModels.get(j).getPostUser());
                        area.add(studyModels.get(j).getRunArea());
                        time.add(studyModels.get(j).getRunTime());
                        info.add(studyModels.get(j).getRunInformation());
                        state.add("跑");
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
                handler.sendEmptyMessage(0x9527);
            }
        }.start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                onActivityCreated(null);
            }
        }
    };
}
