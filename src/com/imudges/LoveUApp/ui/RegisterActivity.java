package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.UserService;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by dy on 2016/3/9.
 */
public class RegisterActivity extends Activity {
    private UserService userService=new UserService();

    private static final String[] sex={"男","女"};
    private static final String[] grade={"大一","大二","大三","大四"};
    private TextView showsex ;
    private Spinner setsex;
    private ArrayAdapter<String> sexadapter;
    private TextView showgrade ;
    private Spinner setgrade;
    private ArrayAdapter<String> gradeadapter;
    private EditText ed_username;
    private EditText ed_password1;
    private EditText ed_password2;
    private EditText ed_truename;
    private TextView tx_number;
    private EditText ed_major;
    private String username,password1,password2,truename,number,major;
    private int sexvalue,gradevalue;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register_layout);
        SysApplication.getInstance().addActivity(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.login_title);

        Bundle bundle = this.getIntent().getExtras();;
        number = bundle.getString("number");
        findobject();
    }

    /**
     * 注册函数
     * @param name
     * @param password
     * @param phone

     * @param sex
     */
    public void register(String name,String password,String phone ,Integer sex){
        userService.register(getApplicationContext(), name, password, sex,phone, new Listener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                SysApplication.getInstance().exit();
            }

            @Override
            public void onFailure(String msg) {

                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findobject(){


        tx_number = (TextView) findViewById(R.id.register_haoma);
        ed_password1 = (EditText)findViewById(R.id.register_mima1);
        ed_password2 = (EditText)findViewById(R.id.register_mima2);
        ed_username = (EditText)findViewById(R.id.register_zhanghao);

        tx_number.setText(number);
        showsex = (TextView) findViewById(R.id.register_sex);
        //showgrade = (TextView) findViewById(R.id.register_grade);



        setsex = (Spinner) findViewById(R.id.register_Spinner1);
        //setgrade = (Spinner) findViewById(R.id.register_Spinner2);
        //将可选内容与ArrayAdapter连接起来
        sexadapter = new ArrayAdapter<String>(this,R.layout.spinnertext,sex);
        //设置下拉列表的风格
        sexadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        setsex.setAdapter(sexadapter);

        //添加事件Spinner事件监听
        setsex.setOnItemSelectedListener(new SpinnerSelectedListener1());

        //设置默认值
        setsex.setVisibility(showsex.VISIBLE);

        /**************************************************/

    }

    class SpinnerSelectedListener1 implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            if(arg2==0)
            {
                sexvalue=1;
            }
            else
                sexvalue=0;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

   /* class SpinnerSelectedListener2 implements OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            gradevalue=arg2+1;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }*/
    public void click(View v){
       // Toast.makeText(getApplicationContext(),"监听了",Toast.LENGTH_SHORT).show();
        username = ed_username.getText().toString();
        password1 = ed_password1.getText().toString();
        password2 = ed_password2.getText().toString();


        if(password1.equals(password2)!=true)
            return ;
        register(username,password1,number,sexvalue);
    }
}
