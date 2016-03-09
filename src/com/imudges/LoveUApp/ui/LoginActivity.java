package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.os.Bundle;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.UserService;

/**
 * Created by dy on 2016/3/9.
 */
public class LoginActivity extends Activity {

    private UserService userService=new UserService();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
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

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}