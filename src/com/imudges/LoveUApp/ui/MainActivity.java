package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.DAO.SavePhoto;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.PhotoService;
import com.imudges.LoveUApp.ui.ArcMenu.ArcMenu;
import com.imudges.LoveUApp.ui.ArcMenu.MyThread;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by dy on 2016/3/9.
 */
public class MainActivity extends Activity {

    private ProgressDialog progressDialog;
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private Bitmap TestBitmap;
    private String Path;
    private static Bitmap myBitmap;

    private Bitmap Mybitmap;
    private String PhotoUrl;
    public static boolean key=false;

    private ArcMenu mArcMenuLeftTop;
    private TextView UserSaying,UserName;
    private ImageView UserImage;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.main);
        SysApplication.getInstance().addActivity(this);

        init();
        myclick();
        setphoto();
        mArcMenuLeftTop = (ArcMenu) findViewById(R.id.id_arcmenu1);
        mArcMenuLeftTop.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener(){
            @Override
            public void onClick(View view, int pos){
                //Toast.makeText(MainActivity.this, pos + ":" + view.getTag(), Toast.LENGTH_SHORT).show();
                switch (pos){
                    case 0:startActivity(new Intent(getApplicationContext(),MainYueActivity.class));
                        break;
                    case 1:startActivity(new Intent(getApplicationContext(),MainPresentActivity.class));
                        break;
                    case 2:startActivity(new Intent(getApplicationContext(),MainSyllabusActivity.class));
                        break;
                    case 3://startActivity(new Intent(getApplicationContext(),MainSellActivity.class));
                        Toast.makeText(MainActivity.this, "由于资金流问题，该功能无法应用，敬请期待!", Toast.LENGTH_SHORT).show();
                        break;
                    case 4://startActivity(new Intent(getApplicationContext(),MainCooperationActivity.class));
                        Toast.makeText(MainActivity.this, "由于资金流问题，该功能无法应用，敬请期待!", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:startActivity(new Intent(getApplicationContext(),MainMealActivity.class));
                        break;
                }
                //finish();
            }
        });
    }

    /**
     * 回调图片地址
     */
    private void setImage() {
        // TODO Auto-generated method stub
        //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
        try{
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType(IMAGE_TYPE);
            startActivityForResult(getAlbum, IMAGE_CODE);
        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getContentResolver();
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originUri = data.getData();
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originUri);
                TestBitmap = bm;
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(originUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                Path = Environment.getExternalStorageDirectory().getPath()+ "/"+path.substring(20);
                //Toast.makeText(MainActivity.this, Path, Toast.LENGTH_SHORT).show();
                PhotoCut bitmapUtil = new PhotoCut(MainActivity.this);
                myBitmap = bitmapUtil.toRoundBitmap(TestBitmap);
                UserImage.setImageBitmap(myBitmap);

                Get get=new Get("User",getApplicationContext());
                SavePhoto savePhoto=new SavePhoto(myBitmap,Environment.getExternalStorageDirectory().getPath(),"UserAd");
                savePhoto.Savephoto();

                PhotoService service=new PhotoService("http://loveu.iheshulin.com:9999/LOVEU/service/ImageService.php");
                Get get1=new Get("UserKey",getApplicationContext());
                String s=service.uploadFile(get.getout("username",""),get1.getout("secretkey",""),Path);
//                System.out.println(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }
    public void myclick(){
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });

        Get get=new Get("User",getApplicationContext());
        //UserName.setText(get.getout("username",""));
        Get get1=new Get("Nick",getApplicationContext());
        UserName.setText(get1.getout(get.getout("username",""),get.getout("username","")));
    }

    /**
     * 初始化函数
     */
    public void init(){
        UserImage=(ImageView) findViewById(R.id.UserImage);
        UserName=(TextView) findViewById(R.id.UserName);
        UserSaying=(TextView)findViewById(R.id.UserSaying);
    }
    public void setphoto(){
        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        UserImage.setImageBitmap(bitmap);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(new Intent(MainActivity.this,MainSetActivity.class));
            //super.openOptionsMenu();  // 调用这个，就可以弹出菜单
            return super.onKeyDown(keyCode, event);
        }
        MyThread my=new MyThread();
        my.start();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0&& key==true) {
            finish();
            return true;
        }
        if(key==false){
            Toast.makeText(MainActivity.this, "(>ˍ<) ～您真的想要离开吗?", Toast.LENGTH_LONG).show();
            key=true;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
