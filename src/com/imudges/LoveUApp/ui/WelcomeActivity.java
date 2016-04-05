package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.imudges.LoveUApp.service.UpDataService;
import com.imudges.LoveUApp.service.UserService;

import java.io.InputStream;
import java.net.URL;

public class WelcomeActivity extends Activity {
    private ImageView welcomeImg = null;
    private UserService userService = new UserService();
    private String Url="";
    private UpDataService service;
    private static boolean key=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.welcome_layout);

        AppUpdata();

        welcomeImg = (ImageView) this.findViewById(R.id.welcome_img);
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
                Drawable drawable =new BitmapDrawable(getResources(),bitmap);
                welcomeImg.setBackground(drawable);
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
            if(true){
                //!checkNetworkAvailable(WelcomeActivity.this)
                welcomeImg.setBackgroundResource(R.drawable.we);
            }else{
                Get get=new Get("AdS",getApplicationContext());
                Url=get.getout("AdS","http://7xrqhs.com1.z0.glb.clouddn.com/welcome.jpg");
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
        }

        public void onAnimationEnd(Animation animation) {
            if(key==false){
                loadData();
            }
            // 动画结束后跳转到别的页面
        }
        public void onAnimationRepeat(Animation animation) {
        }
    }
    private void loadData() {
        Get get=new Get("User",getApplicationContext());
        String name=get.getout("username","");
        String passsword=get.getout("password","");
        relogin(name,passsword);
    }
    public void relogin(String username,String pass){
        userService.login(username, pass, getApplicationContext(), new Listener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            @Override
            public void onFailure(String msg) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * 判断网络状态
     * @param context
     * @return
     */
    public static boolean checkNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void AppUpdata() {
        service=new UpDataService();
        //Toast.makeText(WelcomeActivity.this, getVersion(), Toast.LENGTH_SHORT).show();
        service.updata(getApplicationContext(), getVersion(), new Listener() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onFailure(String msg) {
                updata(msg);
            }
        });
    }
    public void updata(String url){
        key=true;
        new AlertDialog.Builder(WelcomeActivity.this)
                .setTitle("是否要更新？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(url);
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        it.setData(uri);
                        it.setAction( Intent.ACTION_VIEW);
                        WelcomeActivity.this.startActivity(it);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        key=false;
                        loadData();
                    }
                })
                .show();
    }

}
