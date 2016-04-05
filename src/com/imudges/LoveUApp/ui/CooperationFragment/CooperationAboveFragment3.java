package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import com.imudges.LoveUApp.ui.MainCooperationActivity;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/3/17.
 */
public class CooperationAboveFragment3 extends Fragment {
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cooperation_above3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        btn = (Button) getView().findViewById(R.id.cooperation_top_3_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainCooperationActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });
    }
}
