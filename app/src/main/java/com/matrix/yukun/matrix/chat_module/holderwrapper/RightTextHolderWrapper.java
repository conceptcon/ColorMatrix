package com.matrix.yukun.matrix.chat_module.holderwrapper;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.chat_module.entity.ChatListInfo;
import com.matrix.yukun.matrix.chat_module.holder.RightTextHolder;
import com.matrix.yukun.matrix.video_module.MyApplication;
import com.matrix.yukun.matrix.video_module.play.AboutUsActivity;
import com.matrix.yukun.matrix.video_module.play.LoginActivity;

import java.text.SimpleDateFormat;

/**
 * author: kun .
 * date:   On 2019/3/14
 */
public class RightTextHolderWrapper {
    static RightTextHolderWrapper mRightTextHolderWrapper;
    Context mContext;

    public static RightTextHolderWrapper getInstance(){
        if(mRightTextHolderWrapper==null){
            mRightTextHolderWrapper=new RightTextHolderWrapper();
        }
        return mRightTextHolderWrapper;
    }

    public void content(Context context, ChatListInfo chatListInfo, RightTextHolder holder){
        this.mContext=context;
        if(chatListInfo.isShowTime()){
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm:ss");//获取当前时间
            String str = formatter.format(chatListInfo.getMsgTime());
            (holder).mTextViewRightTime.setText(str);
            (holder).mTextViewRightTime.setVisibility(View.VISIBLE);
        }else {
            ( holder).mTextViewRightTime.setVisibility(View.GONE);
        }
        (holder).mTextViewRight.setText(chatListInfo.getChatInfo());
        if(MyApplication.userInfo==null){
            Glide.with(mContext).load(chatListInfo.getBitmap()).placeholder(R.drawable.head_2).into((holder).mImageViewRight);
        }else {
            Glide.with(mContext).load(MyApplication.getUserInfo().getImg()).into((holder).mImageViewRight);
        }
        (holder).mImageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.userInfo!=null) {
                    Intent intent=new Intent(mContext, AboutUsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }else {
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });

    }
}
