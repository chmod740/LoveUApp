package com.imudges.LoveUApp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.ArcMenu.ArcMenu;

/**
 * Created by dy on 2016/3/9.
 */
public class MainActivity extends Activity {

    private ArcMenu mArcMenuLeftTop;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mArcMenuLeftTop = (ArcMenu) findViewById(R.id.id_arcmenu1);

        mArcMenuLeftTop.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener(){
            @Override
            public void onClick(View view, int pos){
                Toast.makeText(MainActivity.this,
                        pos + ":" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadData(Context context) {
        //第一个参数是 文件名字  第二个参数 是访问权限  一般是MODE_PRIVATE
        SharedPreferences sp = context.getSharedPreferences("config", MODE_PRIVATE);
        Toast.makeText(this, sp.getString("content", "").toString(), Toast.LENGTH_SHORT).show();
    }
    private void saveData(Context context,String string){
        SharedPreferences sp = context.getSharedPreferences("config", MODE_PRIVATE);
        //建立编辑器  然后开始写入 文件
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("content", string);
        editor.commit();
    }
}
