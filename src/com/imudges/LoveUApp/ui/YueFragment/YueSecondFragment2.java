package com.imudges.LoveUApp.ui.YueFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    //private EditText address;
    private String initEndDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.run_2_2, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt =(Button)getView().findViewById(R.id.run2_2_bt);
        ed = new EditText(getActivity().getApplicationContext());
        info = (EditText) getView().findViewById(R.id.run2_2_ed2);
       // address = (EditText) getView().findViewById(R.id.run2_2_ed3);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                infomation = info.getText().toString();
                //addRess = address.getText().toString();
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initEndDateTime,infomation);
                dateTimePicKDialog.dateTimePicKDialog(ed);


            }
        });
    }
}
