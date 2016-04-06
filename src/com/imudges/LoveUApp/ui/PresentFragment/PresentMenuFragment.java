package com.imudges.LoveUApp.ui.PresentFragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.DAO.GetPhoto;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.PhotoService;
import com.imudges.LoveUApp.ui.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 1111 on 2016/3/14.
 */
public class PresentMenuFragment extends Fragment {

    private ImageView userImage,userSetImg;
    private TextView UserTv,UserSet;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Toast.makeText(getActivity(),"Present",Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container,false);
        userImage=(ImageView)view.findViewById(R.id.menu_img_user);
        userSetImg = (ImageView) view.findViewById(R.id.userset_img);
        UserTv=(TextView) view.findViewById(R.id.menu_text);
        UserSet=(TextView) view.findViewById(R.id.userSet);

        setUser();
        Myclick();

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
                        startActivity(new Intent(getActivity().getApplicationContext(),MainYueActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainMealActivity.class));
                        break;
                    case 3:Toast.makeText(getActivity(), "由于资金流问题，该功能无法应用，敬请期待!", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity().getApplicationContext(),MainSellActivity.class));
                        break;
                    case 4:Toast.makeText(getActivity(), "由于资金流问题，该功能无法应用，敬请期待!", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity().getApplicationContext(),MainCooperationActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getActivity().getApplicationContext(),MainSyllabusActivity.class));
                        break;
                }
                //getActivity().finish();
            }
        });
        UserSet.setOnClickListener(new View.OnClickListener() {
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
        map.put("text", "   约" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("text", "   吃饭");
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
        UserTv.setText(get1.getout(get.getout("username",""),get.getout("username","")));

        GetPhoto getPhoto=new GetPhoto(Environment.getExternalStorageDirectory().getPath(),"UserAd");
        Bitmap bitmap=getPhoto.getphoto();
        userImage.setImageBitmap(bitmap);
    }
    private ProgressDialog progressDialog;
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private Bitmap TestBitmap;
    private String Path;
    public void Myclick(){
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType(IMAGE_TYPE);
                startActivityForResult(getAlbum, IMAGE_CODE);
            }
        });
    }
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
                Cursor cursor = getActivity().getContentResolver().query(originUri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(column_index);
                Path =Environment.getExternalStorageDirectory().getPath()+ "/"+path.substring(20);
                //Toast.makeText(MainActivity.this, Path, Toast.LENGTH_SHORT).show();
                PhotoCut bitmapUtil = new PhotoCut(getActivity());
                Bitmap myBitmap = bitmapUtil.toRoundBitmap(TestBitmap);
                userImage.setImageBitmap(myBitmap);

                Get get=new Get("User",getActivity().getApplicationContext());
                Get get1=new Get("UserKey",getActivity().getApplicationContext());
                get.getout("username","");get1.getout("secretkey","");
                String s=new PhotoService("http://loveu.iheshulin.com:9999/LOVEU/service/ImageService.php")
                        .uploadFile(get.getout("username",""),get1.getout("secretkey",""),Path);
                Toast.makeText(getActivity(),s , Toast.LENGTH_SHORT).show();

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
