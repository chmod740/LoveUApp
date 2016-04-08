package com.imudges.LoveUApp.ui.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.SysApplication;

/**
 * Created by dy on 2016/4/1.
 */
public class HelpUserActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.set_help);
        SysApplication.getInstance().addActivity(this);
    }
}
