package com.imudges.LoveUApp.ui.MealFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/3/14.
 */
public class MealSecondFragment extends Fragment {
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.run_2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv = (TextView) getView().findViewById(R.id.titleTv);
        tv.setText("this is 2");
    }
}
