package com.imudges.LoveUApp.ui.YueFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/13.
 */
public class YueThirdFragment extends Fragment {
    private ImageView imageView;
    private SimpleAdapter simpleAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.run_3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.run3_3_list1);
        imageView = (ImageView)getView().findViewById(R.id.run3_img);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.default1);
        PhotoCut photoCut = new PhotoCut(getActivity().getApplicationContext());
        Bitmap bitmap1 = photoCut.toRoundBitmap(bitmap);
        //imageView.setImageBitmap(bitmap1);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_run_3_1,
                new String[] { "img", "title", "time", "location" },
                new int[] { R.id.run3_img, R.id.run3_tx1, R.id.run3_tx2, R.id.run3_tx3 }
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
            map.put("time",  "时间");
            map.put("location", "人");
            map.put("img", img);
            list.add(map);
        }

        return list;
    }


}
