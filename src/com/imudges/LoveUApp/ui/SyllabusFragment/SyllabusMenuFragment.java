package com.imudges.LoveUApp.ui.SyllabusFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/14.
 */
public class SyllabusMenuFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Toast.makeText(getActivity(),"Syllabus",Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container,false);

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
                        startActivity(new Intent(getActivity().getApplicationContext(),MainYueActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainMealActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainPresentActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainSellActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainCooperationActivity.class));
                        break;
                }
                getActivity().finish();
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
        map.put("text", "约" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "吃饭");
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

        return list;
    }
}