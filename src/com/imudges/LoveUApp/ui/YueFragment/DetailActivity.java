package com.imudges.LoveUApp.ui.YueFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.Timer.DateTimePickDialogUtil;

import static com.imudges.LoveUApp.ui.R.id.run_details_time;

/**
 * Created by 1111 on 2016/3/17.
 */
public class DetailActivity extends Activity{

    private String userName = null;
    private TextView tv_userName,tv_sex,tv_submitTime,tv_time,tv_other;
    private Button btn_button;

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

        btn_button.setText("约");

        //<-----------获取userName---------->
        userName = MainYueActivity.getUserName();
        if(userName == null){
            Toast.makeText(this,"userName is null",Toast.LENGTH_LONG).show();
        }
        else {
            tv_userName.setText(userName);
        }
        //<--------------------------------->

        btn_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this,"需要添加",Toast.LENGTH_LONG).show();
            }
        });
    }

}
