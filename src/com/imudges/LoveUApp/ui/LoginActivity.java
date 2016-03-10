package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    //private Button button1,button2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        findobject();
    }

    /**
     * 登录函数
     * @param name
     * @param password
     * 获取SecretKey函数
     *  public String getSecretKey () throws IOException {
            File file=new File(getFilesDir(),"info_book.txt");
            FileInputStream fos = new FileInputStream(file);
            byte[] bytes = new byte[fos.available()];
            fos.read(bytes);
            String SecretKey=new String(bytes);
            return SecretKey;
        }
     */
    public void login(String name,String password){
        userService.login(name, password, getApplicationContext(), new Listener() {
            @Override
            public void onSuccess() {
                SaveSecretKey();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 本地保存SecretKey
     */

    public void SaveSecretKey(){
        File file = new File(getFilesDir(),"info_Secretkey.txt");
        try {
            FileOutputStream fos =new FileOutputStream(file);
            fos.write(loginModel.getSecretKey().getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    private void findobject(){
        ed1=(EditText) findViewById(R.id.login_zhuce);
        ed2=(EditText) findViewById(R.id.login_mima);

    }
    private void registeredclick(View v){
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
    public void loginclick(View v){
        username = ed1.getText().toString();
        password = ed2.getText().toString();
        login(username,password);

        Toast.makeText(getApplicationContext(),username+password,Toast.LENGTH_SHORT).show();
    }
}
