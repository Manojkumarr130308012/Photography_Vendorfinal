package com.astr.hc.celebrations.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astr.hc.celebrations.R;

import java.io.IOException;
import java.util.List;

public class PhtotAdapter extends RecyclerView.Adapter<PhtotAdapter.PhotoViewHolder>{
   private Context mContext;
   private List<Uri> mListPhoto;

    public PhtotAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Uri> list){
        this.mListPhoto=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
     Uri uri=mListPhoto.get(position);
     if (uri == null){
         return;
     }
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
            if (bitmap != null){
                holder.imgphoto.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (mListPhoto != null){
         return mListPhoto.size();
        }

        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgphoto;


        public PhotoViewHolder(@NonNull View itemView){
            super(itemView);

            imgphoto=itemView.findViewById(R.id.img_photo);

        }
    }
}
