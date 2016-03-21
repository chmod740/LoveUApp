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
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/3/17.
 */
public class DetailActivity extends Activity{

    private String userName = null;
    private TextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.run_details);
        initView();
    }

    private void initView() {
        userName = MainYueActivity.getUserName();
        if(userName == null){
            Toast.makeText(this,"userName is null",Toast.LENGTH_LONG).show();
        }
        else {
            tv_userName = (TextView) findViewById(R.id.run_details_name);
            tv_userName.setText(userName);
        }
    }

}
