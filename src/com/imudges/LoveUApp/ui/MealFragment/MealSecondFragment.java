package com.imudges.LoveUApp.ui.MealFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.ui.MainMealActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.Timer.MealDateTimePickDialogUitl;

/**
 * Created by 1111 on 2016/3/14.
 */
public class MealSecondFragment extends Fragment {

    private Button bt,btn;
    private String infomation;
    private EditText info;
    private EditText ed;
    private String initEndDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private EditText add;
    private String addrss;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private String which = "我请客";
    private TextView shengyu1,shengyu2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meal_2, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        shengyu1 = (TextView)getView().findViewById(R.id.meal2_shengyu1);
        shengyu1.setText("还可以输入100个字");

        shengyu2 = (TextView)getView().findViewById(R.id.meal2_shengyu2);
        shengyu2.setText("还可以输入10个字");
        bt = (Button) getView().findViewById(R.id.meal2_bt);
        ed = new EditText(getActivity().getApplicationContext());
        info = (EditText) getView().findViewById(R.id.meal2_ed2);
        add = (EditText) getView().findViewById(R.id.meal2_ed3);
        radioGroup = (RadioGroup) getView().findViewById(R.id.meal2_group);
        rb1 = (RadioButton) getView().findViewById(R.id.meal2_radio1);
        rb2 = (RadioButton) getView().findViewById(R.id.meal2_radio2);
        rb3 = (RadioButton) getView().findViewById(R.id.meal2_radio3);
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
                shengyu1.setText("还可以输入"+num+"个字");
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
                shengyu2.setText("还可以输入"+num+"个字");
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Toast.makeText(getActivity(),"改变",Toast.LENGTH_SHORT).show();
                if (i == rb1.getId())
                    which = "我请客";
                if (i == rb2.getId())
                    which = "55开";
                if (i == rb3.getId())
                    which = "他/她请客";
//                Toast.makeText(getActivity(),""+which,Toast.LENGTH_SHORT).show();

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                infomation = info.getText().toString();
//                Toast.makeText(getActivity(),infomation,Toast.LENGTH_LONG).show();
                addrss = add.getText().toString();
//                Toast.makeText(getActivity(), addrss, Toast.LENGTH_SHORT).show();
                //将两个框内文字清除
                info.setText("");
                add.setText("");
                MealDateTimePickDialogUitl dateTimePicKDialog = new MealDateTimePickDialogUitl(
                        getActivity(), initEndDateTime, infomation, addrss, which);
                dateTimePicKDialog.dateTimePicKDialog(ed);

            }
        });

        btn = (Button) getView().findViewById(R.id.meal_top_2_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainMealActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });
    }
    /*public void run2_click(View v){
        infomation = info.getText().toString();
    }*/
}
