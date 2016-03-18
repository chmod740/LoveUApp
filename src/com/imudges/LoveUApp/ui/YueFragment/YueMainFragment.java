package com.imudges.LoveUApp.ui.YueFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/10.
 */
public class YueMainFragment extends ListFragment {

    private String url;
    private String responStr;
    private RequestParams params;
    private List<YueStudyModel> studyModels;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Bitmap bitmap;

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
                GetStudy(),
                R.layout.run_1,
                new String[]{"img", "title", "time", "location"},
                new int[]{R.id.img, R.id.title, R.id.text1, R.id.text2}
        );
        setListAdapter(adapter);
    }
    /**
     * 获取自习表中所有信息
     */
    public List<Map<String, Object>> GetStudy(){
        url="xueservice/DownXueService.php";
        params=new RequestParams();
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    studyModels = gson.fromJson(responStr,new TypeToken<List<YueStudyModel>>(){}.getType());

                    Map<String, Object> map;
                    int Length=studyModels.size();
                    int j;
                    for(j=Length-1;j>=0;j--) {
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("title",studyModels.get(j).getPostUser());
                        map.put("time", studyModels.get(j).getXueTime());
                        map.put("location", studyModels.get(j).getXueInformation());
                        downPhoto(studyModels.get(j).getPostImage());
//                        Drawable drawable=new BitmapDrawable(getActivity().getApplicationContext().getResources(),bitmap);
//                        new ImageView(getActivity().getApplicationContext()).setImageBitmap(bitmap);
                        map.put("img",bitmap);
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
}
