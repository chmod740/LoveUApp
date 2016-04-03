package com.imudges.LoveUApp.ui.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.google.gson.Gson;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.UserModel;
import com.imudges.LoveUApp.service.JWXTService;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;


/**
 * Created by dy on 2016/3/19.
 */
public class UserSet extends Activity {

    private EditText name,psd;
    private TextView back,truename,sex,major,gread;
    private Button ok;
    private CheckBox cb;
    private String usernum,secret,num,pass;
    private JWXTService jwxt;

    private String url;
    private String responStr;
    private RequestParams params;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_user);
        init();
        Myclick();
    }
    public void Myclick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb.isChecked()){
                    Get get=new Get("User",getApplicationContext());
                    usernum=get.getout("username","");
                    Get get1=new Get("UserKey",getApplicationContext());
                    secret=get1.getout("secretkey","");
                    num=name.getText().toString();
                    pass=psd.getText().toString();

                    setup(usernum,secret,num,pass);
                }else{
                    Toast.makeText(UserSet.this, "您未勾选保密协议", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * 上传JWXT
     */
    public void setup(String username,String secretkey,String num,String pas){
        jwxt=new JWXTService();
        jwxt.setup(getApplicationContext(), username, secretkey, num, pas, new Listener() {
            @Override
            public void onSuccess() {
                getService(username);
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(UserSet.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * 回调教务信息
     */
    public void getService(String username){
        int k=1;
        char a[]=username.toCharArray();
        for (char s:a) {
            if(s>='0'&&s<='9'){
                k++;
            }
        }
        if(k==12){
            url="service/NumberService.php";
            params=new RequestParams();
            params.add("UserPhone",username);
            HttpRequest.get(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    responStr = new String(bytes);
                    try {
                        UserModel userModel = new Gson().fromJson(responStr, UserModel.class);
                        if (userModel.getState() == 1) {
                            truename.setText(userModel.getTrueName());
                            if(userModel.getUserSex()==1){
                                sex.setText("男");
                            }else{
                                sex.setText("女");
                            }
                            major.setText(userModel.getUserMajor());
                            gread.setText(userModel.getUserGrade());
                        } else {
                            Toast.makeText(UserSet.this, userModel.getMag(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(UserSet.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(UserSet.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            url="service/UserNameService.php";
            params=new RequestParams();
            params.add("UserName",username);
            HttpRequest.get(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    responStr=new String(bytes);
                    try{
                        UserModel userModel=new Gson().fromJson(responStr,UserModel.class);
                        if(userModel.getState()==1){
                            truename.setText(userModel.getTrueName());
                            if(userModel.getUserSex()==1){
                                sex.setText("男");
                            }else{
                                sex.setText("女");
                            }
                            major.setText(userModel.getUserMajor());
                            gread.setText(userModel.getUserGrade());
                        }else{
                            Toast.makeText(UserSet.this,userModel.getMag(), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        Toast.makeText(UserSet.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(UserSet.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /**
     * 初始化
     */
    public void init(){
        name=(EditText)findViewById(R.id.manage_number);
        psd=(EditText)findViewById(R.id.manage_password);
        back=(TextView)findViewById(R.id.manage_back);
        truename=(TextView)findViewById(R.id.manage_TrueName);
        sex=(TextView)findViewById(R.id.manage_sex);
        major=(TextView)findViewById(R.id.manage_major);
        gread=(TextView)findViewById(R.id.manage_grade);
        ok=(Button)findViewById(R.id.manage_ok);
        cb=(CheckBox) findViewById(R.id.manage_agreement);
    }

}
