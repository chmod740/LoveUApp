package com.imudges.LoveUApp.ui.YueFragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.StudyModel;
import com.imudges.LoveUApp.model.YueRunModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.RunService;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.InputStream;
import java.net.URL;
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
    private Bitmap bitmap;

    private String user_id = null;
    private TextView tv_userName,tv_sex,tv_submitTime,tv_time,tv_other;
    private Button btn_button, btn_above;
    private int Length = 0;
    private ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.run_details);
        initView();
    }

    private void initView() {

        tv_userName = (TextView) findViewById(R.id.run_details_name);
        tv_submitTime = (TextView) findViewById(R.id.run_details_submit_time);
        tv_other = (TextView) findViewById(R.id.run_details_other);
        tv_time = (TextView) findViewById(run_details_time);
        btn_button = (Button) findViewById(R.id.run_details_button);
        btn_above = (Button) findViewById(R.id.run_details_above_button);
        userImage=(ImageView) findViewById(R.id.run_details_img);

        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        btn_above.setWidth(width/3);
        btn_button.setWidth(width/3);
        btn_button.setText("  约  ");

        //<-----------获取userName---------->
        user_id = MainYueActivity.getUserName();
        if(user_id == ""||user_id == null){
            Toast.makeText(this,"userName is null",Toast.LENGTH_LONG).show();
        }
        else {
            getInfo();
            MainYueActivity.setUserName("");
        }
        //<--------------------------------->

        btn_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RunService service=new RunService();
                Get get=new Get("User",getApplicationContext());
                Get get1=new Get("UserKey",getApplicationContext());
                service.makeY(get1.getout("secretkey", ""), getApplicationContext(),user_id,get.getout("username", ""), new Listener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "约成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String msg) {
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_above.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               RunDetailActivity.this.finish();
            }
        });
    }
    public void getInfo(){
        url="RunService/DetailRunService.php";
        params=new RequestParams();
        params.add("RunId",user_id);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    //System.out.println(responStr);
                    Gson gson=new Gson();
                    YueRunModel runModel = gson.fromJson(responStr,YueRunModel.class);
                    downPhoto(runModel.getPostImage());
                    tv_submitTime.setText(runModel.getRunTime());
                    tv_userName.setText(runModel.getPostUser());
                    tv_time.setText(runModel.getRunArea());
                    tv_other.setText(runModel.getRunInformation());
                }catch(Exception e){
//                    System.out.println(e.getLocalizedMessage());
//                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9522) {
                //显示从网上下载的图片
                PhotoCut cut=new PhotoCut(getApplicationContext());
                bitmap=cut.toRoundBitmap(bitmap);
                userImage.setImageBitmap(bitmap);
            }
        }
    };
    public void downPhoto(String Urldownphoto){
        new Thread(){
            @Override
            public void run() {
                try {
                    //创建一个url对象
                    URL url=new URL(Urldownphoto);
                    //打开URL对应的资源输入流
                    InputStream is= url.openStream();
                    //从InputStream流中解析出图片
                    bitmap = BitmapFactory.decodeStream(is);

                    handler.sendEmptyMessage(0x9522);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
