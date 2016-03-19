package com.imudges.LoveUApp.ui.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by dy on 2016/3/19.
 */
public class UserSet extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_user);
    }
}
