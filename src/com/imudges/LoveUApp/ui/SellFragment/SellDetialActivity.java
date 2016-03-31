package com.imudges.LoveUApp.ui.SellFragment;

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
import android.view.*;
import android.widget.*;
import com.google.gson.Gson;
import com.imudges.LoveUApp.DAO.Get;
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
 * Created by dy on 2016/3/25.
 */
public class SellDetialActivity extends Activity {

    private String url;
    private String responStr;
    private RequestParams params;

    private String SellId,Money,myMoney;
    private TextView uptime,downtime,money,title,information,back,user;
    private ImageView image;
    private Button button;
    private Bitmap bitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.sell_main_detial);
        init();

        GetInfor();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SetView();
    }
    public void init(){
        uptime=(TextView)findViewById(R.id.sell_d_uptime);
        downtime=(TextView)findViewById(R.id.sell_d_downtime);
        money=(TextView)findViewById(R.id.sell_d_money);
        title=(TextView)findViewById(R.id.sell_d_title);
        information=(TextView)findViewById(R.id.sell_d_info);
        back=(TextView)findViewById(R.id.sell_d_back);
        uptime=(TextView)findViewById(R.id.sell_d_uptime);
        user=(TextView)findViewById(R.id.sell_d_username);
        image=(ImageView) findViewById(R.id.sell_d_image);
        button=(Button)findViewById(R.id.sell_d_button);
    }
    public void GetInfor(){
        Intent intent = this.getIntent();
        SellId = intent.getStringExtra("SellId");
        url="paiservice/DetailPaiService.php";
        params=new RequestParams();
        params.add("PaiId",SellId);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    SellModel sell=new Gson().fromJson(responStr,SellModel.class);
                    if(sell.getState()==1){
                        downPhoto(sell.getPaiImage());
                        uptime.setText(sell.getUpTime());
                        downtime.setText(sell.getDownTime());
                        money.setText(sell.getPaiMoney());
                        Money=sell.getPaiMoney();
                        title.setText(sell.getPaiTitle());
                        user.setText(sell.getPostUser());
                        information.setText(sell.getPaiInformation());
                    }
                }catch(Exception e){
//                    System.out.println(e.getLocalizedMessage());
//                    Toast.makeText(SellDetialActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(SellDetialActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
                image.setImageBitmap(bitmap);
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
    public void SetView(){
        Intent intent = this.getIntent();
        switch (intent.getStringExtra("State")){
            case "main":sellmain();
                break;
            case "self":sellmy();
                break;
            case "it":sellit();
                break;
        }
    }
    public void sellmain(){
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        button.setWidth(width/3);
        button.setText("竞价拍卖");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ev=new EditText(SellDetialActivity.this);
                new AlertDialog.Builder(SellDetialActivity.this)
                        .setTitle("请输入竞拍价格")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(ev)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s=ev.getText().toString();
                                Integer now=new Integer(Money);
                                Integer jing=new Integer(s);
                                if(now>=jing){
                                    new AlertDialog.Builder(SellDetialActivity.this)
                                            .setMessage("输入竞拍不能小于当前竞拍！！！")
                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .show();
                                }else{
                                    myMoney=s;
                                    auction(ev.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }
    public void auction(String money){
        url="moneyservice/CheckService.php";
        params=new RequestParams();
        Get get=new Get("User",getApplicationContext());
        Get get1=new Get("UserKey",getApplicationContext());
        params.add("UserName",get.getout("username",""));
        params.add("SecretKey",get1.getout("secretkey",""));
        params.add("Money",money);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    SellModel sell=new Gson().fromJson(responStr,SellModel.class);
                    if(sell.getState()==1){
                        auctionext();
                    }else{
                        Toast.makeText(SellDetialActivity.this,sell.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(SellDetialActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(SellDetialActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void auctionext(){
        EditText ev=new EditText(SellDetialActivity.this);
        new AlertDialog.Builder(SellDetialActivity.this)
                .setTitle("请输密码")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(ev)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auctionexto(ev.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    public void auctionexto(String password){
                url="paiservice/DoPaiService.php";
                params=new RequestParams();
                Get get=new Get("User",getApplicationContext());
                Get get1=new Get("UserKey",getApplicationContext());
                params.add("UserName",get.getout("username",""));
                params.add("SecretKey",get1.getout("secretkey",""));
                params.add("Money",myMoney);
                params.add("PaiId",SellId);
                params.add("Comment","非常好");
                params.add("PayPassword",password);
                HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        responStr=new String(bytes);
                try{
                    SellModel sell=new Gson().fromJson(responStr,SellModel.class);
                    if(sell.getState()==1){
                        GetInfor();
                        Toast.makeText(SellDetialActivity.this, "竞拍成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SellDetialActivity.this,sell.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(SellDetialActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(SellDetialActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SellDetialActivity.this, "还没有被拍下哦！", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sellit(){
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();

        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;

        button.setWidth(width/3);
        button.setText("继续竞价");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ev=new EditText(SellDetialActivity.this);
                new AlertDialog.Builder(SellDetialActivity.this)
                        .setTitle("请输入竞拍价格")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(ev)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String s=ev.getText().toString();
                                Integer now=new Integer(Money);
                                Integer jing=new Integer(s);
                                if(now>=jing){
                                    new AlertDialog.Builder(SellDetialActivity.this)
                                            .setMessage("输入竞拍不能小于当前竞拍！！！")
                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .show();
                                }else{
                                    myMoney=s;
                                    auction(ev.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }
}
