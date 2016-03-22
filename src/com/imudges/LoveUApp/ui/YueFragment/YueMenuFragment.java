package com.imudges.LoveUApp.ui.YueFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.ui.*;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 1111 on 2016/3/10.
 */
public class YueMenuFragment extends Fragment {

    private ImageView userImage,userSetImg;
    private TextView userTv,userSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container,false);
        userImage=(ImageView)view.findViewById(R.id.menu_img_user);
        userSetImg = (ImageView) view.findViewById(R.id.userset_img);
        userTv=(TextView) view.findViewById(R.id.menu_text);
        userSet=(TextView) view.findViewById(R.id.userSet);
        //tvOutput.setText (savedInstanceState.getString ("output"));

        setUser();

        ListView listView = (ListView) view.findViewById(R.id.menu_list);
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.slidingmenu_item,
                new String[]{"text"},
                new int[]{R.id.item}
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainMealActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainPresentActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainSellActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainCooperationActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainSyllabusActivity.class));
                        break;
                }
                getActivity().finish();
            }
        });

        userSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),MainSetActivity.class));
            }
        });

        userSetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),MainSetActivity.class));
            }
        });
        return view;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("text", "主菜单");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "吃饭" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "赠送");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "拍卖");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "互助");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "课程表");
        list.add(map);

        return list;
    }

    /**
     * 显示用户
     */
    public void setUser(){
        Get get=new Get("User",getActivity().getApplicationContext());
        //UserName.setText(get.getout("username",""));
        Get get1=new Get("Nick",getActivity().getApplicationContext());
        //Toast.makeText(getActivity().getApplicationContext(),get1.getout(get.getout("username",""),get.getout("username","")) , Toast.LENGTH_LONG).show();
        userTv.setText(get1.getout(get.getout("username",""),get.getout("username","")));

        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        userImage.setImageBitmap(bitmap);
    }
}
