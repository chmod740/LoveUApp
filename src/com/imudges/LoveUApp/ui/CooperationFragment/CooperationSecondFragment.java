package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/3/14.
 */
public class CooperationSecondFragment extends Fragment{

    private Button bt;
    private EditText neirong,biaoti;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cooperation_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt=(Button) getView().findViewById(R.id.cooperation2_tijiao);
        neirong = (EditText) getView().findViewById(R.id.cooperation2_neirong);
        biaoti = (EditText) getView().findViewById(R.id.cooperation2_biaoti);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ;
            }
        });
    }
}
