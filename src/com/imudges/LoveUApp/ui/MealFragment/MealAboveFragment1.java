package com.imudges.LoveUApp.ui.MealFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.MainCooperationActivity;
import com.imudges.LoveUApp.ui.MainMealActivity;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/4/1.
 */
public class MealAboveFragment1 extends Fragment {
    private TextView tv_titile ;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meal_above1,container,false);
        tv_titile = (TextView) view.findViewById(R.id.meal_above1_tv);
        tv_titile.setText("吃饭");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        btn = (Button) getView().findViewById(R.id.meal_top_1_button);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                MainMealActivity.getMenu().showMenu();//显示SlidingMenu
            }
        });
    }
}
