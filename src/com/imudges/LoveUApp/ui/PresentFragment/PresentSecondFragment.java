package com.imudges.LoveUApp.ui.PresentFragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.SavePhoto;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.PhotoService;
import com.imudges.LoveUApp.service.PresentService;
import com.imudges.LoveUApp.ui.MainActivity;
import com.imudges.LoveUApp.ui.MainPresentActivity;
import com.imudges.LoveUApp.ui.R;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 1111 on 2016/3/14.
 */
public class PresentSecondFragment extends Fragment {

    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private Bitmap TestBitmap;
    private String Path;
    private static Bitmap myBitmap;

    /**********/
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String time;
    private EditText title,info;
    private Button selectimg,sure,btn;
    private String username,secretkey;
    private String titleString,infoString;//EditText 中的数据
    private PresentService presentService = new PresentService();
    private ImageView UserImage;
    private TextView shengyu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.present_2, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData(getActivity());
        UserImage = (ImageView) getView().findViewById(R.id.present_2_img);
        info = (EditText) getView().findViewById(R.id.present_2_info);
        selectimg = (Button) getView().findViewById(R.id.present_2_selectimg);
        shengyu = (TextView)getView().findViewById(R.id.present_2_surplus);
        shengyu.setText("还可以输入100字");
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });

        info.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num;
                num=100-editable.length();
               shengyu.setText("还可以输入"+num+"字");
            }
        });
        sure = (Button) getView().findViewById(R.id.present_2_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoString = info.getText().toString();
                info.setText("");
                //Toast.makeText(getActivity(),username+""+secretkey+""+infoString,Toast.LENGTH_SHORT).show();
                PhotoService service=new PhotoService("http://loveu.iheshulin.com:9999/LOVEU/giveservice/UpGiveService.php");
                //Get get1=new Get("UserKey",getActivity().getApplicationContext());
                String s=service.Uppic(username,secretkey,Path,infoString);
                //System.out.println("信息就是："+infoString);
//                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
//                System.out.println(s+"******************************************"+secretkey);
            }
        });

        btn = (Button) getView().findViewById(R.id.present_top_2_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainPresentActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });
    }

    private void loadData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        username = sp.getString("username", "").toString();
        SharedPreferences sd = context.getSharedPreferences("UserKey", Context.MODE_PRIVATE);
        secretkey = sd.getString("secretkey", "").toString();
    }

    private void setImage() {
        // TODO Auto-generated method stub
        //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        startActivityForResult(getAlbum, IMAGE_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getActivity().getContentResolver();
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originUri = data.getData();
                Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, originUri);
                TestBitmap = bm;
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(originUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                Path = Environment.getExternalStorageDirectory().getPath()+ "/"+path.substring(20);
                //Toast.makeText(MainActivity.this, Path, Toast.LENGTH_SHORT).show();
                PhotoCut bitmapUtil = new PhotoCut(getActivity());
                myBitmap = bitmapUtil.toRoundBitmap(TestBitmap);
                UserImage.setImageBitmap(myBitmap);


                /*SavePhoto savePhoto=new SavePhoto(myBitmap,Environment.getExternalStorageDirectory().getPath(),"UserAd");
                savePhoto.Savephoto();*/


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

}
