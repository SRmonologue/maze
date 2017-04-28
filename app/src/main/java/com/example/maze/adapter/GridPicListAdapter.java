package com.example.maze.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.maze.util.ScreenUtil;

import java.util.List;

/**
 * Created by gz on 2017/4/28.
 */

public class GridPicListAdapter extends BaseAdapter{

    private Context mContext;
    private List<Bitmap> picList;

    public GridPicListAdapter(Context context, List<Bitmap> picList) {
        this.mContext = context;
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_pic_item = null;
        int density = (int) ScreenUtil.getDeviceDensity(mContext);
        if (convertView == null) {
            iv_pic_item = new ImageView(mContext);
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(100 * density, 120 * density));
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            iv_pic_item = (ImageView) convertView;
        }
        iv_pic_item.setBackgroundColor(Color.BLACK);
        iv_pic_item.setImageBitmap(picList.get(position));
        return iv_pic_item;
    }
}
