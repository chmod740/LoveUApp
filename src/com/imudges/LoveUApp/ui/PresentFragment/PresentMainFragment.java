package com.imudges.LoveUApp.ui.PresentFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.GetPresentModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.service.PhotoCut;
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
 * Created by 1111 on 2016/3/14.
 */
public class PresentMainFragment extends Fragment{

    private SimpleAdapter simpleAdapter;
    private ListView listView;
    private String url;
    private String responStr;
    private RequestParams params;
    private List<GetPresentModel> getPresentModels;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Bitmap bitmap;
    private int Length = 0;
    private List<String> user_id=new ArrayList<>();
    private List<String> ID=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.present_1, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.present_1_list);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_present_1,
                new String[] { "man","img","info"},
                new int[] { R.id.present_1_man,R.id.present_1_img,R.id.present_1_info }
        );
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        int numOfList = 10;
        Map<String, Object> map;
        for(int i = numOfList-1; i>=0; i--){
            int img = R.drawable.default1;

            map = new HashMap<String, Object>();
            map.put("man",  "拍卖人");
            map.put("img", img);
            map.put("info","详细信息");
            list.add(map);
        }

        return list;
    }


    public List<Map<String, Object>> GetStudy(){
        url="xueservice/DownXueService.php";
        params=new RequestParams();
        Get get=new Get("User",getActivity().getApplicationContext());
        Get get1=new Get("UserKey",getActivity().getApplicationContext());
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get.getout("secretkey",""));
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    getPresentModels = gson.fromJson(responStr,new TypeToken<List<GetPresentModel>>(){}.getType());

                    Map<String, Object> map;
                    Length=getPresentModels.size();
                    int j,ii=0;
                    for(j=Length-1;j>=0;j--,ii++) {
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("man", getPresentModels.get(j).getPostUser());
                        map.put("money","买拍价钱");
                        downPhoto(getPresentModels.get(j).getPostImage());
                        user_id.add(ii,getPresentModels.get(j).getPresentId()+"");
//                        Drawable drawable=new BitmapDrawable(getActivity().getApplicationContext().getResources(),bitmap);
//                        new ImageView(getActivity().getApplicationContext()).setImageBitmap(bitmap);
                        map.put("img",bitmap);
                        list.add(map);
                    }
                    change();
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
    public void change(){
        int j=user_id.size()-1;
        for(int i=0;i<user_id.size();i++,j--){
            ID.add(i,user_id.get(j));
        }
    }
}
