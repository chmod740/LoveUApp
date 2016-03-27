package com.imudges.LoveUApp.ui.PresentFragment;

import android.content.Intent;
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
import com.imudges.LoveUApp.model.GetPresentModel;
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
public class PresentMainFragment extends Fragment{

    private ListView listView;
    private String url;
    private String responStr;
    private RequestParams params;
    private List<GetPresentModel> getPresentModels;
    private RefreshableView refreshableView;

    private PresentAdapter adapter;
    private List<String> user_id;
    private List<String> Url;
    private List<String> Name;
    private List<String> info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.present_main_list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.present_list);

        Url=new ArrayList<>();
        Name=new ArrayList<>();
        user_id=new ArrayList<>();
        info=new ArrayList<>();

        GetPresent();

        adapter=new PresentAdapter(getActivity().getApplicationContext(),Url,Name,info,listView);
        listView.setAdapter(adapter);

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
        }, ReFreshId.Present_1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getActivity(),PresentDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",user_id.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void GetPresent(){
        url="giveservice/DownGiveService.php";
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
                    Gson gson=new Gson();
                    getPresentModels = gson.fromJson(responStr,new TypeToken<List<GetPresentModel>>(){}.getType());
                    int j;
                    for(j=0;j<getPresentModels.size();j++) {
                        Name.add(getPresentModels.get(j).getGiveUser());
                        info.add(getPresentModels.get(j).getGiveInformation());
                        user_id.add(j,getPresentModels.get(j).getGiveId()+"");
                        Url.add(getPresentModels.get(j).getGiveImage());
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
