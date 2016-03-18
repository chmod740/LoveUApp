package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.VCodeService;


public class RegisterActivity1 extends Activity {
    private EditText ednumber;
    private String number;
    private VCodeService vCodeService = new VCodeService();
   // private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1_layout);
        SysApplication.getInstance().addActivity(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ednumber = (EditText) findViewById(R.id.register1_number);
    }

    public void numberclick(View v){

        number = ednumber.getText().toString();
        if(number.length()!=11)
        {
            Toast.makeText(getApplicationContext(),"手机号码有误",Toast.LENGTH_SHORT).show();
            return ;
        }

        applyvcode(number);

    }

    public void applyvcode(String number){
        vCodeService.applyVcode(number, getApplicationContext(), new Listener() {
            @Override
            public void onSuccess() {
                Intent intent =new Intent(RegisterActivity1.this,RegisterActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putString("number",number);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
