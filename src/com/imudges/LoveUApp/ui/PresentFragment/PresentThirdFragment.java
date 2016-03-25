package com.imudges.LoveUApp.ui.PresentFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/14.
 */
public class PresentThirdFragment extends Fragment {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(android.R.id.list);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_sell_1,
                new String[] { "img", "title", "man", "time","money"},
                new int[] { R.id.sell_1_img, R.id.sell_1_biaoti, R.id.sell_1_paimairen, R.id.sell_1_time,R.id.sell_1_money }
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
            map.put("title",  "内容");
            map.put("man",  "当前竞拍者");
            map.put("time", "下架时间");
            map.put("img", img);
            map.put("money","买拍价钱");
            list.add(map);
        }

        return list;
    }
}
