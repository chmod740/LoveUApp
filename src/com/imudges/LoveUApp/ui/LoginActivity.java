package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.UserService;

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
    //private Button button1,button2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


}
