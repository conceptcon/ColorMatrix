package com.matrix.yukun.matrix.gif_module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.constant.AppConstant;
import com.matrix.yukun.matrix.video_module.play.ImageDetailActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

/**
 * author: kun .
 * date:   On 2019/2/14
 */
public class RVGifAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context mContext;
    List<String> mImageBeans;

    public RVGifAdapter(Context context, List<String> imageBeans) {
        mContext = context;
        mImageBeans = imageBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.image_gif_item, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyHolder){

            Bitmap bitmap= BitmapFactory.decodeFile(mImageBeans.get(position));
            ((MyHolder) holder).mIvImage.setImageBitmap(bitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageDetailActivity.start(mContext,mImageBeans.get(position),true);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mImageBeans.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{
        ImageView mIvImage;
        ImageView mIvDel;
        public MyHolder(View itemView) {
            super(itemView);
            mIvImage=itemView.findViewById(R.id.iv_image);
            mIvDel=itemView.findViewById(R.id.iv_del);
        }
    }

}
