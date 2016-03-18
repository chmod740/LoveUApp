package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by dy on 2016/3/18.
 * 设置界面
 */
public class MainSetActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting);
    }

}
