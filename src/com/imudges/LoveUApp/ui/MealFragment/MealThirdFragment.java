package com.imudges.LoveUApp.ui.MealFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MealThirdFragment extends Fragment {

    private ListView listView;
    private  ImageView imageView;
    private SimpleAdapter simpleAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meal_3_1, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.meal3_3_list1);
        imageView = (ImageView)getView().findViewById(R.id.meal3_img);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.default1);
        PhotoCut photoCut = new PhotoCut(getActivity().getApplicationContext());
        Bitmap bitmap1 = photoCut.toRoundBitmap(bitmap);
        //imageView.setImageBitmap(bitmap1);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_meal_3_1,
                new String[] { "img", "title", "time", "location","way" },
                new int[] { R.id.meal3_img, R.id.meal3_tx1, R.id.meal3_tx2, R.id.meal3_tx3,R.id.meal3_tx4 }
        );
        listView.setAdapter(simpleAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new  AlertDialog.Builder(getActivity())
                        .setTitle("删除" )
                        .setMessage("确定删除吗？" )
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /**
                                 * 删除数据逻辑
                                 */
                                Toast.makeText(getActivity(),"删除",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("否" , null)
                        .show();
                return false;
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
            map.put("title",  "内容");
            map.put("time",  "时间");
            map.put("location", "人");
            map.put("img", img);
            map.put("way","方式");
            list.add(map);
        }

        return list;
    }
}
