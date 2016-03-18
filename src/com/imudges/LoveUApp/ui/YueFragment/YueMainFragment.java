package com.imudges.LoveUApp.ui.YueFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.model.YueStudyModel;
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
 * Created by 1111 on 2016/3/10.
 */
public class YueMainFragment extends ListFragment {

    private String url="";
    private String responStr;
    private RequestParams params;
    List<YueStudyModel> studyModels;

    private int numOfList=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.run_1,
                new String[] { "img", "title", "time", "location" },
                new int[] { R.id.img, R.id.title, R.id.text1, R.id.text2 }
        );
        setListAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        numOfList = 10;
        Map<String, Object> map;
        for(int i = numOfList-1; i>=0; i--){
            int img = R.drawable.ic_launcher;

            map = new HashMap<String, Object>();
            map.put("title",  "1-1的title");
            map.put("time",  "time");
            map.put("location", "location");
            map.put("img", img);
            list.add(map);
        }

        return list;
    }

    /**
     * 获取自习表中所有信息
     */
    public List<Map<String, Object>> GetStudy(){
        url="xueservice/DownXueService.php";
        params=new RequestParams();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    studyModels = gson.fromJson(responStr,new TypeToken<List<YueStudyModel>>(){}.getType());
                    System.out.println(studyModels.get(0).getPostUser());
                    System.out.println(studyModels.get(1).getState());
                    System.out.println(studyModels.size());

                    Map<String, Object> map;
                    int Length=studyModels.size();
                    int j;
                    for(j=Length-1;j>=0;j--) {
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("title",studyModels.get(j).getPostUser());
                        map.put("time", studyModels.get(j).getXueTime());
                        map.put("location", studyModels.get(j).getXueInformation());
                        map.put("img", img);
                        list.add(map);
                    }
                    //onActivityCreated(null);
                }catch(Exception e){
                    //System.out.println(e.getLocalizedMessage()+"*******************8");
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
}
