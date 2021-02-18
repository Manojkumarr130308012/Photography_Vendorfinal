package com.shashank.platform.loginui.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.shashank.platform.loginui.R;
import com.squareup.picasso.Picasso;

public class CustomGalleryAdapter extends BaseAdapter {

    Context context;
    String logos[];
    String logosid[];
    LayoutInflater inflter;
    public CustomGalleryAdapter(Context applicationContext, String[] logos, String[] logosid) {
        this.context = applicationContext;
        this.logos = logos;
        this.logosid = logosid;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon);// get the reference of ImageView
        Log.e("ddddddddddddddd",""+logosid[i]);
        Picasso.with(context).load(logosid[i]).resize(300, 300).into(icon);

//        Picasso.with(context).load(""+logos[i]).into(icon);
     // set logo images
        return view;
    }
}
