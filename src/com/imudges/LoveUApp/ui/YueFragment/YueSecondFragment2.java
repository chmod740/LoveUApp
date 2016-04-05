package com.imudges.LoveUApp.ui.YueFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.Timer.DateTimePickDialogUtil;

/**
 * Created by 1111 on 2016/3/16.
 */
public class YueSecondFragment2 extends Fragment {
    private Button bt;
    private String infomation;
   // private String addRess;
    private EditText info;
    private EditText ed;
    private EditText add;
    private String addrss;
    //private EditText address;
    private String initEndDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private TextView shengyu,shengyu1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.run_2_2, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        shengyu = (TextView)getView().findViewById(R.id.run2_2_shengyu);
        shengyu1 = (TextView)getView().findViewById(R.id.run2_2_shengyu1);
        bt =(Button)getView().findViewById(R.id.run2_2_bt);
        ed = new EditText(getActivity().getApplicationContext());
        info = (EditText) getView().findViewById(R.id.run2_2_ed2);
        add = (EditText)getView().findViewById(R.id.run2_2_ed3);
        shengyu.setText("还可以输入100个字");
        shengyu1.setText("还可以输入10个字");
        info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num;
                num=100-editable.length();
                shengyu.setText("还可以输入"+num+"个字");
            }
        });
        add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num;
                num=10-editable.length();
                shengyu1.setText("还可以输入"+num+"个字");
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                infomation = info.getText().toString();
                addrss = add.getText().toString();
                //将两个框内文字清除
                info.setText("");
                add.setText("");
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initEndDateTime,infomation,addrss,2);
                dateTimePicKDialog.dateTimePicKDialog(ed);

            }
        });
    }
}
