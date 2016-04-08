package com.imudges.LoveUApp.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.StrictMode;

/**
 * Created by dy on 2016/3/9.
 */
public class PhotoService {

    private String fileName = "image.jpg";  //报文中的文件名参数
    private String uploadFile;//+ "/" + fileName;    //待上传的文件路径
    private String Url;//="http://183.175.12.157/LOVEU/service/ImageService.php";
    public PhotoService(String Url){
        this.Url=Url;
    }
    /**
     * 上传图片文件至Server的方法
     * 传参
     * 1,name
     * 2,secretkey
     * 3,图片本地地址Path
     * 回调图片并获取PAth方法：
             private ProgressDialog progressDialog;
             private final String IMAGE_TYPE="image/*";
             private final int IMAGE_CODE=1;
             private Bitmap TestBitmap;
             private String Path;

            private void setImage() {
                 // TODO Auto-generated method stub
                //使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType(IMAGE_TYPE);
                startActivityForResult(getAlbum, IMAGE_CODE);
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
                         Cursor cursor = managedQuery(originUri, proj, null, null, null);
                         int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                         cursor.moveToFirst();
                         String path = cursor.getString(column_index);
                         Path =Environment.getExternalStorageDirectory().getPath()+ "/"+path.substring(20);
                         //Toast.makeText(MainActivity.this, Path, Toast.LENGTH_SHORT).show();
                         BitmapUtil bitmapUtil = new BitmapUtil(MainActivity.this);
                         Bitmap myBitmap = bitmapUtil.toRoundBitmap(TestBitmap);
                         //ImageView userImage=find...;
                         //userImage.setImageBitmap(myBitmap);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                         // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
             }
     */
    public String uploadFile(String name,String secretkey,String Path){
        String postUrl=Url+"?"+"UserName="+name+"&SecretKey="+secretkey;
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try{
            URL url = new URL(postUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
            con.setDoOutput(true);
            /* Read from the connection. Default is true.*/
            con.setDoInput(true);
            /* Post cannot use caches */
            con.setUseCaches(false);
            /* Set the post method. Default is GET*/
            con.setRequestMethod("POST");
            /* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            /*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*/
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            /* 设置DataOutputStream，getOutputStream中默认调用connect()*/
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +"name=\"file\";filename=\"" +fileName + "\"" + end);
            ds.writeBytes(end);
            /* 取得文件的FileInputStream */
            uploadFile=Path;
            FileInputStream fStream = new FileInputStream(uploadFile);
            /* 设置每次写入8192bytes */
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];   //8k
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1){
            	/* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* 关闭流，写入的东西自动生成Http正文*/
            fStream.close();
            /* 关闭DataOutputStream */
            ds.close();
            /* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1){
                b.append((char) ch);
            }
            return "上传成功";

        } catch (Exception e){
            return e.toString();
        }
    }

    public String Uppic(String name,String secretkey,String Path,String info){
        String postUrl=Url+"?"+"UserName="+name+"&SecretKey="+secretkey+"&GiveInformation="+info;
        System.out.println("最后的信息："+info);
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try{
            URL url = new URL(postUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* Output to the connection. Default is false,
             set to true because post method must write something to the connection */
            con.setDoOutput(true);
            /* Read from the connection. Default is true.*/
            con.setDoInput(true);
            /* Post cannot use caches */
            con.setUseCaches(false);
            /* Set the post method. Default is GET*/
            con.setRequestMethod("POST");
            /* 设置请求属性 */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            /*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*/
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            /* 设置DataOutputStream，getOutputStream中默认调用connect()*/
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());  //output to the connection
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; " +"name=\"file\";filename=\"" +fileName + "\"" + end);
            ds.writeBytes(end);
            /* 取得文件的FileInputStream */
            uploadFile=Path;
            FileInputStream fStream = new FileInputStream(uploadFile);
            /* 设置每次写入8192bytes */
            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];   //8k
            int length = -1;
            /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1){
            	/* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* 关闭流，写入的东西自动生成Http正文*/
            fStream.close();
            /* 关闭DataOutputStream */
            ds.close();
            /* 从返回的输入流读取响应信息 */
            InputStream is = con.getInputStream();  //input from the connection 正式建立HTTP连接
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1){
                b.append((char) ch);
            }
            return "上传成功";

        } catch (Exception e){
            return e.toString();
        }
    }

    /**
     * 及时显示在线图片
     * 显示图片方法Handler(需放在Activity中与downPhoto函数一起用)
     */
    Bitmap bitmap;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                //显示从网上下载的图片
                //imageview.setImageBitmap(bitmap);
            }
        }
    };
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
                    //  imageview.setImageBitmap(bitmap);
                    //发送消息，通知UI组件显示图片
                    handler.sendEmptyMessage(0x9527);
                    //关闭输入流
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
