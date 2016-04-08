package com.imudges.LoveUApp.ui.Set;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.DAO.Save;
import com.imudges.LoveUApp.DAO.SavePhoto;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.ChongzhiService;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.PhotoService;
import com.imudges.LoveUApp.service.UserService;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.SysApplication;

import java.io.File;

/**
 * Created by dy on 2016/4/6.
 */
public class UserChange extends Activity {

    EditText name,saying;
    TextView zi,user,money;
    Button bt;
    ImageView iv;
    ChongzhiService service=new ChongzhiService();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_userself);
        SysApplication.getInstance().addActivity(this);
        init();
        MyClick();
        Myview();
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
        iv=(ImageView) findViewById(R.id.self_iv);
        user=(TextView) findViewById(R.id.self_user);
        money=(TextView) findViewById(R.id.self_money);

        Get get=new Get("User",getApplicationContext());
        Get get1=new Get("UserKey",getApplicationContext());
        Get get2=new Get("Nick",getApplicationContext());
        user.setText(get.getout("username",""));
        saying.setText(get.getout("saying",""));
        name.setText(get2.getout(get.getout("username",""),get.getout("username","")));

        service.Getmoney(getApplicationContext(), get.getout("username", ""), get1.getout("secretkey", ""), new Listener() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onFailure(String msg) {
                money.setText(msg);
            }
        });

        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        iv.setImageBitmap(bitmap);
    }
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private String Path;
    public void Myview(){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(IMAGE_TYPE);
                intent.putExtra("crop", "true");    // crop=true 有这句才能出来最后的裁剪页面.
                intent.putExtra("aspectX", 1);      // 这两项为裁剪框的比例.
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                //输出地址
                intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg")));
                intent.putExtra("outputFormat", "JPEG");//返回格式
                startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_CODE);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save save=new Save("User",getApplicationContext());
                save.savein("saying",saying.getText().toString());

                Get get=new Get("User",getApplicationContext());
                Get get1=new Get("UserKey",getApplicationContext());
                Save save1=new Save("Nick",getApplicationContext());
                save1.savein(get.getout("username",""),name.getText().toString());
                Toast.makeText(UserChange.this, "更改成功", Toast.LENGTH_SHORT).show();

//                UserService service=new UserService();
//                service.changesaying(getApplicationContext(), get.getout("username", ""), get1.getout("secretkey",""), saying.getText().toString(),
//                        name.getText().toString(), new Listener() {
//                            @Override
//                            public void onSuccess() {
//                                Toast.makeText(UserChange.this, "更改成功", Toast.LENGTH_SHORT).show();
//                            }
//                            @Override
//                            public void onFailure(String msg) {
//                            }
//                        });
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg", options);
                Path=Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg";

                PhotoCut bitmapUtil = new PhotoCut(getApplicationContext());
                Bitmap myBitmap = bitmapUtil.toRoundBitmap(bitmap);
                iv.setImageBitmap(myBitmap);

                SavePhoto savePhoto=new SavePhoto(myBitmap,Environment.getExternalStorageDirectory().getPath(),"UserAd");
                savePhoto.Savephoto();

                Get get=new Get("User",getApplicationContext());
                Get get1=new Get("UserKey",getApplicationContext());
                //get.getout("username","");get1.getout("secretkey","");
                String s=new PhotoService("http://loveu.iheshulin.com:9999/LOVEU/service/ImageService.php")
                        .uploadFile(get.getout("username",""),get1.getout("secretkey",""),Path);
                Toast.makeText(getApplicationContext(),s , Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }
}
