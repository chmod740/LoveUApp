package com.imudges.LoveUApp.ui.SellFragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.service.SellPhotoService;
import com.imudges.LoveUApp.service.SellService;
import com.imudges.LoveUApp.ui.R;

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
    private Button selectimg,sure;
    private ImageView Image;
    private String time;
    private String titleString,infoString;//EditText 中的数据
    private SellService sellService;


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
        if (resultCode != getActivity().RESULT_OK) {
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
                Cursor cursor = getActivity().managedQuery(originUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                Path = Environment.getExternalStorageDirectory().getPath()+ "/"+path.substring(20);
                Image.setImageBitmap(TestBitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
