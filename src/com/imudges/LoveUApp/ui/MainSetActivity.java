package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.ChongzhiService;
import com.imudges.LoveUApp.ui.Set.AboutActivity;
import com.imudges.LoveUApp.ui.Set.UserChange;
import com.imudges.LoveUApp.ui.Set.UserSet;

/**
 * Created by dy on 2016/3/18.
 * 设置界面
 */
public class MainSetActivity extends Activity{

    private TextView set_phone;
    private ImageView set_image,setback;
    private TextView set_out;
    private LinearLayout set_user,set_user_P,set_about,set_help,setself,chong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_ting);
        init();
        setUserImage();
        Myclick();
    }

    public void init(){
        set_about=(LinearLayout)findViewById(R.id.set_user_about);
        set_image=(ImageView)findViewById(R.id.set_imageView);
        set_help=(LinearLayout)findViewById(R.id.set_user_Help);
        set_phone=(TextView)findViewById(R.id.setPhone);
        set_out=(TextView)findViewById(R.id.set_user_Out);
        setback=(ImageView)findViewById(R.id.setback);
        set_user=(LinearLayout) findViewById(R.id.set_user_User);
        set_user_P=(LinearLayout) findViewById(R.id.set_user_Phone);
        setself=(LinearLayout) findViewById(R.id.set_user_self);

        chong=(LinearLayout)findViewById(R.id.set_user_chongzhi);
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
                Save save=new Save("User",getApplicationContext());
                save.savein("username","");
                save.savein("password","");
                save.savein("truename","");
                save.savein("major","");
                save.savein("grade","");
                save.savein("sex","");
                SysApplication.getInstance().exit();
                finish();
            }
        });
        set_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainSetActivity.this, AboutActivity.class));
            }
        });
        set_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(MainSetActivity.this, "帮助", Toast.LENGTH_SHORT);
            }
        });
        set_user_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(MainSetActivity.this, "暂不支持更改电话！", Toast.LENGTH_SHORT);
            }
        });
        setself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainSetActivity.this,UserChange.class));
                //Toast.makeText(MainSetActivity.this, "个人信息", Toast.LENGTH_SHORT).show();
            }
        });
        chong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chong();
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

    public void Chong(){
        new AlertDialog.Builder(MainSetActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setMessage("钱包管理")
                .setPositiveButton("设置钱包密码", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chongpass();
                    }
                })
                .setNegativeButton("钱包", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chongzhi();
                    }
                })
                .show();
    }
    public void chongzhi() {
        ChongzhiService service = new ChongzhiService();
        EditText editText = new EditText(MainSetActivity.this);
        new AlertDialog.Builder(MainSetActivity.this)
                .setTitle("请输入金额")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Get get = new Get("User", getApplicationContext());
                        Get get1 = new Get("UserKey", getApplicationContext());
                        String ss=editText.getText().toString();
                        char []a=ss.toCharArray();
                        for (char x:a) {
                            if(x>'9'||x<'0'){
                                showToast(MainSetActivity.this, "请输入正格式", Toast.LENGTH_SHORT);
                                return;
                            }
                        }
                        Integer s=new Integer(editText.getText().toString());
                        if(s<=0){
                            showToast(MainSetActivity.this, "请输入正格式", Toast.LENGTH_SHORT);
                            return;
                        }
                        service.chong(getApplicationContext(), get.getout("username", ""), get1.getout("secretkey", ""), editText.getText().toString(), new Listener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(MainSetActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(MainSetActivity.this, msg, Toast.LENGTH_SHORT).show();
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    public void chongpass(){
        ChongzhiService service = new ChongzhiService();
        EditText editText = new EditText(MainSetActivity.this);
        new AlertDialog.Builder(MainSetActivity.this)
                .setTitle("请输入钱包密码")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Get get = new Get("User", getApplicationContext());
                        Get get1 = new Get("UserKey", getApplicationContext());
                        service.chongpass(getApplicationContext(), get.getout("username", ""), get1.getout("secretkey", ""), editText.getText().toString(), new Listener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(MainSetActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(MainSetActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    private static Toast mToast = null;
    static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                mToast=null;
            }
        }
    };
    public static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.cancel();
            new Thread(){
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);
                    }catch (Exception e){
                        e.getLocalizedMessage();
                    }
                    handler.sendEmptyMessage(0x9527);
                }
            }.start();
        }
        mToast.show();
    }

}

