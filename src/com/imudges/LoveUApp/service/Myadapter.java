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
    Context context;
    ImageView iv;
    TextView tv1,tv2,tv3,tv4;
    private ListView listView;

    public Myadapter(Context context, List<String> list,ListView listView) {
        this.context = context;
        this.data = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_run_3_1,null);
        }
        iv = (ImageView) convertView.findViewById(R.id.run3_img);
        tv1=(TextView) convertView.findViewById(R.id.run3_tx1);
        tv2=(TextView) convertView.findViewById(R.id.run3_tx2);
        tv3=(TextView) convertView.findViewById(R.id.run3_tx3);
        tv4=(TextView) convertView.findViewById(R.id.run3_1_way);
        String url=data.get(position).toString();
        iv.setTag(url);
        tv1.setText("hhhhhhhhhhhhhhhhhhhhhhhhhh");

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
