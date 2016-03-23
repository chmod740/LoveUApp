package com.imudges.LoveUApp.ui.SellFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/3/14.
 */
public class SellSecondFragment extends Fragment {

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText title,info;
    private Button selectimg,sure;
    private String time;
    private String titleString,infoString;//EditText 中的数据
    private static final String[] m={"三天","五天","七天"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sell_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title = (EditText) getView().findViewById(R.id.sell_2_title);
        info = (EditText) getView().findViewById(R.id.sell_2_info);
        selectimg = (Button) getView().findViewById(R.id.sell_2_selectimg);
        sure = (Button) getView().findViewById(R.id.sell_2_sure);
        spinner = (Spinner)getView().findViewById(R.id.sell_2_spinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinnertext,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            Toast.makeText(getActivity(),m[arg2],Toast.LENGTH_SHORT).show();
            switch (arg2){
                case 0:time="三天";break;
                case 1:time="五天";break;
                case 2:time="七天";break;
                default:time="错误数据";break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
