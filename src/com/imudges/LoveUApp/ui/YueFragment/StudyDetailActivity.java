package com.imudges.LoveUApp.ui.YueFragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.StudyService;
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
import static com.imudges.LoveUApp.ui.R.id.userimage;

/**
 * Created by 1111 on 2016/3/17.
 */
public class StudyDetailActivity extends Activity{

    private String url;
    private String responStr;
    private RequestParams params;
    private Bitmap bitmap;

    private String user_id = null;
    private TextView tv_userName,tv_submitTime,tv_time,tv_other;
    private Button btn_button, btn_above;
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
        userImage=(ImageView)findViewById(R.id.run_details_img);

        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        btn_button.setWidth(width/3);
        btn_button.setText("约");

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
                Get get=new Get("User",getApplicationContext());
                Get get1=new Get("UserKey",getApplicationContext());
                StudyService studyService=new StudyService();
                studyService.makeY(get1.getout("secretkey", ""), get.getout("username", ""), user_id, getApplicationContext(), new Listener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(StudyDetailActivity.this, "约成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(StudyDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_above.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StudyDetailActivity.this.finish();
            }
        });
    }
    public void getInfo(){
        url="XueService/DetailXueService.php";
        params=new RequestParams();
        params.add("XueId",user_id);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    //System.out.println(responStr);
                    Gson gson=new Gson();
                    YueStudyModel studyModels = gson.fromJson(responStr,YueStudyModel.class);
                    tv_submitTime.setText(studyModels.getXueTime());
                    tv_userName.setText(studyModels.getPostUser());
                    tv_time.setText(studyModels.getXueArea());
                    tv_other.setText(studyModels.getXueInformation());
                    downPhoto(studyModels.getPostImage());
                    //System.out.println(studyModels.getPostImage());
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
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9521) {
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
                    handler.sendEmptyMessage(0x9521);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
