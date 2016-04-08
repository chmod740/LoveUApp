package com.imudges.LoveUApp.ui.CooperationFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.google.gson.Gson;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.model.HelpModel;
import com.imudges.LoveUApp.model.SellModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by dy on 2016/3/29.
 */
public class CooperationDetail extends Activity {

    private TextView money,time,user,info,back;
    private ImageView userimage;
    private Button button;

    private String url;
    private String responStr;
    private RequestParams params;
    private String HelpId;
    private Bitmap bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.cooperation_detial);

        init();
        SetView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        GetInfor();
    }
    public void init(){
        money=(TextView) findViewById(R.id.help_d_money);
        time=(TextView) findViewById(R.id.help_d_uptime);
        user=(TextView) findViewById(R.id.help_d_username);
        info=(TextView) findViewById(R.id.help_d_info);
        back=(TextView) findViewById(R.id.help_d_back);
        userimage=(ImageView)findViewById(R.id.help_d_image);
        button=(Button)findViewById(R.id.help_d_button);
    }
    public void SetView(){
        Intent intent = this.getIntent();
        switch (intent.getStringExtra("State")){
            case "main":sellmain();
                break;
            case "self":sellmy();
                break;
            case "it":helpit();
                break;
        }
    }
    public void sellmain(){
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        button.setWidth(width/3);
        button.setText("我来受理");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auction();
            }
        });
    }
    public void helpit(){
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        button.setWidth(width/3);
        button.setText("受理中");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CooperationDetail.this, "main", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sellmy(){
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        button.setWidth(width/3);
        button.setText("查看状态");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CooperationDetail.this, "等待帮忙中", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void GetInfor(){
        Intent intent = this.getIntent();
        HelpId = intent.getStringExtra("HelpId");
        url="helpservice/DetailHelpService.php";
        params=new RequestParams();
        params.add("HelpId",HelpId);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    HelpModel helpModel=new Gson().fromJson(responStr,HelpModel.class);
                    info.setText(helpModel.getHelpInformation());
                    money.setText(helpModel.getHelpMoney());
                    user.setText(""+helpModel.getPostUser());
                    time.setText(helpModel.getDownTime());
                    downPhoto(helpModel.getHelpImage());
                }catch (Exception e){
                    //Toast.makeText(CooperationDetail.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(CooperationDetail.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void auction(){
        url="helpservice/DoHelpService.php";
        params=new RequestParams();
        Get get=new Get("User",getApplicationContext());
        Get get1=new Get("UserKey",getApplicationContext());
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));
        params.add("HelpId",HelpId);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    SellModel sell=new Gson().fromJson(responStr,SellModel.class);
                    if(sell.getState()==1){
                        Toast.makeText(CooperationDetail.this, "受理开始", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CooperationDetail.this,sell.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    //Toast.makeText(CooperationDetail.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(CooperationDetail.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                //显示从网上下载的图片
                PhotoCut p=new PhotoCut(getApplicationContext());
                bitmap=p.toRoundBitmap(bitmap);
                userimage.setImageBitmap(bitmap);
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
                    //发送消息，通知UI组件显示图片
                    handler.sendEmptyMessage(0x9527);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

