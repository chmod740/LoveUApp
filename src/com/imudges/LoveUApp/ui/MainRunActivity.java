package com.imudges.LoveUApp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.RunFragment.RunMainFragment;
import com.imudges.LoveUApp.ui.RunFragment.RunMenuFragment;
import com.slidingmenu.lib.SlidingMenu;

/**
 * Created by dy on 2016/3/9.
 */
public class MainRunActivity extends FragmentActivity implements View.OnClickListener{
    private SlidingMenu menu;
    private Fragment[] mFragments;
    private RadioGroup bottomRg;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbOne, rbTwo, rbThree, rbFour;

    private TextView tv_text1, tv_text2, tv_text3, tv_text4, tv_text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置标题
        setTitle("Attach");

        //初始化主界面
        initMain();

        //初始化滑动菜单
        initSlidingMenu();

        initView();

    }

    private void initMain() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.run_main);
        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.run_fragment1);
        mFragments[1] = fragmentManager.findFragmentById(R.id.run_fragment2);
        mFragments[2] = fragmentManager
                .findFragmentById(R.id.run_fragment3);
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();
        setFragmentIndicator();
    }

    private void setFragmentIndicator() {

        bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        rbThree = (RadioButton) findViewById(R.id.rbThree);

        bottomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1])
                        .hide(mFragments[2]);
                switch (checkedId) {
                    case R.id.rbOne:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;

                    case R.id.rbTwo:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;

                    case R.id.rbThree:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        tv_text1 = (TextView) findViewById(R.id.menu_text1);
        tv_text2 = (TextView) findViewById(R.id.menu_text2);
        tv_text3 = (TextView) findViewById(R.id.menu_text3);
        tv_text4 = (TextView) findViewById(R.id.menu_text4);
        tv_text5 = (TextView) findViewById(R.id.menu_text5);

        tv_text1.setOnClickListener(this);
        tv_text2.setOnClickListener(this);
        tv_text3.setOnClickListener(this);
        tv_text4.setOnClickListener(this);
        tv_text5.setOnClickListener(this);
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
        menu.setMenu(R.layout.slidingmenu_fragment);
//        getSupportFragmentManager().beginTransaction().replace(R.id.menu_linearlayout, new RunMenuFragment()).commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.menu_text1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_text2:
                intent = new Intent(this,MainMealActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_text3:
                intent = new Intent(this,MainStudyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_text4:
                intent = new Intent(this,MainSellActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_text5:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
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
