package com.imudges.LoveUApp.ui.PresentFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.service.PhotoService;
import com.imudges.LoveUApp.service.PresentService;
import com.imudges.LoveUApp.ui.MainActivity;
import com.imudges.LoveUApp.ui.MainPresentActivity;
import com.imudges.LoveUApp.ui.R;

import java.io.File;

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
        shengyu.setText("还可以输入100个字");
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
               shengyu.setText("还可以输入"+num+"个字");
            }
        });
        sure = (Button) getView().findViewById(R.id.present_2_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoString = info.getText().toString();
                info.setText("");
                UserImage.setImageResource(R.drawable.photo);
                PhotoService service=new PhotoService("http://loveu.iheshulin.com:9999/LOVEU/giveservice/UpGiveService.php");
                String s=service.Uppic(username,secretkey,Path,infoString);
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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

    public void setImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(IMAGE_TYPE);
        intent.putExtra("crop", "true");    // crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);      // 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);
        //输出地址
        intent.putExtra("outputX", 125);
        intent.putExtra("outputY", 125);
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
                UserImage.setImageBitmap(bitmap);

            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }

}
