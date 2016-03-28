package com.imudges.LoveUApp.ui.YueFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class YueMainFragment extends ListFragment {

    private RefreshableView refreshableView;

    private String url;
    private String responStr;
    private RequestParams params;
    private List<YueStudyModel> studyModels;

    private Bitmap bitmap;
    private int Length = 0;
    private List<String> user_id=new ArrayList<>();
    private List<String> ID=new ArrayList<>();
    Map<String, Object> map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        View view =getView();

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                GetStudy(),
                R.layout.run_1,
                new String[]{"img", "title", "time", "location"},
                new int[]{R.id.img, R.id.title, R.id.text1, R.id.text2}
        );
        setListAdapter(adapter);
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
        }, ReFreshId.Yue_Main_Study);
    }
    /**
     * 获取自习表中所有信息
     */
    public List<Map<String, Object>> GetStudy(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    studyModels = gson.fromJson(responStr,new TypeToken<List<YueStudyModel>>(){}.getType());

                    Length=studyModels.size();
                    int j;
                    for(j=0;j<Length;j++) {
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("title",studyModels.get(j).getPostUser());
                        map.put("time", studyModels.get(j).getXueTime());
                        map.put("location", studyModels.get(j).getXueInformation());
                        downPhoto(studyModels.get(j).getPostImage());
                        user_id.add(j,studyModels.get(j).getXueId()+"");
//                        Drawable drawable=new BitmapDrawable(getActivity().getApplicationContext().getResources(),bitmap);
//                        new ImageView(getActivity().getApplicationContext()).setImageBitmap(bitmap);
                        map.put("img",bitmap);
                        list.add(map);
                    }
                    //change();
                }catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }
    public void downPhoto(String Urldownphoto){
        new Thread(){
            @Override
            public void run() {
                try {
                    //创建一个url对象
                    URL url=new URL(Urldownphoto);
                    //打开URL对应的资源输入流
                    InputStream is= url.openStream();
                    //从InputStream流中解析出图片
                    bitmap = BitmapFactory.decodeStream(is);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String send_Username=null;
        send_Username = user_id.get(position);
        if (send_Username != null){
            MainYueActivity.setUserName(send_Username);
            Intent intent = new Intent(getActivity(),StudyDetailActivity.class);
            startActivity(intent);
        }
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
