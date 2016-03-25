package com.imudges.LoveUApp.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.YueFragment.YueMenuFragment;
import com.slidingmenu.lib.SlidingMenu;

/**
 * Created by dy on 2016/3/9.
 */
public class MainYueActivity extends FragmentActivity {
    private SlidingMenu menu;
    private Fragment[] mFragments;
    private RadioGroup bottomRg;
    private RadioGroup topRg1,topRg2,topRg3;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbOne, rbTwo, rbThree;
    private static String userName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置标题
        setTitle("Yue");

        //初始化主界面
        initMain();

        //初始化滑动菜单
        initSlidingMenu();

//        initView();

    }

    private void initMain() {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.run_main);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mFragments = new Fragment[10];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.run_fragment1);
        mFragments[1] = fragmentManager.findFragmentById(R.id.run_fragment1_2);
        mFragments[2] = fragmentManager.findFragmentById(R.id.run_fragment2);
        mFragments[3] = fragmentManager.findFragmentById(R.id.run_fragment2_2);
        mFragments[4] = fragmentManager.findFragmentById(R.id.run_fragment3);
        mFragments[5] = fragmentManager.findFragmentById(R.id.run_fragment3_2);
        //top fragment
        mFragments[6] = fragmentManager.findFragmentById(R.id.top_fragment1);
        mFragments[7] = fragmentManager.findFragmentById(R.id.top_fragment2);
        mFragments[8] = fragmentManager.findFragmentById(R.id.top_fragment3);
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5])
                .hide(mFragments[6]).hide(mFragments[7]).hide(mFragments[8]);
        //初始显示的fragment
        fragmentTransaction.show(mFragments[0]).show(mFragments[6]).commit();
        setFragmentIndicator();
    }

    private void setFragmentIndicator() {

        bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        rbThree = (RadioButton) findViewById(R.id.rbThree);
        rbOne.setText("社交");
        rbTwo.setText("发布");
        rbThree.setText("记录");

        topRg1 = (RadioGroup) findViewById(R.id.topRg1);
        topRg2 = (RadioGroup) findViewById(R.id.topRg2);
        topRg3 = (RadioGroup) findViewById(R.id.topRg3);

        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                        .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5])
                        .hide(mFragments[6]).hide(mFragments[7]).hide(mFragments[8]);
                switch (checkedId) {
                    case R.id.rbOne:
                        fragmentTransaction.show(mFragments[0]).show(mFragments[6]).commit();
                        topRg1.check(R.id.run_top_11);
                        break;

                    case R.id.rbTwo:
                        fragmentTransaction.show(mFragments[2]).show(mFragments[7]).commit();
                        topRg2.check(R.id.run_top_21);
                        break;

                    case R.id.rbThree:
                        fragmentTransaction.show(mFragments[4]).show(mFragments[8]).commit();
                        topRg3.check(R.id.run_top_31);
                        break;

                    default:
                        break;
                }
            }
        });

        topRg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                        .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5])
                        .hide(mFragments[6]).hide(mFragments[7]).hide(mFragments[8]);
                switch (checkedId) {
                    case R.id.run_top_11:
                        fragmentTransaction.show(mFragments[0]).show(mFragments[6]).commit();

                        break;

                    case R.id.run_top_12:
                        fragmentTransaction.show(mFragments[1]).show(mFragments[6]).commit();
                        break;

                    default:
                        break;
                }
            }
        });

        topRg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                        .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5])
                        .hide(mFragments[6]).hide(mFragments[7]).hide(mFragments[8]);
                switch (checkedId) {
                    case R.id.run_top_21:
                        fragmentTransaction.show(mFragments[2]).show(mFragments[7]).commit();
                        break;

                    case R.id.run_top_22:
                        fragmentTransaction.show(mFragments[3]).show(mFragments[7]).commit();
                        break;

                    default:
                        break;
                }
            }
        });

        topRg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                        .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5])
                        .hide(mFragments[6]).hide(mFragments[7]).hide(mFragments[8]);
                switch (checkedId) {
                    case R.id.run_top_31:
                        fragmentTransaction.show(mFragments[4]).show(mFragments[8]).commit();
                        break;

                    case R.id.run_top_32:
                        fragmentTransaction.show(mFragments[5]).show(mFragments[8]).commit();
                        break;

                    default:
                        break;
                }
            }
        });
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

        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new YueMenuFragment()).commit();
    }



    @Override
    public void onBackPressed() {
        //点击返回键关闭滑动菜单
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
    }

    public static void setUserName(String userName1){
        userName = userName1;
    }

    public static String getUserName(){
        return userName;
    }
}
