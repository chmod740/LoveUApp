package com.imudges.LoveUApp.ui.PresentFragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.imudges.LoveUApp.model.GetPresentModel;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.ReFresh.ReFreshId;
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
 * Created by 1111 on 2016/3/14.
 */
public class PresentThirdFragment extends Fragment {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private RefreshableView refreshableView;
    private String url;
    private String responStr;
    private RequestParams params;
    private List<GetPresentModel> getPresentModels;
    private Bitmap bitmap;
    private  Map<String, Object> map;
    private List<String> user_id=new ArrayList<>();

    private int Length = 0;
    /*private List<String> user_id=new ArrayList<>();
    private List<String> ID=new ArrayList<>();*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(android.R.id.list);


        simpleAdapter = new SimpleAdapter(getActivity(),
                GetPresent(),
                R.layout.item_present_1,
                new String[] { "man","img","info"},
                new int[] { R.id.present_1_man,R.id.present_1_img,R.id.present_1_info }
        );
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(),PresentDetailActivity1.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",user_id.get(i));
                bundle.putBoolean("set",true);
                intent.putExtras(bundle);
                //startActivityForResult(intent, 10);
                startActivity(intent);
            }
        });
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
        }, ReFreshId.Present_2);
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        int numOfList = 10;
        Map<String, Object> map;
        for(int i = numOfList-1; i>=0; i--){
            int img = R.drawable.default1;

            map = new HashMap<String, Object>();
            map.put("title",  "内容");
            map.put("man",  "当前竞拍者");
            map.put("time", "下架时间");
            map.put("img", img);
            map.put("money","买拍价钱");
            list.add(map);
        }

        return list;
    }
    public List<Map<String, Object>> GetPresent(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        url="giveservice/DownGService.php";
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
                    getPresentModels = gson.fromJson(responStr,new TypeToken<List<GetPresentModel>>(){}.getType());

                    Length=getPresentModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("man",getPresentModels.get(j).getGiveUser());
                        map.put("info", getPresentModels.get(j).getGiveInformation());
                        user_id.add(j,getPresentModels.get(j).getGiveId()+"");
                        map.put("img",img);
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
