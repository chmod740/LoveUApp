package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.os.Bundle;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.UserService;

/**
 * Created by dy on 2016/3/9.
 */
public class RegisterActivity extends Activity {
    private UserService userService=new UserService();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
    }

    /**
     * 注册函数
     * @param name
     * @param password
     * @param phone
     * @param truename
     * @param sex
     * @param usergoade
     * @param usermagor
     */
    public void register(String name,String password,String phone ,String truename,Integer sex,
                         Integer usergoade,String usermagor){
        userService.register(getApplicationContext(), name, password, truename, sex, usergoade,
                usermagor, phone, new Listener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

}
