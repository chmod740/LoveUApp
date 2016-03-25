package com.imudges.LoveUApp.ui.YueFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.RefreshableView;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/13.
 */
public class YueThirdFragment extends Fragment {

    private RefreshableView refreshableView;
    private SimpleAdapter simpleAdapter;
    private ListView listView,lv;

    private int Length;

    private String url;
    private String responStr;
    private RequestParams params;
    Map<String, Object> map;

    public List<String> URL;
    Myadapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.run_3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        listView = (ListView) getView().findViewById(R.id.run3_3_list1);
        lv = (ListView) getView().findViewById(R.id.run3_3_list1);
        URL = new ArrayList<String>();

        simpleAdapter = new SimpleAdapter(getActivity(),
                GetStudy(),
                R.layout.item_run_3_1,
                new String[] { "img", "title", "time", "location" ,"state"},
                new int[] { R.id.run3_img, R.id.run3_tx1, R.id.run3_tx2, R.id.run3_tx3 ,R.id.run3_1_way}
        );
        adapter = new Myadapter(getActivity().getApplicationContext(), URL,lv);
        //listView.setAdapter(simpleAdapter);
        lv.setAdapter(adapter);
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                new  AlertDialog.Builder(getActivity())
//                        .setTitle("删除" )
//                        .setMessage("确定删除吗？" )
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                /**
//                                 * 删除数据逻辑
//                                 */
//                                Toast.makeText(getActivity(),"删除",Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("否" , null)
//                        .show();
//                return false;
//            }
//        });
        refreshableView = (RefreshableView) getView().findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                    next();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 3);
    }
    public List<Map<String, Object>> GetStudy(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        url="xueservice/DownXService.php";
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
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    List<YueStudyModel> studyModels = gson.fromJson(responStr,new TypeToken<List<YueStudyModel>>(){}.getType());

                    Length=studyModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        int img = R.drawable.default1;
                        map = new HashMap<String, Object>();
                        map.put("title",studyModels.get(j).getXueInformation());
                        map.put("time", studyModels.get(j).getXueTime());
                        map.put("location",studyModels.get(j).getXueArea());
                        map.put("img", img);
                        map.put("state","学");
                        URL.add(studyModels.get(j).getPostImage());
                        list.add(map);
                    }

                }catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity().getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        url="runservice/DownRService.php";
        params=new RequestParams();
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    List<YueRunModel> studyModels = gson.fromJson(responStr,new TypeToken<List<YueRunModel>>(){}.getType());

                    Length=studyModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        int img = R.drawable.default1;
                        map = new HashMap<String, Object>();
                        map.put("title",studyModels.get(j).getPostUser());
                        map.put("time", studyModels.get(j).getRunTime());
                        map.put("location",studyModels.get(j).getRunArea());
                        map.put("img", img);
                        map.put("state","跑");
                        URL.add(studyModels.get(j).getPostImage());
                        list.add(map);
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity().getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
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
