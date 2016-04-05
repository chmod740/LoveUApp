package com.imudges.LoveUApp.ui.PresentFragment;

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
public class PresentAboveFragment1 extends Fragment {
    TextView tv_titile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.present_above1,container,false);
        tv_titile = (TextView) view.findViewById(R.id.present_above1_tv);
        //tv_titile.setText("赠送");
        return view;
    }
}
