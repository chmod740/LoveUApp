package com.imudges.LoveUApp.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dy on 2016/3/9.
 */
public class PhotoService {
    //上传的地址
    private String acceptUrl="";
    //下载地址
    private String downPathurl="";
    Bitmap bitmap;

    /**
     * 上传图片
     * @param pathString
     */

    public void doUpload(String pathString) {
        RandomAccessFile raf =  null;
        try{
            raf = new RandomAccessFile(pathString, "r");
            long alllength=raf.length();
            raf.seek(0);
            byte[] buffer = new byte[128*1024];//128k
            int count = 0;
            while ((count = raf.read(buffer)) != -1){
                String result = uploadFil(acceptUrl,buffer);
                System.out.println("MediaActivity doUpload return:"+result+ " count:"+count);
                break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(raf!=null)
                    raf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PhotoService photoService=new PhotoService();
    }
    public String uploadFil(String acceptUrl,byte[] data) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try{
            URL url = new URL(acceptUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(data.length);// 128*1024 是128k
//            允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);//application/octet-stream   multipart/form-data
            DataOutputStream dos  = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    // +pathString.substring(pathString.lastIndexOf("/")+1)
                    +"myfilename"
                    +"\""
                    +end);
            dos.writeBytes(end);
            dos.write(data,0,data.length);
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            String reponse = "";
            if(httpURLConnection.getResponseCode() == 200 ){
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);
                while (null !=br.readLine()) {
                    reponse +=br.readLine();
                }
                is.close();
            }
            System.out.println("MediaActivity uploadFil Reponse:"+reponse);
            dos.close();
            return reponse;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("MediaActivity uploadFil Exception:"+e.getMessage());
        }
        return "";
    }

    /**
     * 下载图片
     * 显示图片方法Handler(需放在Activity中与downPhoto函数一起用)
     */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x9527) {
                //显示从网上下载的图片
                //imageview.setImageBitmap(bitmap);
            }
        }
    };
    public void downPhoto(){
        new Thread(){
            @Override
            public void run() {
                try {
                    //创建一个url对象
                    URL url=new URL(downPathurl);
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
