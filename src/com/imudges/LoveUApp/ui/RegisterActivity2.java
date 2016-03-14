package com.imudges.LoveUApp.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.VCodeService;

public class RegisterActivity2  extends Activity{
    private String number;
    private EditText ederification;
    private String verification;
    private VCodeService vCodeService = new VCodeService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register2_layout);
        Bundle bundle = this.getIntent().getExtras();;
        number = bundle.getString("number");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ederification = (EditText) findViewById(R.id.register2_verification);
        Toast.makeText(getApplicationContext(),number,Toast.LENGTH_SHORT).show();
    }


    public void setvcode(String number,String verification){
        vCodeService.vCode(number, verification, getApplicationContext(), new Listener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(RegisterActivity2.this,RegisterActivity.class));
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void verificationclick(View v){
        verification = ederification.getText().toString();
        setvcode(number,verification);
        Toast.makeText(getApplicationContext(),number+" "+verification,Toast.LENGTH_SHORT).show();
    }
}
