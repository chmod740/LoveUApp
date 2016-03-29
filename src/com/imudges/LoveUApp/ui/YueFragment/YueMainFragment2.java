package com.imudges.LoveUApp.ui.YueFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.YueRunModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.ReFresh.ReFreshId;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.ReFresh.RefreshableView;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

import java.util.List;


/**
 * Created by 1111 on 2016/3/16.
 */
public class YueMainFragment2 extends Fragment {

    private RefreshableView refreshableView;

    private String url;
    private String responStr;
    private RequestParams params;
    private List<YueRunModel> studyModels;
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
                    Intent intent = new Intent(getActivity(),RunDetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    /**
     * 获取自习表中所有信息
     */
    public void GetStudy(){
        url="runservice/DownRunService.php";
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
                    studyModels = gson.fromJson(responStr,new TypeToken<List<YueRunModel>>(){}.getType());
                    int j;
                    for(j=0;j<studyModels.size();j++) {
                        title.add(studyModels.get(j).getPostUser());
                        time.add(studyModels.get(j).getRunTime());
                        location.add(studyModels.get(j).getRunArea());
                        user_id.add(j,studyModels.get(j).getRunId()+"");
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
