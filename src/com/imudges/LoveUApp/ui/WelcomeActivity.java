package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.AdService;
import com.imudges.LoveUApp.service.UserService;

import java.io.InputStream;
import java.net.URL;

public class WelcomeActivity extends Activity {
    private ImageView welcomeImg = null;
    private UserService userService = new UserService();
    private String username,secretKey;
    private String Url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.welcome_layout);

        welcomeImg = (ImageView) this.findViewById(R.id.welcome_img);
        //loadData(getApplicationContext());
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());

    }
    Bitmap bitmap;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9528) {
                //显示从网上下载的图片
                welcomeImg.setImageBitmap(bitmap);
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
                    //  imageview.setImageBitmap(bitmap);
                    //发送消息，通知UI组件显示图片
                    handler.sendEmptyMessage(0x9528);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class AnimationImpl implements Animation.AnimationListener {

        public void onAnimationStart(Animation animation) {
            Get get=new Get("AdS",getApplicationContext());
            Url=get.getout("AdS","http://imgsrc.baidu.com/forum/w%3D580/sign=b520cc5deb50352ab16125006341fb1a/2ca959086e061d9585a8f4d279f40ad160d9ca93.jpg");
            //Toast.makeText(getApplicationContext(),Url,Toast.LENGTH_LONG).show();
            downPhoto(Url);
            AdService adService=new AdService();
            adService.GetAdurl(getApplicationContext(), new Listener() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onFailure(String msg) {
                }
            });
        }

        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        public void onAnimationRepeat(Animation animation) {

        }

    }

    private void skip() {
        //判断跳转主界面或者登陆界面
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    private void loadData(Context context) {
        Get get=new Get("User",getApplicationContext());
        String a=get.getout("username","");
        String b=get.getout("password","");
        Get get1=new Get("UserKey",getApplicationContext());
        String c=get1.getout("secretkey","");
        //Toast.makeText(WelcomeActivity.this,a+b+c , Toast.LENGTH_SHORT).show();
    }

    public void relogin(String username,String secretKey){
        userService.SureName(getApplicationContext(), new Listener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            }

            @Override
            public void onFailure(String msg) {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();;
            }
        }, secretKey, username);
    }

}
