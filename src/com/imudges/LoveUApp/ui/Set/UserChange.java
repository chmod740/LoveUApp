package com.imudges.LoveUApp.ui.Set;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by dy on 2016/4/6.
 */
public class UserChange extends Activity {

    EditText name,saying;
    TextView zi;
    Button bt;
    String Name,Saying;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_userself);
        init();
        MyClick();
    }
    Integer k=10;
    public void MyClick(){
        saying.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s=saying.getText().toString();
                k=10-s.length();
                zi.setText(k+"");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void init(){
        name=(EditText) findViewById(R.id.self_name);
        saying=(EditText) findViewById(R.id.self_saying);
        bt=(Button) findViewById(R.id.self_ok);
        zi=(TextView) findViewById(R.id.self_num);
    }
}
