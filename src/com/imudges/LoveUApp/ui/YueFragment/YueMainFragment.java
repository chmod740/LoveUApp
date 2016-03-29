package com.imudges.LoveUApp.ui.YueFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.ReFresh.RefreshableView;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import com.imudges.LoveUApp.ui.ReFresh.ReFreshId;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/10.
 * 自习
 */
public class YueMainFragment extends Fragment {

    private RefreshableView refreshableView;

    private String url;
    private String responStr;
    private RequestParams params;
    private List<YueStudyModel> studyModels;
    YueAdapter adapter;

    ListView listView;

    private List<String> user_id;
    private List<String> title;
    private List<String> location;
    private List<String> Url;
    private List<String> time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yue_main_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view =getView();
        user_id=new ArrayList<String>();
        title=new ArrayList<String>();
        location=new ArrayList<String>();
        Url=new ArrayList<String>();
        time=new ArrayList<String>();

        listView=(ListView) getView().findViewById(R.id.yue_main_list_ot);
        GetStudy();

        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
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
        }, ReFreshId.Yue_Main_Study);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String send_Username=null;
                send_Username = user_id.get(i);
                if (send_Username != null){
                    MainYueActivity.setUserName(send_Username);
                    Intent intent = new Intent(getActivity(),StudyDetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * 获取自习表中所有信息
     */
    public void GetStudy(){
        url="xueservice/DownXueService.php";
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
                    studyModels = gson.fromJson(responStr,new TypeToken<List<YueStudyModel>>(){}.getType());
                    int j;
                    for(j=0;j<studyModels.size();j++) {
                        title.add(studyModels.get(j).getPostUser());
                        time.add(studyModels.get(j).getXueTime());
                        location.add(studyModels.get(j).getXueInformation());
                        user_id.add(j,studyModels.get(j).getXueId()+"");
                        Url.add(studyModels.get(j).getPostImage());
                    }
                    adapter=new YueAdapter(getActivity().getApplicationContext(),Url,title,location,time,listView);
                    listView.setAdapter(adapter);
                }catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
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
