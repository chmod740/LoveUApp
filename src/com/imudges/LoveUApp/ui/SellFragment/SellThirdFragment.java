package com.imudges.LoveUApp.ui.SellFragment;

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
import com.imudges.LoveUApp.model.SellModel;
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
public class SellThirdFragment extends Fragment {

    private RefreshableView refreshableView;

    private String url;
    private String responStr;
    private RequestParams params;
    private List<SellModel> sellModels;
    private int Length;

    private ListView listView;
    private SellAdpter adpter;

    public List<String> URL;
    public List<String> name;
    public List<String> info;
    public List<String> user;
    public List<String> time;
    public List<String> money;
    public List<String> SellId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sell_main_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GetSell();

        listView = (ListView) getView().findViewById(R.id.sell_list_main);

        URL = new ArrayList<String>();
        name=new ArrayList<String>();
        time=new ArrayList<String>();
        user=new ArrayList<String>();
        money=new ArrayList<String>();
        SellId=new ArrayList<String>();

        adpter = new SellAdpter(getActivity().getApplicationContext(), URL,name,user,time,money,listView);
        listView.setAdapter(adpter);

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
        }, ReFreshId.Sell_Main);
    }

    public void GetSell(){
        url="paiservice/DownPaiService.php";
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
                    sellModels = gson.fromJson(responStr,new TypeToken<List<SellModel>>(){}.getType());
                    Length=sellModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        URL.add(sellModels.get(j).getPaiImage());
                        name.add(sellModels.get(j).getPaiTitle());
                        user.add(sellModels.get(j).getPostUser());
                        time.add(sellModels.get(j).getDownTime());
                        money.add(sellModels.get(j).getPaiMoney());
                        SellId.add(sellModels.get(j).getPaiId()+"");
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
}
