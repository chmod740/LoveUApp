package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity1 extends Activity {
    private EditText ednumber;
    private String number;
   // private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1_layout);
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

        Intent intent =new Intent(getApplicationContext(),RegisterActivity2.class);
        Bundle bundle = new Bundle();

        bundle.putString("number",number);
        intent.putExtras(bundle);
        startActivity(intent);



    }
}
