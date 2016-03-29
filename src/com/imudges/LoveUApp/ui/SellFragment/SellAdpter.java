package com.imudges.LoveUApp.ui.SellFragment;

/**
 * Created by dy on 2016/3/25.
 */

import android.content.Context;
import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.List;
/**
 * Created by dy on 2016/3/25.
 */
public class SellAdpter extends BaseAdapter {
    private AsyncImageLoader asyncImageLoader;
    List<String> data;
    List<String> name;
    List<String> user;
    List<String> time;
    List<String> money;

    Context context;
    ImageView iv;
    TextView tv1,tv3,tv4,tv5;
    private ListView listView;

    public SellAdpter(Context context, List<String> list,List<String> name,List<String> user,
                      List<String> time,List<String> money,ListView listView) {
        this.context = context;
        this.data = list;
        this.name=name;
        this.user=user;
        this.time=time;
        this.money=money;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sell_1,null);
        }
        iv = (ImageView) convertView.findViewById(R.id.sell_1_img);
        tv1=(TextView) convertView.findViewById(R.id.sell_1_biaoti);
        tv1.setText(name.get(position));
        tv3=(TextView) convertView.findViewById(R.id.sell_1_time);
        tv3.setText(time.get(position));
        tv5=(TextView) convertView.findViewById(R.id.sell_1_paimairen);
        tv5.setText(user.get(position));
        tv4=(TextView) convertView.findViewById(R.id.sell_1_money);
        tv4.setText(money.get(position));
        String url=data.get(position).toString();
        iv.setTag(url);

        Drawable cachedImage = asyncImageLoader.loadDrawable(url, new AsyncImageLoader.ImageCallback() {
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    BitmapDrawable bd = (BitmapDrawable) imageDrawable;
                    Bitmap bitmap = bd.getBitmap();
                    bitmap=new PhotoCut(context).toRoundBitmap(bitmap);
                    imageViewByTag.setImageBitmap(bitmap);
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
        return convertView;
    }
}


