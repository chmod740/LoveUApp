package com.imudges.LoveUApp.ui.PresentFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.GetPresentModel;
import com.imudges.LoveUApp.model.UserModel;
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
 * Created by caolu on 2016/3/26.
 */
public class PresentCommentActivity extends Activity {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private RefreshableView refreshableView;
    private String url;
    private String responStr;
    private RequestParams params;
    private List<GetPresentModel> getPresentModels;
    private int Length = 0;
    private  Map<String, Object> map;
    private List<String> user_id=new ArrayList<>();
    private String id;
    private String responStr1;
    private String url1;
    private RequestParams params1;
    private String username;
    private boolean set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.present_comment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        set = bundle.getBoolean("set");
        listView = (ListView) findViewById(R.id.present_comment_list);
        simpleAdapter = new SimpleAdapter(this,
                GetData(),
                R.layout.item_present_comment,
                new String[]{"man", "info", "img"},
                new int[]{R.id.present_comment_man, R.id.present_comment_info, R.id.present_comment_img}
        );
        listView.setAdapter(simpleAdapter);
        if (set == true) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(getApplicationContext(),"点击了",Toast.LENGTH_SHORT).show();
                }
            });
        }


        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);

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
        }, ReFreshId.Present_1);
    }

    public List<Map<String, Object>> GetData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        url="giveservice/GetService.php";
        params=new RequestParams();
        params.add("GiveId",id);
        HttpRequest.get(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
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
                        //getId(getPresentModels.get(j).getUserId());
                        //System.out.println(username);
                        map.put("man",getPresentModels.get(j).getUserName());
                        map.put("info", getPresentModels.get(j).getGetInformation());
                        map.put("img",R.drawable.ic_launcher);
                        //user_id.add(j,getPresentModels.get(j).getGiveId()+"");

                        //System.out.println("1");
                        //downPhoto(getPresentModels.get(j).getGiveImage());
                        //map.put("img",img);
                        list.add(map);
                    }
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
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
                onStart();
            }
        }
    };
    private void getId(String id){

        url1="service/IdService.php";
        params1=new RequestParams();
        params1.add("UserId",id);
        System.out.println("当前评论用户"+id);
        HttpRequest.get(getApplicationContext(), url1, params1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr1=new String(bytes);
                System.out.println(responStr1);
                Gson gson1=new Gson();
                UserModel userModel = gson1.fromJson(responStr1,UserModel.class);
                username = userModel.getUserName();
                System.out.println(username);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"错误！",Toast.LENGTH_SHORT).show();
            }
        }) ;

    }
}
