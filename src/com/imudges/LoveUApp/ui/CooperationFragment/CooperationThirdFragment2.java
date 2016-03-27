package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.imudges.LoveUApp.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CooperationThirdFragment2 extends Fragment {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cooperation_3_2, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.cooperation3_2_list);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_cooperation_3_1,
                new String[] { "man","img","info","title"},
                new int[] { R.id.cooperation3_1_man,R.id.cooperation3_1_img,R.id.cooperation3_1_neirong,R.id.cooperation3_1_biaoti }
        );
        listView.setAdapter(simpleAdapter);
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        int numOfList = 10;
        Map<String, Object> map;
        for(int i = numOfList-1; i>=0; i--){
            int img = R.drawable.default1;

            map = new HashMap<String, Object>();
            map.put("title",  "标题");
            map.put("man",  "发出人");
            map.put("img", img);
            map.put("info","内容");
            list.add(map);
        }

        return list;
    }
}
