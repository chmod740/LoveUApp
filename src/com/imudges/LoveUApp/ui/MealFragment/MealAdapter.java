package com.imudges.LoveUApp.ui.MealFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.imudges.LoveUApp.service.AsyncImageLoader;
import com.imudges.LoveUApp.service.PhotoCut;
import com.imudges.LoveUApp.ui.R;

import java.util.List;

/**
 * Created by dy on 2016/3/28.
 */
public class MealAdapter extends BaseAdapter {
    private AsyncImageLoader asyncImageLoader;
    List<String> data;
    List<String> name;
    List<String> area;
    List<String> time;
    List<String> way;
    List<String> info;

    Context context;
    ImageView iv;
    TextView tv1,tv3,tv4,tv5,tv2;
    private ListView listView;

    public MealAdapter(Context context, List<String> list,List<String> name,List<String> area,
                      List<String> time,List<String> way,List<String> info,ListView listView) {
        this.context = context;
        this.data = list;
        this.name=name;
        this.area=area;
        this.time=time;
        this.way=way;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_meal_3_2,null);
        }

        tv1=(TextView) convertView.findViewById(R.id.meal3_2_tx4);
        tv1.setText(name.get(position));
        tv2=(TextView) convertView.findViewById(R.id.meal3_2_tx3);
        tv2.setText(way.get(position));
        tv3=(TextView) convertView.findViewById(R.id.meal3_2_tx1);
        tv3.setText(time.get(position));
        tv5=(TextView) convertView.findViewById(R.id.meal3_2_tx2);
        tv5.setText(area.get(position));
        //tv4=(TextView) convertView.findViewById(R.id.meal3_2_tx5);
        //tv4.setText(info.get(position));

        iv = (ImageView) convertView.findViewById(R.id.meal3_2_img);
        try{
            String url=data.get(position);
            iv.setTag(url);
            Drawable cachedImage = asyncImageLoader.loadDrawable(url, new AsyncImageLoader.ImageCallback() {
                public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                    ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                    if (imageViewByTag != null) {
                        try{
                            BitmapDrawable bd = (BitmapDrawable) imageDrawable;
                            Bitmap bitmap = bd.getBitmap();
                            bitmap=new PhotoCut(context).toRoundBitmap(bitmap);
                            imageViewByTag.setImageBitmap(bitmap);
                        }catch(Exception e){
                            imageViewByTag.setImageDrawable(imageDrawable);
                        }
                    }
                }
            });
            if (cachedImage == null) {
                //iv.setImageResource(R.drawable.ic_launcher);
            }else{
                BitmapDrawable bd = (BitmapDrawable) cachedImage;
                Bitmap bitmap = bd.getBitmap();
                bitmap=new PhotoCut(context).toRoundBitmap(bitmap);
                iv.setImageBitmap(bitmap);
            }
        }catch(Exception e){
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.default1);
            iv.setImageBitmap(bitmap);
        }
        return convertView;
    }
}
