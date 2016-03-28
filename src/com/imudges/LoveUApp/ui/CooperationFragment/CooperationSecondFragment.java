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
import com.imudges.LoveUApp.ui.Timer.HelpDateTimePickDialogUitl;

/**
 * Created by 1111 on 2016/3/14.
 */
public class CooperationSecondFragment extends Fragment{

    private Button bt;
    private EditText neirong,biaoti;
    private String initEndDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private EditText edtime;
    private String neirongString,biaotiString,paypassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cooperation_2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bt=(Button) getView().findViewById(R.id.cooperation2_tijiao);
        edtime = new EditText(getActivity().getApplicationContext());
        neirong = (EditText) getView().findViewById(R.id.cooperation2_neirong);
        biaoti = (EditText) getView().findViewById(R.id.cooperation2_biaoti);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpDateTimePickDialogUitl helpDateTimePickDialogUitl = new HelpDateTimePickDialogUitl(getActivity(),initEndDateTime,neirongString,biaotiString,paypassword);
                helpDateTimePickDialogUitl.dateTimePicKDialog(edtime);
                //getActivity().finish();
            }
        });
    }
}
