package com.imudges.LoveUApp.ui.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.R;


/**
 * Created by dy on 2016/3/31.
 */
public class AboutActivity extends Activity {

    private TextView tv1,tv2;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.set_about_view);
        init();
        tv2.setText(getVersion());
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://loveu.iheshulin.com:9999/LoveUniversity/");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                it.setData(uri);
                it.setAction( Intent.ACTION_VIEW);
                AboutActivity.this.startActivity(it);
            }
        });

    }
    public void init(){
        tv1=(TextView)findViewById(R.id.www_set);
        tv2=(TextView) findViewById(R.id.vision);
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}