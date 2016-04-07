package com.imudges.LoveUApp.ui.YueFragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.DAO.SavePhoto;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.PhotoService;
import com.imudges.LoveUApp.ui.*;
import com.imudges.LoveUApp.ui.Set.UserChange;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 1111 on 2016/3/10.
 */
public class YueMenuFragment extends Fragment {

    private ImageView userImage,userSetImg;
    private TextView userTv,userSet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container,false);
        userImage=(ImageView)view.findViewById(R.id.menu_img_user);
        userSetImg = (ImageView) view.findViewById(R.id.userset_img);
        userTv=(TextView) view.findViewById(R.id.menu_text);
        userSet=(TextView) view.findViewById(R.id.userSet);
        //tvOutput.setText (savedInstanceState.getString ("output"));

        setUser();
        Myclick();
        TextView tv=(TextView)view.findViewById(R.id.meun_saying);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserChange.class));
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.menu_list);
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),
                getData(),
                R.layout.slidingmenu_item,
                new String[]{"text"},
                new int[]{R.id.item}
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainMealActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainPresentActivity.class));
                        break;
                    case 3://Toast.makeText(getActivity(), "由于资金流问题，该功能无法应用，敬请期待!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity().getApplicationContext(),MainSellActivity.class));
                        break;
                    case 4://Toast.makeText(getActivity(), "由于资金流问题，该功能无法应用，敬请期待!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity().getApplicationContext(),MainCooperationActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainSyllabusActivity.class));
                        break;
                }
                //getActivity().finish();
            }
        });

        userSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),MainSetActivity.class));
            }
        });

        userSetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(),MainSetActivity.class));
            }
        });
        return view;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("text", "   主菜单");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "   吃饭" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "   赠送");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "   拍卖");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "   互助");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "   课程表");
        list.add(map);

        return list;
    }

    /**
     * 显示用户
     */
    public void setUser(){
        Get get=new Get("User",getActivity().getApplicationContext());
        //UserName.setText(get.getout("username",""));
        Get get1=new Get("Nick",getActivity().getApplicationContext());
        //Toast.makeText(getActivity().getApplicationContext(),get1.getout(get.getout("username",""),get.getout("username","")) , Toast.LENGTH_LONG).show();
        userTv.setText(get1.getout(get.getout("username",""),get.getout("username","")));

        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        userImage.setImageBitmap(bitmap);
    }
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private String Path;
    public void Myclick(){
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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

                PhotoCut bitmapUtil = new PhotoCut(getActivity());
                Bitmap myBitmap = bitmapUtil.toRoundBitmap(bitmap);
                userImage.setImageBitmap(myBitmap);

                SavePhoto savePhoto=new SavePhoto(myBitmap,Environment.getExternalStorageDirectory().getPath(),"UserAd");
                savePhoto.Savephoto();

                Get get=new Get("User",getActivity().getApplicationContext());
                Get get1=new Get("UserKey",getActivity().getApplicationContext());
                get.getout("username","");get1.getout("secretkey","");
                String s=new PhotoService("http://loveu.iheshulin.com:9999/LOVEU/service/ImageService.php")
                        .uploadFile(get.getout("username",""),get1.getout("secretkey",""),Path);
                Toast.makeText(getActivity(),s , Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }
}
