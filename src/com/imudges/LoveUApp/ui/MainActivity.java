package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.imudges.LoveUApp.ui.ArcMenu.MyThread;
import com.imudges.LoveUApp.ui.MealFragment.MealMenuFragment;
import com.slidingmenu.lib.SlidingMenu;


/**
 * Created by dy on 2016/3/9.
 */
public class MainActivity extends FragmentActivity{

    private SlidingMenu menu;
    public static boolean key=false;
    private Button btn_set,btn_sliding;

    private ViewFlipper viewFlipper;

    Animation leftInAnimation;
    Animation leftOutAnimation;
    Animation rightInAnimation;
    Animation rightOutAnimation;
    DisplayMetrics dm = new DisplayMetrics();
    ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_another);
        SysApplication.getInstance().addActivity(this);
        init();

        //初始化滑动菜单
        initSlidingMenu();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);

        //往viewFlipper添加View
        viewFlipper.addView(getImageView(R.drawable.sell2));
        viewFlipper.addView(getImageView(R.drawable.food));
        viewFlipper.addView(getImageView(R.drawable.run2));
        viewFlipper.addView(getImageView(R.drawable.send));
        viewFlipper.addView(getImageView(R.drawable.help));
        viewFlipper.addView(getImageView(R.drawable.school));

        //动画效果
        leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);

        nextView();
        Myclick();
    }

    public void Myclick(){
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainYueActivity.class));
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainMealActivity.class));
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainSyllabusActivity.class));
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,MainSellActivity.class));
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,MainPresentActivity.class));
            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainCooperationActivity.class));
            }
        });
    }

    private ImageView getImageView(int id){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                viewFlipper.setInAnimation(leftInAnimation);
                viewFlipper.setOutAnimation(leftOutAnimation);
                viewFlipper.showNext();//向右滑动
            }
        }
    };
    public void nextView(){
        new Thread(){
            @Override
            public void run() {
                try {
                    while(true){
                        Thread.sleep(2000);
                        handler.sendEmptyMessage(0x9527);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void init(){
        viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
        iv1=(ImageView)findViewById(R.id.main_yue);
        iv2=(ImageView)findViewById(R.id.main_food);
        iv3=(ImageView)findViewById(R.id.main_ke);
        iv4=(ImageView)findViewById(R.id.main_pai);
        iv5=(ImageView)findViewById(R.id.main_present);
        iv6=(ImageView)findViewById(R.id.main_xuan);

        btn_set = (Button) findViewById(R.id.main_top_set);
        btn_sliding = (Button) findViewById(R.id.main_top_sliding_button);

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainSetActivity.class));
            }
        });
        btn_sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.showMenu();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(new Intent(MainActivity.this,MainSetActivity.class));
            //super.openOptionsMenu();  // 调用这个，就可以弹出菜单
            return super.onKeyDown(keyCode, event);
        }
        MyThread my=new MyThread();
        my.start();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&& key==true) {
            finish();
            return true;
        }
        if(key==false){
            Toast.makeText(MainActivity.this, "(>ˍ<) ～您真的想要离开吗?", Toast.LENGTH_LONG).show();
            key=true;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化滑动菜单
     */
    private void initSlidingMenu() {
        // 设置滑动菜单的属性值
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.toggle();
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        // 设置滑动菜单的视图界面
        menu.setMenu(R.layout.menu_fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MainMenuFragment()).commit();
    }

}
