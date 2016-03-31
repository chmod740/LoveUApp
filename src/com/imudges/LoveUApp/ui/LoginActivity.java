package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.DAO.SavePhoto;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.UserService;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by dy on 2016/3/9.
 */
public class LoginActivity extends Activity {

    private ImageView UserImage;
    private UserService userService=new UserService();
    private PhotoCut photoCut=new PhotoCut(LoginActivity.this);
    private EditText ed1,ed2;
    private String username,password;
    private String secretKey;
    private TextView tv1;
    private TextView tv2;

    private String PhotoUrl;
    private Bitmap mybitmap;

    //private Button button1,button2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login_layout);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.login_title);

        //显示图片
        findobject();
        setImage();
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity1.class));
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //切换到忘记密码页面！

            }
        });
        trends();
    }

    /**
     * 登录函数
     * @param name
     * @param password
     */
    public void login(String name,String password){
        userService.login(name, password, getApplicationContext(), new Listener() {
            @Override
            public void onSuccess() {
                saveData(getApplicationContext());
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void findobject(){
        ed1=(EditText) findViewById(R.id.login_zhuce);
        ed2=(EditText) findViewById(R.id.login_mima);
        tv1=(TextView) findViewById(R.id.login_register);
        tv2=(TextView) findViewById(R.id.login_losspass);
        UserImage=(ImageView) findViewById(R.id.userimage);
    }
    public void loginclick(View v){
        username = ed1.getText().toString();
        password = ed2.getText().toString();
        login(username,password);
        getUser();
        // Toast.makeText(getApplicationContext(),username+password,Toast.LENGTH_SHORT).show();
    }


    private void saveData(Context context){
        Save save=new Save("User",getApplicationContext());
        save.savein("username",username);
        save.savein("password",password);
    }
    public void setImage(){
        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        if(bitmap!=null){
            UserImage.setImageBitmap(bitmap);
        }
    }

    /**
     * 动态获取用户图片
     */
    public void test(){
        UserService user=new UserService();
        char []a=username.toCharArray();
        int k=1;
        for (char s:a) {
            if(s>='0'&&s<='9'){
                k++;
            }
        }
        if(k==12){
            user.getNickP(getApplicationContext(),username, new Listener() {
                @Override
                public void onSuccess() {
                    Get get1=new Get("Nick",getApplicationContext());
                    PhotoUrl=get1.getout("Photo","");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                //创建一个url对象
                                URL url = new URL(PhotoUrl);
                                //打开URL对应的资源输入流
                                InputStream is = url.openStream();
                                //从InputStream流中解析出图片
                                mybitmap = BitmapFactory.decodeStream(is);
                                PhotoCut p=new PhotoCut(getApplicationContext());
                                mybitmap=p.toRoundBitmap(mybitmap);
                                //  imageview.setImageBitmap(bitmap);
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
                @Override
                public void onFailure(String msg) {}
            });
        }else{
            user.getNickU(getApplicationContext(), username, new Listener() {
                @Override
                public void onSuccess() {
                    Get get1=new Get("Nick",getApplicationContext());
                    PhotoUrl=get1.getout("Photo","");
                    //Toast.makeText(LoginActivity.this, PhotoUrl, Toast.LENGTH_SHORT).show();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                //创建一个url对象
                                URL url = new URL(PhotoUrl);
                                //打开URL对应的资源输入流
                                InputStream is = url.openStream();
                                //从InputStream流中解析出图片
                                mybitmap = BitmapFactory.decodeStream(is);
                                PhotoCut p=new PhotoCut(getApplicationContext());
                                mybitmap=p.toRoundBitmap(mybitmap);
                                //  imageview.setImageBitmap(bitmap);
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
                @Override
                public void onFailure(String msg) {}
            });
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                //显示从网上下载的图片
                //UserImage.setImageBitmap(bitmap);
                SavePhoto savePhoto=new SavePhoto(mybitmap,Environment.getExternalStorageDirectory().getPath(),"UserAd");
                savePhoto.Savephoto();
                UserImage.setImageBitmap(mybitmap);
            }
        }
    };
    public void trends(){
        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username=ed1.getText().toString();
                test();
                //Toast.makeText(LoginActivity.this, username, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                username=ed1.getText().toString();
                test();
                //Toast.makeText(LoginActivity.this, username, Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 获取昵称
     */

    public void getUser(){
        int k=1;
        char a[]=username.toCharArray();
        for (char s:a) {
            if(s>='0'&&s<='9'){
                k++;
            }
        }
        if(k==12){
            userService.getNickP(getApplicationContext(), username, new Listener() {
                @Override
                public void onSuccess() {
                    //Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                }
            });
        }else{
            userService.getNickU(getApplicationContext(), username, new Listener() {
                @Override
                public void onSuccess() {
                    //Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
