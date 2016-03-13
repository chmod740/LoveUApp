package com.imudges.LoveUApp.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dy on 2016/3/13.
 */
public class Get {
    private String documation;
    private Context context;

    public Get(String documation,Context context) {
        this.documation = documation;
        this.context=context;
    }
    public String getout(String name,String Ex){
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= context.getSharedPreferences(documation,Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return  sharedPreferences.getString(name,Ex);
    }
}
