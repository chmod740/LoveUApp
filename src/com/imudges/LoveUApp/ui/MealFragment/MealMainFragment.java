package com.imudges.LoveUApp.ui.MealFragment;

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
 * Created by 1111 on 2016/3/14.
 */
public class MealMainFragment extends Fragment {

    private RefreshableView refreshableView;

    private String responStr;
    private List<MealModel> MealModels;
    private int Length=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView;
        SimpleAdapter simpleAdapter;
        View view = getView();

        listView = (ListView) getView().findViewById(android.R.id.list);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_meal_3_2,
                new String[] { "img", "title", "time", "location","man","way"},
                new int[] { R.id.meal3_2_img, R.id.meal3_2_tx1, R.id.meal3_2_tx2, R.id.meal3_2_tx3,R.id.meal3_2_tx4 ,R.id.meal3_2_tx5}
        );
        listView.setAdapter(simpleAdapter);

        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    next();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, ReFreshId.Meal_Main);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        RequestParams params;
        String url;

        url="foodservice/DownFoodService.php";
        params=new RequestParams();
        Get get=new Get("User",getActivity().getApplicationContext());
        Get get1=new Get("UserKey",getActivity().getApplicationContext());
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try {
//                    System.out.println(responStr);
                    Gson gson = new Gson();
                    MealModels = gson.fromJson(responStr, new TypeToken<List<MealModel>>() {}.getType());

                    Length = MealModels.size();
                    int j;
                    for (j = 0; j < Length; j++) {
                        Map<String, Object> map;
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("title", MealModels.get(j).getUsername());
                        map.put("time", MealModels.get(j).getFoodTime());
                        map.put("location", MealModels.get(j).getFoodArea());
                        map.put("img", img);
                        map.put("man",MealModels.get(j).getFriendname());
                        map.put("way",MealModels.get(j).getFoodWay());
                        list.add(map);
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity(),"MealMain " + e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
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
