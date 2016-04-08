package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.ArcMenu.MyThread;
import com.imudges.LoveUApp.ui.SellFragment.SellMenuFragment;
import com.imudges.LoveUApp.ui.YueFragment.YueMenuFragment;
import com.slidingmenu.lib.SlidingMenu;

/**
 * Created by dy on 2016/3/9.
 */
public class MainSellActivity extends FragmentActivity{
    private static SlidingMenu menu;
    private Fragment[] mFragments;
    private RadioGroup bottomRg,topRg;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置标题
        //setTitle("Sell");

        //初始化主界面
        initMain();

        //初始化滑动菜单
        initSlidingMenu();

    }

    private void initMain() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sell_main);
        SysApplication.getInstance().addActivity(this);
        mFragments = new Fragment[6];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.sell_fragment1);
        mFragments[1] = fragmentManager.findFragmentById(R.id.sell_fragment2);
        mFragments[2] = fragmentManager.findFragmentById(R.id.sell_fragment3_1);
        mFragments[3] = fragmentManager.findFragmentById(R.id.sell_fragment3_2);

        mFragments[4] = fragmentManager.findFragmentById(R.id.sell_top_fragment1);
        mFragments[5] = fragmentManager.findFragmentById(R.id.sell_top_fragment3);

        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5]);
        fragmentTransaction.show(mFragments[0]).show(mFragments[4]).commit();
        setFragmentIndicator();
    }

    private void setFragmentIndicator() {

        bottomRg = (RadioGroup) findViewById(R.id.sell_bottomRg);
        topRg = (RadioGroup) findViewById(R.id.sell_topRg3);

        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                        .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5]);
                switch (checkedId) {
                    case R.id.sell_rbOne:
                        fragmentTransaction.show(mFragments[0]).show(mFragments[4]).commit();
                        break;

                    case R.id.sell_rbTwo:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;

                    case R.id.sell_rbThree:
                        fragmentTransaction.show(mFragments[2]).show(mFragments[5]).commit();
                        topRg.check(R.id.sell_top_31);
                        break;

                    default:
                        break;
                }
            }
        });

        topRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
                        .hide(mFragments[3]).hide(mFragments[4]).hide(mFragments[5]);
                switch (checkedId) {
                    case R.id.sell_top_31:
                        fragmentTransaction.show(mFragments[2]).show(mFragments[5]).commit();
                        break;

                    case R.id.sell_top_32:
                        fragmentTransaction.show(mFragments[3]).show(mFragments[5]).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SellMenuFragment()).commit();
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
    public static SlidingMenu getMenu() {
        return menu;
    }
}
