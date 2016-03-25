package com.imudges.LoveUApp.service;

/**
 * Created by dy on 2016/3/24.
 */
import java.util.List;

import android.widget.TextView;
import com.imudges.LoveUApp.ui.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class Myadapter extends BaseAdapter {
    private AsyncImageLoader asyncImageLoader;
    List<String> data;
    List<String> name;
    List<String> info;
    List<String> area;
    List<String> time;
    List<String> state;

    Context context;
    ImageView iv;
    TextView tv1,tv2,tv3,tv4,tv5;
    private ListView listView;

    public Myadapter(Context context, List<String> list,List<String> name,List<String> info,List<String> area,
                     List<String> time,List<String> state,ListView listView) {
        this.context = context;
        this.data = list;
        this.name=name;
        this.info=info;
        this.area=area;
        this.time=time;
        this.state=state;
        asyncImageLoader = new AsyncImageLoader();
        this.listView=listView;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_run_3_2,null);
        }
        iv = (ImageView) convertView.findViewById(R.id.run3_2_img);
        tv1=(TextView) convertView.findViewById(R.id.run3_2_tx4);
        tv1.setText(name.get(position).toString());
        tv2=(TextView) convertView.findViewById(R.id.run3_2_tx1);
        tv2.setText(info.get(position).toString());
        tv3=(TextView) convertView.findViewById(R.id.run3_2_tx2);
        tv3.setText(time.get(position).toString());
        tv5=(TextView) convertView.findViewById(R.id.run3_2_tx3);
        tv5.setText(area.get(position).toString());
        tv4=(TextView) convertView.findViewById(R.id.run3_2_way);
        tv4.setText(state.get(position).toString());
        String url=data.get(position).toString();
        iv.setTag(url);

        Drawable cachedImage = asyncImageLoader.loadDrawable(url, new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage == null) {
            iv.setImageResource(R.drawable.ic_launcher);
        }else{
            iv.setImageDrawable(cachedImage);
        }
        return convertView;
    }

}
