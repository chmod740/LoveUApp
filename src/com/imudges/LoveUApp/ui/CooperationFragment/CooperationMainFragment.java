package com.imudges.LoveUApp.ui.CooperationFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.imudges.LoveUApp.model.MealModel;
import com.imudges.LoveUApp.ui.R;

import java.util.List;

/**
 * Created by 1111 on 2016/3/14.
 */
public class CooperationMainFragment extends Fragment {

    private String responStr;
    private List<MealModel> MealModels;
    private int Length=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView;
        SimpleAdapter simpleAdapter;

        listView = (ListView) getView().findViewById(android.R.id.list);
//        simpleAdapter = new SimpleAdapter(getActivity(),
//                getData(),
//                R.layout.item_meal_3_2,
//                new String[] { "img", "title", "time", "location","man","way"},
//                new int[] { R.id.meal3_2_img, R.id.meal3_2_tx1, R.id.meal3_2_tx2, R.id.meal3_2_tx3,R.id.meal3_2_tx4 ,R.id.meal3_2_tx5}
//        );
//        listView.setAdapter(simpleAdapter);
    }
}
