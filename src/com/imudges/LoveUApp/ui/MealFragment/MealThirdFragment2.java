package com.imudges.LoveUApp.ui.MealFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.imudges.LoveUApp.model.MealModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.MainMealActivity;
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
 * Meal接受
 */
public class MealThirdFragment2 extends Fragment {

    private RefreshableView refreshableView;

    private ListView listView;
    private MealAdapter adpter;
    RequestParams params;
    String url;

    public List<String> URL;
    public List<String> name;
    public List<String> info;
    public List<String> area;
    public List<String> time;
    public List<String> way;

    private String responStr;
    private List<MealModel> MealModels;
    private List<String> meal_id;
    private int Length=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meal_main_list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        URL = new ArrayList<String>();
        name=new ArrayList<String>();
        time=new ArrayList<String>();
        area=new ArrayList<String>();
        way=new ArrayList<String>();
        info=new ArrayList<String>();
        meal_id=new ArrayList<>();
        GetSell();

        listView = (ListView) getView().findViewById(R.id.meal_list_ll);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String send_MealId=null;
                send_MealId = meal_id.get(position);
                if (send_MealId != null){
                    MainMealActivity.setMealId(send_MealId);
                    Intent intent = new Intent(getActivity(),MealDetailActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"onItemClick sendMeal Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

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
        }, ReFreshId.Meal_Main);
    }
    public void GetSell(){
        url="foodservice/DownFServiceme.php";
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
                    MealModels = gson.fromJson(responStr,new TypeToken<List<MealModel>>(){}.getType());
                    Length=MealModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        URL.add(MealModels.get(j).getPostImage());
                        name.add(MealModels.get(j).getPostUser());
                        time.add(MealModels.get(j).getFoodTime());
                        area.add(MealModels.get(j).getFoodArea());
                        way.add(MealModels.get(j).getFoodWay());
                        info.add(MealModels.get(j).getFoodInformation());
                        meal_id.add(MealModels.get(j).getFoodId()+"");
                    }
                    adpter = new MealAdapter(getActivity().getApplicationContext(), URL,name,area,time,way,info,listView);
                    listView.setAdapter(adpter);
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
