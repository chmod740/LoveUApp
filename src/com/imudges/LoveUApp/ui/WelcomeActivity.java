package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.UserService;

public class WelcomeActivity extends Activity {
    private ImageView welcomeImg = null;
    private String username,secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        welcomeImg = (ImageView) this.findViewById(R.id.welcome_img);
        loadData(getApplicationContext());
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());

    }

    private class AnimationImpl implements Animation.AnimationListener {

        public void onAnimationStart(Animation animation) {
            welcomeImg.setBackgroundResource(R.drawable.ic_launcher);
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
        //第一个参数是 文件名字  第二个参数 是访问权限  一般是MODE_PRIVATE

        SharedPreferences sp = context.getSharedPreferences("loginconfig", MODE_PRIVATE);
        username = sp.getString("username","").toString();
        secretKey = sp.getString("secretkey","错误数据").toString();

        Toast.makeText(this, username+secretKey+sp.getString("password","密码空"), Toast.LENGTH_SHORT).show();
    }
}
