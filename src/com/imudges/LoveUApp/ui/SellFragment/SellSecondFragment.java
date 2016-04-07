package com.imudges.LoveUApp.ui.SellFragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.SellPhotoService;
import com.imudges.LoveUApp.service.SellService;
import com.imudges.LoveUApp.ui.MainSellActivity;
import com.imudges.LoveUApp.ui.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 1111 on 2016/3/14.
 */
public class SellSecondFragment extends Fragment {


    private ProgressDialog progressDialog;
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private Bitmap TestBitmap;
    private String Path;

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private EditText title,info;
    private Button selectimg,sure,btn;
    private ImageView Image;
    private String time;
    private String titleString,infoString;//EditText 中的数据
    private SellService sellService;
    private TextView shengyu1,shengyu2;

    private static final String[] m={"三天","五天","七天"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sell_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        title = (EditText) getView().findViewById(R.id.sell_2_title);
        info = (EditText) getView().findViewById(R.id.sell_2_info);
        selectimg = (Button) getView().findViewById(R.id.sell_2_selectimg);
        sure = (Button) getView().findViewById(R.id.sell_2_sure);
        Image=(ImageView) getView().findViewById(R.id.sell_image);
        btn = (Button) getView().findViewById(R.id.sell_top_2_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainSellActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });

        shengyu1 = (TextView)getView().findViewById(R.id.sell_2_shengyu1);
        shengyu2 = (TextView)getView().findViewById(R.id.sell_2_shengyu2);
        shengyu1.setText("还可以输入20个字");
        shengyu2.setText("还可以输入100个字");
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num;
                num=20-editable.length();
                shengyu1.setText("还可以输入"+num+"个字");
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
                shengyu2.setText("还可以输入"+num+"个字");
            }
        });
        myclick();

        spinner = (Spinner)getView().findViewById(R.id.sell_2_spinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinnertext,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

    }
    public void myclick(){
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellService=new SellService();
                titleString=title.getText().toString();
                infoString=info.getText().toString();
                info.setText("");
                title.setText("");
                Image.setImageResource(R.drawable.photo);
                Get get=new Get("User",getActivity().getApplicationContext());
                Get get1=new Get("UserKey",getActivity().getApplicationContext());
                SellPhotoService sell=new SellPhotoService("http://loveu.iheshulin.com:9999/LOVEU/paiservice/UpPaiService.php");
                String msg=sell.uploadFile(get.getout("username",""),get1.getout("secretkey",""),infoString,titleString,time,Path);
//                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                System.out.println(msg);
            }
        });
    }

    /**
     * 回调图片
     */

    public void setImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(IMAGE_TYPE);
        intent.putExtra("crop", "true");    // crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);      // 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);
        //输出地址
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg")));
        intent.putExtra("outputFormat", "JPEG");//返回格式
        startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_CODE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg", options);
                Path=Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg";

//                PhotoCut bitmapUtil = new PhotoCut(getActivity());
//                Bitmap myBitmap = bitmapUtil.toRoundBitmap(bitmap);
                Image.setImageBitmap(bitmap);

            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }
    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            Toast.makeText(getActivity(),m[arg2],Toast.LENGTH_SHORT).show();
            switch (arg2){
                case 0:time="三天";break;
                case 1:time="五天";break;
                case 2:time="七天";break;
                default:time="错误数据";break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
