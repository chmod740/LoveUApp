package com.imudges.LoveUApp.ui.YueFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.imudges.LoveUApp.ui.MainActivity;
import com.imudges.LoveUApp.ui.MainYueActivity;
import com.imudges.LoveUApp.ui.R;
import com.imudges.LoveUApp.ui.Timer.DateTimePickDialogUtil;

/**
 * 这是上边菜单
 * Created by 1111 on 2016/3/16.
 */
public class YueAboveFragment1 extends Fragment{
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.run_above1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        btn = (Button) getView().findViewById(R.id.run_top_1_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainYueActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });
    }
}
