package com.matrix.yukun.matrix.video_module.play;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.matrix.yukun.matrix.video_module.BaseActivity;
import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.video_module.MyApplication;
import com.matrix.yukun.matrix.video_module.adapter.VideoAdapter;
import com.matrix.yukun.matrix.video_module.dialog.ShareDialog;
import com.matrix.yukun.matrix.video_module.entity.AttentList;
import com.matrix.yukun.matrix.video_module.entity.CollectsInfo;
import com.matrix.yukun.matrix.video_module.entity.EyesInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;

public class PersonActivity extends BaseActivity implements View.OnClickListener, VideoAdapter.ItemClickCallBack {
    @BindView(R.id.tv_col)
    TextView mTvCollect;
    @BindView(R.id.tv_attent)
    TextView mTvAttent;
    @BindView(R.id.iv_header_bg)
    ImageView mIvBg;
    @BindView(R.id.iv_avator)
    CircleImageView mCircleImageView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_remind)
    RelativeLayout mLayout;
    private EyesInfo mEyesInfo;
    private String mNextUrl;
    private int mType;
    private List<EyesInfo> mEyesInfos=new ArrayList<>();
    private VideoAdapter mJokeAdapter;

    public static void start(Context context,EyesInfo eyesInfo,String nextUrl){
        Intent intent=new Intent(context,PersonActivity.class);
        intent.putExtra("next_url",nextUrl);
        intent.putExtra("eyesInfo",eyesInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_person;
    }

    @Override
    public void initView() {
        mEyesInfo = (EyesInfo) getIntent().getSerializableExtra("eyesInfo");
        mNextUrl = getIntent().getStringExtra("next_url");
        mType = getIntent().getIntExtra("type",0);
        isAttent();
        isCollect();
        Glide.with(this).load(mEyesInfo.getData().getAuthor().getIcon()).into(mCircleImageView);
        //高斯模糊
        Glide.with(this).load(mEyesInfo.getData().getAuthor().getIcon()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Blurry.with(PersonActivity.this).sampling(1).from(resource).into(mIvBg);
            }
        });
        startAnimation(mIvBg);
    }

    private void startAnimation(final View view){
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP) {
                    Animator animationTop = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2,
                            view.getHeight() / 2, 0,
                            Math.max(view.getWidth() / 2,
                                    view.getHeight() / 2));
                    animationTop.start();
                }
            }
        });
    }
    private void startAnimation(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
          getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    mIvBg.setVisibility(View.GONE);
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onTransitionEnd(Transition transition) {
                    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP) {
                        Animator animationTop = ViewAnimationUtils.createCircularReveal(mIvBg, mIvBg.getWidth() / 2,
                                mIvBg.getHeight() / 2, 0,
                                Math.max(mIvBg.getWidth() / 2,
                                        mIvBg.getHeight() / 2));
                        animationTop.start();
                    }
                }
                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    @Override
    public void initListener() {
        mTvCollect.setOnClickListener(this);
        mTvAttent.setOnClickListener(this);
        mCircleImageView.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void initDate() {
        EyesInfo.DataBean.WebUrlBean webUrlBean=new EyesInfo.DataBean.WebUrlBean();
        webUrlBean.setForWeibo(mEyesInfo.getData().getPlayUrl());
        mEyesInfo.getData().setWebUrl(webUrlBean);
        mEyesInfos.add(mEyesInfo);
        mLayout.setVisibility(View.GONE);
        mJokeAdapter = new VideoAdapter(this,mEyesInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mJokeAdapter);
        mJokeAdapter.setItemClickCallBack(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        }
        if(v.getId()==R.id.iv_avator){
            Intent intent=new Intent(this, ImageDetailActivity.class);
            intent.putExtra("url",mEyesInfo.getData().getAuthor().getIcon());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,mCircleImageView,"shareview").toBundle());
            }else {
               startActivity(intent);
               overridePendingTransition(R.anim.rotate,R.anim.rotate_out);
            }
        }
        if(v.getId()==R.id.tv_col){
            if(mTvCollect.getText().equals("已收藏")){
                //取消收藏
                DataSupport.deleteAll(CollectsInfo.class, "play_url = ?", mEyesInfo.getData().getPlayUrl());
                Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                updateCollectView(false);
            }else {
                //加入
                CollectsInfo collectInfo = new CollectsInfo();
                collectInfo.setHeader(mEyesInfo.getData().getAuthor().getIcon());
                collectInfo.setCover(mEyesInfo.getData().getCover().getDetail());
                collectInfo.setTitle(mEyesInfo.getData().getTitle());
                collectInfo.setName(mEyesInfo.getData().getSlogan());
                collectInfo.setNextUrl(mNextUrl);
                collectInfo.setDescription(mEyesInfo.getData().getSlogan());
                collectInfo.setData(mEyesInfo.getData().getDate());
                collectInfo.setType(1);
                collectInfo.setPlay_url(mEyesInfo.getData().getPlayUrl());
                collectInfo.save();
                Toast.makeText(this, "添加到收藏成功", Toast.LENGTH_SHORT).show();
                updateCollectView(true);
            }
        }
        if(v.getId()==R.id.tv_attent){
            if(mTvAttent.getText().equals("已关注")){
                //取消收藏
                DataSupport.deleteAll(AttentList.class, "play_url = ?", mEyesInfo.getData().getPlayUrl());
                Toast.makeText(this, "取消关注成功", Toast.LENGTH_SHORT).show();
                updateAttentView(false);
            }else {
                //加入
                AttentList collectInfo = new AttentList();
                collectInfo.setHeader(mEyesInfo.getData().getAuthor().getIcon());
                collectInfo.setCover(mEyesInfo.getData().getCover().getDetail());
                collectInfo.setTitle(mEyesInfo.getData().getTitle());
                collectInfo.setName(mEyesInfo.getData().getSlogan());
                collectInfo.setNextUrl(mNextUrl);
                collectInfo.setDescription(mEyesInfo.getData().getSlogan());
                collectInfo.setData(mEyesInfo.getData().getDate());
                collectInfo.setType(1);
                collectInfo.setPlay_url(mEyesInfo.getData().getPlayUrl());
                collectInfo.save();
                Toast.makeText(this, "添加关注成功", Toast.LENGTH_SHORT).show();
                updateAttentView(true);
            }
        }
    }
    private void isCollect() {
        List<CollectsInfo> collectsInfos = DataSupport.findAll(CollectsInfo.class);
        for (int i = 0; i < collectsInfos.size(); i++) {
            if(collectsInfos.get(i).getPlay_url().equals(mEyesInfo.getData().getPlayUrl())){
                updateCollectView(true);
                return;
            }
        }
    }

    private void isAttent() {
        List<AttentList> collectsInfos = DataSupport.findAll(AttentList.class);
        for (int i = 0; i < collectsInfos.size(); i++) {
            if(collectsInfos.get(i).getPlay_url().equals(mEyesInfo.getData().getPlayUrl())){
                updateAttentView(true);
                return;
            }
        }
    }

    private void updateCollectView(boolean isCollect) {
        if(isCollect){
            mTvCollect.setBackgroundResource(R.drawable.shape_collect_bg_checked);
            mTvCollect.setTextColor(getResources().getColor(R.color.color_e9e7e7));
            mTvCollect.setText("已收藏");
        }else {
            mTvCollect.setBackgroundResource(R.drawable.shape_collect_bg_unchecked);
            mTvCollect.setTextColor(getResources().getColor(R.color.color_ff2323));
            mTvCollect.setText("+收藏");
        }
    }
    private void updateAttentView(boolean isAttent) {
        if(isAttent){
            mTvAttent.setBackgroundResource(R.drawable.shape_collect_bg_checked);
            mTvAttent.setTextColor(getResources().getColor(R.color.color_e9e7e7));
            mTvAttent.setText("已关注");
        }else {
            mTvAttent.setBackgroundResource(R.drawable.shape_collect_bg_unchecked);
            mTvAttent.setTextColor(getResources().getColor(R.color.color_ff2323));
            mTvAttent.setText("+关注");
        }
    }

    @Override
    public void onShareCallback(int pos) {
        EyesInfo eyesInfo = mEyesInfos.get(pos);
        ShareDialog shareDialog=ShareDialog.getInstance(eyesInfo.getData().getTitle(),eyesInfo.getData().getWebUrl().getForWeibo(),eyesInfo.getData().getCover().getDetail());
        shareDialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void onItemClickListener(EyesInfo eyesInfo, View view) {
        Intent intent = new Intent(this, VideoDetailPlayActivity.class);
        intent.putExtra("eyesInfo",eyesInfo);
        intent.putExtra("next_url",mNextUrl);
        intent.putExtra("type",3);//小视频界面要用
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) this,view,"shareView").toBundle());
        }else {
            startActivity(intent);
            overridePendingTransition(R.anim.rotate,R.anim.rotate_out);
        }
    }

    @Override
    public void onItemCollectClickListener(EyesInfo eyesInfo, View view) {
        CollectsInfo.setCollectInfo(eyesInfo,mNextUrl);
    }
}
