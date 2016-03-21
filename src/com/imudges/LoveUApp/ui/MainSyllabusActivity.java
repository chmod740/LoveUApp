package com.imudges.LoveUApp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.imudges.LoveUApp.ui.SyllabusFragment.SyllabusMenuFragment;
import com.slidingmenu.lib.SlidingMenu;

/**
 * Created by dy on 2016/3/9.
 */
public class MainSyllabusActivity extends FragmentActivity{

    private SlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置标题
        setTitle("Syllabus");

        //初始化主界面
        initMain();

        //初始化滑动菜单
        initSlidingMenu();
    }

    private void initMain() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.syllabus_main);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new SyllabusMenuFragment()).commit();
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
}
