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
import com.imudges.LoveUApp.DAO.Get;
import com.imudges.LoveUApp.listener.Listener;
import com.imudges.LoveUApp.model.GetPresentModel;
import com.imudges.LoveUApp.model.YueStudyModel;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.service.PresentService;
import com.imudges.LoveUApp.service.StudyService;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.util.HttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by caolu on 2016/3/24.
 */
public class PresentDetailActivity extends Activity{
    private String id;
    private Button bt;
    private String url;
    private String responStr;
    private RequestParams params;
    private Bitmap bitmap;
    private TextView man,info;
    private ImageView img;
    private EditText ed;
    private TextView pinglun,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentdetail);
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        bt = (Button) findViewById(R.id.present_detail_bt);
        man = (TextView) findViewById(R.id.present_detail_man);
        info = (TextView) findViewById(R.id.present_detail_info);
        img = (ImageView) findViewById(R.id.present_detail_img) ;
        ed = (EditText) findViewById(R.id.present_detail_ed);
        pinglun = (TextView) findViewById(R.id.present_detail_pinglun);
        back=(TextView)findViewById(R.id.present_d_back) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getinfo();
        pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PresentDetailActivity.this,PresentCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                //startActivityForResult(intent, 10);
                startActivity(intent);
                //finish();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Get get=new Get("User",getApplicationContext());
                Get get1=new Get("UserKey",getApplicationContext());
                PresentService presentService = new PresentService();
                presentService.makeY(get1.getout("secretkey", ""), get.getout("username", ""), id,ed.getText().toString(), getApplicationContext(), new Listener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "约成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
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
                    man.setText("发出者   "+presentModels.getGiveUser());
                    info.setText(presentModels.getGiveInformation());
                    downPhoto(presentModels.getGiveImage());
                    //System.out.println(studyModels.getPostImage());
                }catch(Exception e){
                    //Toast.makeText(getApplicationContext(),e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
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
