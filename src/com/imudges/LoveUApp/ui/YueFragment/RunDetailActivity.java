package com.imudges.LoveUApp.ui.YueFragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;

import static com.imudges.LoveUApp.ui.R.id.run_details_time;

/**
 * Created by 1111 on 2016/3/22.
 */
public class RunDetailActivity extends Activity {
    private String userName = null;
    private TextView tv_userName,tv_sex,tv_submitTime,tv_time,tv_other;
    private Button btn_button, btn_above;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.run_details);
        initView();
    }

    private void initView() {

        tv_userName = (TextView) findViewById(R.id.run_details_name);
        tv_sex = (TextView) findViewById(R.id.run_details_sex);
        tv_submitTime = (TextView) findViewById(R.id.run_details_submit_time);
        tv_other = (TextView) findViewById(R.id.run_details_other);
        tv_time = (TextView) findViewById(run_details_time);
        btn_button = (Button) findViewById(R.id.run_details_button);
        btn_above = (Button) findViewById(R.id.run_details_above_button);

        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        btn_above.setWidth(width/3);
        btn_button.setWidth(width/3);
        btn_button.setText("  约  ");

        //<-----------获取userName---------->
        userName = MainYueActivity.getUserName();
        if(userName == ""||userName == null){
            Toast.makeText(this,"userName is null",Toast.LENGTH_LONG).show();
        }
        else {
            tv_userName.setText(userName);
            MainYueActivity.setUserName("");
        }
        //<--------------------------------->

        btn_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(RunDetailActivity.this,"需要添加",Toast.LENGTH_LONG).show();
            }
        });

        btn_above.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               RunDetailActivity.this.finish();
            }
        });
    }
}
