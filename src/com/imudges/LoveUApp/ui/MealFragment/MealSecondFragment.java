package com.imudges.LoveUApp.ui.MealFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.Timer.MealDateTimePickDialogUitl;

/**
 * Created by 1111 on 2016/3/14.
 */
public class MealSecondFragment extends Fragment {

    private Button bt;
    private String infomation;
    private EditText info;
    private EditText ed;
    private String initEndDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private EditText add;
    private String addrss;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3;
    private int which = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meal_2, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt = (Button) getView().findViewById(R.id.meal2_bt);
        ed = new EditText(getActivity().getApplicationContext());
        info = (EditText) getView().findViewById(R.id.meal2_ed2);
        add = (EditText) getView().findViewById(R.id.meal2_ed3);
        radioGroup = (RadioGroup) getView().findViewById(R.id.meal2_group);
        rb1 = (RadioButton) getView().findViewById(R.id.meal2_radio1);
        rb2 = (RadioButton) getView().findViewById(R.id.meal2_radio2);
        rb3 = (RadioButton) getView().findViewById(R.id.meal2_radio3);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //Toast.makeText(getActivity(),"改变",Toast.LENGTH_SHORT).show();
                if (i == rb1.getId())
                    which = 1;
                if (i == rb2.getId())
                    which = 0;
                if (i == rb3.getId())
                    which = -1;
                //Toast.makeText(getActivity(),""+which,Toast.LENGTH_SHORT).show();

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                infomation = info.getText().toString();
                addrss = add.getText().toString();
                Toast.makeText(getActivity(), addrss, Toast.LENGTH_SHORT).show();
                MealDateTimePickDialogUitl dateTimePicKDialog = new MealDateTimePickDialogUitl(
                        getActivity(), initEndDateTime, infomation, addrss, which);
                dateTimePicKDialog.dateTimePicKDialog(ed);


            }
        });
    }
    /*public void run2_click(View v){
        infomation = info.getText().toString();
    }*/
}
