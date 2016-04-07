package com.imudges.LoveUApp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.ui.ArcMenu.MyThread;
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

        init();

        //初始化滑动菜单
        initSlidingMenu();
    }

    private void initMain() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
        SysApplication.getInstance().addActivity(this);
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
    public void init(){
        Get get=new Get("User",getApplicationContext());
        WebView webView = (WebView) findViewById(R.id.syllabus_Webview);
        //WebView加载web资源
        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
        //webView.setScrollBarStyle(0);
        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setDomStorageEnabled(true);
        webView.requestFocus();
        webView.loadUrl("http://loveu.iheshulin.com:9999/LOVEU/classservice/classview.php?UserName="+get.getout("username",""));
        //webView.loadUrl("http://baidu.com");
        //webView.setScrollBarStyle(0);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
    public static boolean key=false;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(new Intent(MainSyllabusActivity.this,MainSetActivity.class));
            //super.openOptionsMenu();  // 调用这个，就可以弹出菜单
            return super.onKeyDown(keyCode, event);
        }
        MyThread my=new MyThread();
        my.start();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&& key==true) {
            SysApplication.getInstance().exit();
            finish();
            return true;
        }
        if(key==false){
            Toast.makeText(MainSyllabusActivity.this, "(>ˍ<) ～您真的想要离开吗?", Toast.LENGTH_LONG).show();
            key=true;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
