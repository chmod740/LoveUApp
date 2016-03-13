package com.imudges.LoveUApp.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dy on 2016/3/13.
 */
public class Save {

    private String doucation;
    private Context context;

    public Save(String doucation, Context context) {
        this.doucation = doucation;
        this.context = context;
    }

    public void savein(String name,String object){
        SharedPreferences mySharedPreferences= context.getSharedPreferences(doucation, Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(name, object);
        //提交当前数据
        editor.commit();
    }

}
