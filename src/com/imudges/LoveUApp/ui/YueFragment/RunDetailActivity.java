package com.imudges.LoveUApp.ui.YueFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.model.StudyModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.imudges.LoveUApp.ui.R.id.run_details_time;

/**
 * Created by 1111 on 2016/3/22.
 */
public class RunDetailActivity extends Activity {
    private String userName = null,responStr;
    private RequestParams params;
    private String url;
    private TextView tv_userName,tv_sex,tv_submitTime,tv_time,tv_other;
    private Button btn_button, btn_above;
    private int Length = 0;
    private String sex, submitTime,time,other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.run_details);
        GetStudy();
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

        tv_other.setText(other);
        tv_sex.setText(sex);
        tv_time.setText(time);

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

    public void GetStudy(){
        url="runservice/RunService.php";
        params=new RequestParams();
        params.add("UserName",userName);
        HttpRequest.get(RunDetailActivity.this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    System.out.println(responStr);
                    Gson gson=new Gson();
                    StudyModel studyModels = gson.fromJson(responStr, StudyModel.class);
                    time = studyModels.getXueTime();
                    other = studyModels.getXueInformation();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
