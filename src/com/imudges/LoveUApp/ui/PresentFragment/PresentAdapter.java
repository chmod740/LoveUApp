package com.imudges.LoveUApp.ui.PresentFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.imudges.LoveUApp.service.AsyncImageLoader;
import com.imudges.LoveUApp.ui.R;

import java.util.List;

/**
 * Created by dy on 2016/3/27.
 */
public class PresentAdapter extends BaseAdapter {
    private AsyncImageLoader asyncImageLoader;
    List<String> data;
    List<String> name;
    List<String> info;

    Context context;
    ImageView iv;
    TextView tv1,tv2;
    private ListView listView;

    public PresentAdapter(Context context, List<String> list,List<String> name,List<String> info,ListView listView) {
        this.context = context;
        this.data = list;
        this.name=name;
        this.info=info;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_present_1,null);
        }
        iv = (ImageView) convertView.findViewById(R.id.present_1_img);
        tv1=(TextView)convertView.findViewById(R.id.present_1_man) ;
        tv1.setText(name.get(position));
        tv2=(TextView)convertView.findViewById(R.id.present_1_info) ;
        tv2.setText(info.get(position));
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
