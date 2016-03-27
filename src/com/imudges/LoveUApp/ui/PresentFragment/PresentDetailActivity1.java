package com.imudges.LoveUApp.ui.PresentFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.imudges.LoveUApp.model.GetPresentModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by caolu on 2016/3/26.
 */
public class PresentDetailActivity1 extends Activity{
    private String id;
    private Button bt;
    private String url;
    private String responStr;
    private RequestParams params;
    private Bitmap bitmap;
    private TextView man,info,back;
    private ImageView img;
    private boolean set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentdetail1);
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        set = bundle.getBoolean("set");
        bt = (Button) findViewById(R.id.present_detail1_bt);
        man = (TextView) findViewById(R.id.present_detail1_man);
        info = (TextView) findViewById(R.id.present_detai11_info);
        img = (ImageView) findViewById(R.id.present_detail1_img) ;
        back=(TextView)findViewById(R.id.present_d_back1) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getinfo();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PresentDetailActivity1.this,PresentCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
              if(set==true)
                  bundle.putBoolean("set",true);
                else
              bundle.putBoolean("set",false);
                intent.putExtras(bundle);
                //startActivityForResult(intent, 10);
                startActivity(intent);
                //finish();
            }
        });
    }
    public void getinfo(){
        url="giveservice/DetailGiveService.php";
        params=new RequestParams();
        params.add("GiveId",id);
        HttpRequest.post(getApplicationContext(), url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                responStr=new String(bytes);
                try{
                    //System.out.println(responStr);
                    Gson gson=new Gson();
                    GetPresentModel presentModels = gson.fromJson(responStr,GetPresentModel.class);
                    man.setText(presentModels.getGiveUser());
                    info.setText(presentModels.getGiveInformation());
                    downPhoto(presentModels.getGiveImage());
                    //System.out.println(studyModels.getPostImage());
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void downPhoto(String Urldownphoto){
        new Thread(){
            @Override
            public void run() {
                try {
                    //创建一个url对象
                    URL url=new URL(Urldownphoto);
                    //打开URL对应的资源输入流
                    InputStream is= url.openStream();
                    //从InputStream流中解析出图片
                    bitmap = BitmapFactory.decodeStream(is);
                    handler.sendEmptyMessage(0x9521);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9521) {
                //显示从网上下载的图片
                PhotoCut cut=new PhotoCut(getApplicationContext());
                bitmap=cut.toRoundBitmap(bitmap);
                img.setImageBitmap(bitmap);
            }
        }
    };
}
