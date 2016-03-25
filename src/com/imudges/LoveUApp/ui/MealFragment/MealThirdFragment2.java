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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.MealModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MealThirdFragment2 extends Fragment {

    private String responStr;
    private List<MealModel> MealModels;
    private int Length=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView;
        SimpleAdapter simpleAdapter;

        listView = (ListView) getView().findViewById(android.R.id.list);
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.default1);
        PhotoCut photoCut = new PhotoCut(getActivity().getApplicationContext());
        Bitmap bitmap1 = photoCut.toRoundBitmap(bitmap);
        //imageView.setImageBitmap(bitmap1);
        simpleAdapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.item_meal_3_2,
                new String[] { "img", "title", "time", "location","man","way"},
                new int[] { R.id.meal3_2_img, R.id.meal3_2_tx1, R.id.meal3_2_tx2, R.id.meal3_2_tx3,R.id.meal3_2_tx4 ,R.id.meal3_2_tx5}
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
        RequestParams params;
        String url;

        url="foodservice/DownFoodService.php";
        params=new RequestParams();
        Get get=new Get("User",getActivity().getApplicationContext());
        Get get1=new Get("UserKey",getActivity().getApplicationContext());
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));
        HttpRequest.get(getActivity().getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try {
//                    System.out.println(responStr);
                    Gson gson = new Gson();
                    MealModels = gson.fromJson(responStr, new TypeToken<List<MealModel>>() {}.getType());

                    Length = MealModels.size();
                    int j;
                    for (j = 0; j < Length; j++) {
                        Map<String, Object> map;
                        int img = R.drawable.ic_launcher;
                        map = new HashMap<String, Object>();
                        map.put("title", MealModels.get(j).getUsername());
                        map.put("time", MealModels.get(j).getFoodTime());
                        map.put("location", MealModels.get(j).getFoodArea());
                        map.put("img", img);
                        map.put("man",MealModels.get(j).getFriendname());
                        map.put("way",MealModels.get(j).getFoodWay());
                        list.add(map);
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }
}
