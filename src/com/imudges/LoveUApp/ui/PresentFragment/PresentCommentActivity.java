package com.imudges.LoveUApp.ui.PresentFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.GetPresentModel;
import com.imudges.LoveUApp.model.PresentModel;
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
    CommentAdapter adapter;
    private List<String> name;
    private List<String> info;
    private List<String> Url;

    private RefreshableView refreshableView;
    private String url;
    private String responStr;
    private RequestParams params;
    private List<GetPresentModel> getPresentModels;
    private List<String> user_id=new ArrayList<>();
    private String id;
    private String username;
    private String secretkey;
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
        loadData(getApplicationContext());
        name=new ArrayList<>();
        info=new ArrayList<>();
        Url=new ArrayList<>();
        GetData();

        if (set == true) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                    Toast.makeText(getApplicationContext(),"点击了",Toast.LENGTH_SHORT).show();
//                    //上传
//                    System.out.println("点击后发生的"+secretkey+" "+username+" "+getPresentModels.get(i).getGiveId() + " "+getPresentModels.get(i).getUserName());
                    makeYY(secretkey, username, getPresentModels.get(i).getGiveId() + "", getPresentModels.get(i).getUserName(), getApplicationContext(), new Listener() {

                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String msg) {
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    });
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

    public void GetData(){

        url="giveservice/GetService.php";
        params=new RequestParams();
        params.add("GiveId",id);
        HttpRequest.get(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    Gson gson=new Gson();
                    getPresentModels = gson.fromJson(responStr,new TypeToken<List<GetPresentModel>>(){}.getType());

                    int j;
                    for(j=0;j<getPresentModels.size();j++) {
                        user_id.add(j,getPresentModels.get(j).getGiveId()+"");
                        name.add(getPresentModels.get(j).getUserName());
                        info.add(getPresentModels.get(j).getGetInformation());
                        Url.add(getPresentModels.get(j).getGiveImage());
                    }
                    adapter=new CommentAdapter(getApplicationContext(),Url,name,info,listView);
                    listView.setAdapter(adapter);
                }catch(Exception e){
                    //Toast.makeText(getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
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
                onStart();
            }
        }
    };
    /*private void getId(String id){

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

    }*/

    public void makeYY(String key, String name, String giveId, String user, Context context, Listener listener){
        url="giveservice/FinishGiveService.php";
        params=new RequestParams();
        params.add("UserName",name);
        params.add("SecretKey",key);
        params.add("GiveId",giveId);
        params.add("GetUser",user);
        System.out.println(name+" "+key+" "+giveId+" "+user);
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String reponseStr;
                reponseStr=new String(bytes);
                try{
                    PresentModel studyModel=new Gson().fromJson(reponseStr,PresentModel.class);
                    System.out.println("状态"+studyModel.getState()+"信息"+studyModel.getMsg());
                    if(studyModel.getState()==1){
                        listener.onSuccess();
                    }else{
                        listener.onFailure(studyModel.getMsg());
                    }
                }catch (Exception e){
                    listener.onFailure(e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络错误");
            }
        });
    }
    private void loadData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        username = sp.getString("username", "").toString();
        SharedPreferences sd = context.getSharedPreferences("UserKey", Context.MODE_PRIVATE);
        secretkey = sd.getString("secretkey", "").toString();
    }
}
