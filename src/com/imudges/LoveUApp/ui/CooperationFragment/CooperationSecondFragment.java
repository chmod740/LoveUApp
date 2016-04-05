package com.imudges.LoveUApp.ui.CooperationFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.MainCooperationActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.Timer.HelpDateTimePickDialogUitl;

/**
 * Created by 1111 on 2016/3/14.
 */
public class CooperationSecondFragment extends Fragment{

    private Button bt,btn;
    private EditText neirong,biaoti;
    private String initEndDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private EditText edtime;
    private String neirongString,biaotiString;
    private TextView shengyu1,shengyu2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cooperation_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shengyu1 = (TextView)getView().findViewById(R.id.cooperation2_shengyu1);
        shengyu2 = (TextView)getView().findViewById(R.id.cooperation2_shengyu2);
        shengyu1.setText("还可以输入20个字");
        shengyu2.setText("还可以输入100个字");
        bt=(Button) getView().findViewById(R.id.cooperation2_tijiao);
        edtime = new EditText(getActivity().getApplicationContext());
        neirong = (EditText) getView().findViewById(R.id.cooperation2_neirong);
        biaoti = (EditText) getView().findViewById(R.id.cooperation2_biaoti);
        biaoti.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num;
                num=20-editable.length();
                shengyu1.setText("还可以输入"+num+"个字");
            }
        });
        neirong.addTextChangedListener(new TextWatcher() {
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
                shengyu2.setText("还可以输入"+num+"个字");
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaotiString=biaoti.getText().toString();
                neirongString=neirong.getText().toString();
                biaoti.setText("");
                neirong.setText("");
                if(biaotiString.equals("")||neirongString.equals("")){
                    Toast.makeText(getActivity().getApplicationContext(), "请完善信息！", Toast.LENGTH_SHORT).show();
                }else{
                    HelpDateTimePickDialogUitl helpDateTimePickDialogUitl =
                            new HelpDateTimePickDialogUitl(getActivity(),initEndDateTime,neirongString,biaotiString);
                    helpDateTimePickDialogUitl.dateTimePicKDialog(edtime);
                }
            }
        });

        btn = (Button) getView().findViewById(R.id.cooperation_top_2_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainCooperationActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });
    }
}
