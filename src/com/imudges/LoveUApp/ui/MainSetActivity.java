package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.ui.Set.UserSet;

/**
 * Created by dy on 2016/3/18.
 * 设置界面
 */
public class MainSetActivity extends Activity{

    private TextView set_phone,setback,setself;
    private ImageView set_image;
    private TextView set_help,set_about,set_out;
    private LinearLayout set_user,set_user_P;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.setting);
        init();
        setUserImage();
        Myclick();
    }

    public void init(){
        set_about=(TextView)findViewById(R.id.set_user_about);
        set_image=(ImageView)findViewById(R.id.set_imageView);
        set_help=(TextView)findViewById(R.id.set_user_Help);
        set_phone=(TextView)findViewById(R.id.setPhone);
        set_out=(TextView)findViewById(R.id.set_user_Out);
        setback=(TextView)findViewById(R.id.setback);
        set_user=(LinearLayout) findViewById(R.id.set_user_User);
        set_user_P=(LinearLayout) findViewById(R.id.set_user_Phone);
        setself=(TextView) findViewById(R.id.set_user_self);

    }
    public void Myclick(){
        //用户管理
        set_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserSet.class));
            }
        });
        //返回
        setback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(),MainYueActivity.class));
                finish();
            }
        });
        //结束
        set_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        set_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSetActivity.this, "关于", Toast.LENGTH_SHORT).show();
            }
        });
        set_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSetActivity.this, "帮助", Toast.LENGTH_SHORT).show();
            }
        });
        set_user_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSetActivity.this, "电话", Toast.LENGTH_SHORT).show();
            }
        });
        setself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainSetActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setUserImage(){
        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        set_image.setImageBitmap(bitmap);

        Get get=new Get("Nick",getApplicationContext());
        String phone=get.getout("user_Phone","");
        char a[]=phone.toCharArray();
        phone="";
        int k=0;
        for (char s:a) {
            k++;
            if(k>3&&k<8){
                s='*';
            }
            phone+=s;
        }
        set_phone.setText(phone);
    }

}

