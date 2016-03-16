package com.imudges.LoveUApp.ui.YueFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/10.
 */
public class YueMainFragment extends ListFragment {

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
}
