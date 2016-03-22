package com.imudges.LoveUApp.ui.YueFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.model.YueRunModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/16.
 */
public class YueMainFragment2 extends ListFragment {
    private String url;
    private String responStr;
    private RequestParams params;
    private List<YueRunModel> studyModels;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private int Length = 0;
    private String[] Username = new String[10];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                GetRun(),
                R.layout.run_1,
                new String[] { "img", "title", "time", "location" },
                new int[] { R.id.img, R.id.title, R.id.text1, R.id.text2 }
        );
        setListAdapter(adapter);
    }
    /**
     * 获取跑步类
     * @return
     */
    public List<Map<String, Object>> GetRun(){
        url="runservice/DownRunService.php";
        params=new RequestParams();
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    studyModels = gson.fromJson(responStr,new TypeToken<List<YueRunModel>>(){}.getType());

                    Map<String, Object> map;
                    Length=studyModels.size();
                    int Length=studyModels.size();
                    int j;
                    for(j=Length-1;j>=0;j--) {
                        int img = R.drawable.ic_launcher;
                        Username[j] = studyModels.get(j).getPostUser();
                        map = new HashMap<String, Object>();
                        map.put("title",studyModels.get(j).getPostUser());
                        map.put("time", studyModels.get(j).getRunTime());
                        map.put("location", studyModels.get(j).getRunInformation());
//                        downPhoto(studyModels.get(j).getPostImage());
//                        Drawable drawable=new BitmapDrawable(getActivity().getApplicationContext().getResources(),bitmap);
//                        new ImageView(getActivity().getApplicationContext()).setImageBitmap(bitmap);
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int num = Length-1;
        String send_Username = null;
        send_Username = Username[num-position];

        if (send_Username != null){
            MainYueActivity.setUserName(send_Username);
            Intent intent = new Intent(getActivity(),RunDetailActivity.class);
            startActivity(intent);
        }
    }
}
