package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.imudges.LoveUApp.ui.R;

/**
 * Created by 1111 on 2016/3/14.
 */
public class CooperationMenuFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slidingmenu_fragment, null);
    }
}
