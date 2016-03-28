package com.imudges.LoveUApp.ui.PresentFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.GetPresentModel;
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
 * Created by 1111 on 2016/3/21.
 */
public class PresentThirdFragment2 extends Fragment {
    private ListView listView;
    private RefreshableView refreshableView;
    private String url;
    private String responStr;
    private RequestParams params;
    private List<GetPresentModel> getPresentModels;

    private PresentAdapter adapter;
    private List<String> user_id;
    private List<String> Url;
    private List<String> Name;
    private List<String> info;
    private List<String> getstate;
    private String username;
    private String secretkey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.present_main_list, container, false);
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.present_list);
        loadData(getActivity());
        Url=new ArrayList<>();
        Name=new ArrayList<>();
        user_id=new ArrayList<>();
        info=new ArrayList<>();
        getstate=new ArrayList<>();
        GetPresent();

        adapter=new PresentAdapter(getActivity().getApplicationContext(),Url,Name,info,getstate,listView);
        listView.setAdapter(adapter);

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
        }, ReFreshId.Present_3);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(),PresentDetailActivity1.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",user_id.get(i));
                bundle.putBoolean("set",false);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void GetPresent(){
        url="giveservice/DownGServiceme.php";
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
                    getPresentModels = gson.fromJson(responStr,new TypeToken<List<GetPresentModel>>(){}.getType());
                    System.out.println(username);
                    int j;

                    for(j=0;j<getPresentModels.size();j++) {
                        Name.add(getPresentModels.get(j).getGiveUser());
                        info.add(getPresentModels.get(j).getGiveInformation());
                        user_id.add(j,getPresentModels.get(j).getGiveId()+"");
                        Url.add(getPresentModels.get(j).getGiveImage());
                        System.out.println("是否被拿走"+getPresentModels.get(j).getGetUserName()+getPresentModels.get(j).getGetUser());
                        System.out.println("图片地址"+getPresentModels.get(j).getGiveImage());
                        if(getPresentModels.get(j).getGetUserName()==null)
                        {
                                getstate.add("没送人");
                        continue;
                        }
                        if(getPresentModels.get(j).getGetUserName().equals(username))
                        {
                            getstate.add("送给我");
                        continue;
                        }
                        getstate.add("送给别人");
                       // System.out.println(getPresentModels.get(j).getGetUser());
//                        getstate.add("送给别人");
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
    private void loadData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        username = sp.getString("username", "").toString();
        SharedPreferences sd = context.getSharedPreferences("UserKey", Context.MODE_PRIVATE);
        secretkey = sd.getString("secretkey", "").toString();
    }

}
