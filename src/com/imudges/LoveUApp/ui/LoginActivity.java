package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.LoginModel;
import com.imudges.LoveUApp.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilePermission;

/**
 * Created by dy on 2016/3/9.
 */
public class LoginActivity extends Activity {


    private UserService userService=new UserService();
    private LoginModel loginModel=new LoginModel();
    private EditText ed1,ed2;
    private String username,password;
    private String secretKey;
    private TextView tv1;
    //private Button button1,button2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login_layout);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.login_title);

        findobject();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
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
                secretKey =  loginModel.getSecretKey();
                System.out.println("第一次得到"+secretKey);
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

    }
    public void loginclick(View v){
        username = ed1.getText().toString();
        password = ed2.getText().toString();
        login(username,password);

       // Toast.makeText(getApplicationContext(),username+password,Toast.LENGTH_SHORT).show();
    }


    private void saveData(Context context){
        SharedPreferences sp = context.getSharedPreferences("loginconfig", MODE_PRIVATE);
        //建立编辑器  然后开始写入 文件
        SharedPreferences.Editor editor = sp.edit();
        secretKey =  loginModel.getSecretKey();
        editor.putString("username", username);
        editor.putString("secretkey",secretKey);
        editor.putString("username",username);
        System.out.println(username+secretKey);
        editor.commit();
        editor.commit();   ;
    }
}
