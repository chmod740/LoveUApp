package com.imudges.LoveUApp.ui.ArcMenu;

import com.imudges.LoveUApp.ui.MainActivity;

/**
 * Created by dy on 2016/3/22.
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        try{
            sleep(2000);
            MainActivity.key=false;
        }catch(Exception x){
            x.getLocalizedMessage();
        }
    }
}
