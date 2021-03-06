package com.matrix.yukun.matrix.chat_module.fragment.more;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.LinearLayout;

import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.chat_module.ChatBaseActivity;
import com.matrix.yukun.matrix.constant.AppConstant;
import com.matrix.yukun.matrix.video_module.BaseFragment;

import java.io.File;

/**
 * author: kun .
 * date:   On 2019/3/14
 */
public class ChatToolFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mLlPhoto;
    private LinearLayout mLlCamera;
    private LinearLayout mLlShake;
    private LinearLayout mLlFile;
    private static Context mContext;
    private Uri uri;
    public static File cameraSavePath;//拍照照片路径

    public static ChatToolFragment getInstance(Context context,String mCameraSavePath){
        mContext=context;
        cameraSavePath=new File(mCameraSavePath);
        ChatToolFragment chatToolFragment=new ChatToolFragment();
        return chatToolFragment;
    }
    @Override
    public int getLayout() {
        return R.layout.tool_chat_fragment;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        mLlPhoto = inflate.findViewById(R.id.ll_photo);
        mLlCamera = inflate.findViewById(R.id.ll_camera);
        mLlShake = inflate.findViewById(R.id.ll_shake);
        mLlFile = inflate.findViewById(R.id.ll_file);
        mLlPhoto.setOnClickListener(this);
        mLlCamera.setOnClickListener(this);
        mLlShake.setOnClickListener(this);
        mLlFile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.ll_photo){
           openPhoto();
        }else if(id==R.id.ll_camera){
           openCamera();
        }
        else if(id==R.id.ll_shake){
            //发送shake
            ((ChatBaseActivity)(mContext)).sendShakeListener();
        }
        else if(id==R.id.ll_file){
        }
    }

    private void openPhoto(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        ((Activity)mContext).startActivityForResult(intent, 2);
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(mContext, "com.xykj.customview.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity)mContext).startActivityForResult(intent, 1);
    }

//    ShakeClickListener mShakeClickListener;
//
//    public void setShakeClickListener(ShakeClickListener shakeClickListener) {
//        mShakeClickListener = shakeClickListener;
//    }
//
//    public interface ShakeClickListener{
//        void shakeClickListener();
//    }
}
