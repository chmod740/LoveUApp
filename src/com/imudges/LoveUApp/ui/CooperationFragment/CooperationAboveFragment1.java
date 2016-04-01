package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/4/1.
 */
public class CooperationAboveFragment1 extends Fragment {
    private TextView tv_titile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cooperation_above1,container,false);
        tv_titile = (TextView) view.findViewById(R.id.cooperation_above1_tv);
        tv_titile.setText("互助");
        return view;
    }
}
