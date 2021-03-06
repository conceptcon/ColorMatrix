package com.matrix.yukun.matrix.video_module.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.matrix.yukun.matrix.btmovie_module.SpecialActivity;
import com.matrix.yukun.matrix.chat_module.ChatMemberActivity;
import com.matrix.yukun.matrix.download_module.DownLoadActivity;
import com.matrix.yukun.matrix.main_module.SearchActivity;
import com.matrix.yukun.matrix.util.ScreenUtils;
import com.matrix.yukun.matrix.video_module.MyApplication;
import com.matrix.yukun.matrix.video_module.entity.EventCategrayPos;
import com.matrix.yukun.matrix.video_module.entity.EventShowSecond;
import com.matrix.yukun.matrix.video_module.entity.EventUpdateHeader;
import com.matrix.yukun.matrix.video_module.entity.UserInfo;
import com.matrix.yukun.matrix.video_module.play.AboutUsActivity;
import com.matrix.yukun.matrix.video_module.play.HistoryTodayActivity;
import com.matrix.yukun.matrix.video_module.play.LoginActivity;
import com.matrix.yukun.matrix.video_module.play.MViewPagerAdapter;
import com.matrix.yukun.matrix.video_module.play.MyCollectActivity;
import com.matrix.yukun.matrix.video_module.play.PlayMainActivity;
import com.matrix.yukun.matrix.video_module.BaseFragment;
import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.R2;
import com.matrix.yukun.matrix.video_module.play.ShareActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * Created by yukun on 18-1-2.
 */

public class PlayFragment extends BaseFragment {
    @BindView(R2.id.iv_main)
    ImageView mIvMain;
    @BindView(R2.id.rl_contain)
    RelativeLayout mRlContain;
    @BindView(R2.id.tablayout)
    TabLayout mTablayout;
    @BindView(R2.id.drawlayout)
    DrawerLayout mDrawlayout;
    @BindView(R2.id.iv_close)
    ImageView mIvClose;
    @BindView(R2.id.viewpager)
    ViewPager mViewpager;
    @BindView(R2.id.im_snow)
    ImageView mImSnow;
    @BindView(R2.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R2.id.im_bird)
    ImageView mImBird;
    @BindView(R2.id.rl_movie)
    RelativeLayout mRlMovie;
    @BindView(R2.id.im_modu)
    ImageView mImModu;
    @BindView(R2.id.rl_change_modul)
    RelativeLayout mRlChangeModul;
    @BindView(R2.id.im_ball)
    ImageView mImBall;
    @BindView(R2.id.rl_me)
    RelativeLayout mRlMe;
    @BindView(R2.id.tv_close)
    TextView mTvClose;
    @BindView(R2.id.sc_contain)
    ScrollView mScrollview;
    @BindView(R2.id.rl_down)
    RelativeLayout mRlDown;
    @BindView(R2.id.iv_chat)
    ImageView mIvChat;
    @BindView(R2.id.iv_collect)
    ImageView mIvCollect;
    @BindView(R2.id.iv_share)
    ImageView mIvShare;
    @BindView(R2.id.rl_collect)
    RelativeLayout mRlCollect;
    @BindView(R.id.head)
    CircleImageView mCircleImageView;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.rl_bg_special)
    TextView mTvSig;
    @BindView(R2.id.iv_search)
    ImageView mIvSearch ;
    @BindView(R.id.ll_drawable)
    LinearLayout mLayout;
    private MViewPagerAdapter mMViewPagerAdapter;
    private String[] mStringArray;
    List<Fragment> mFragments = new ArrayList<>();
    private VideoFragment mInstance1;
    private ImageFragment mInstance3;
    private JokeFragment mInstance4;
    private TextFragment mInstance5;
    private RecFragment mInstance;
    int count=0;
    private VerticalVideoFragment mInstance2;

    public static PlayFragment getInstance() {
        PlayFragment playFragment = new PlayFragment();
        return playFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_play;
    }

    @Override
    public void initView(View inflate, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mStringArray = getResources().getStringArray(R.array.title);
        for (int i = 0; i < mStringArray.length; i++) {
            mTablayout.addTab(mTablayout.newTab().setText(mStringArray[i]));
        }
        setDrawableWidth();
        mInstance = RecFragment.getInstance();
        mInstance1 = VideoFragment.getInstance();
        mInstance2 = VerticalVideoFragment.getInstance();
        mInstance3 = ImageFragment.getInstance();
        mInstance4 = JokeFragment.getInstance();
        mInstance5 = TextFragment.getInstance();
        mFragments.add(mInstance);
        mFragments.add(mInstance1);
        mFragments.add(mInstance2);
        mFragments.add(mInstance3);
        mFragments.add(mInstance4);
        mFragments.add(mInstance5);
        setAdapter();
        setListener();
        OverScrollDecoratorHelper.setUpOverScroll(mScrollview);
        mTvClose.setText("登录");
        if(MyApplication.userInfo!=null){
            Glide.with(getContext()).load(MyApplication.userInfo.getImg()).into(mCircleImageView);
            mTvName.setText(MyApplication.userInfo.getName());
            mTvSig.setText("签名："+MyApplication.userInfo.getText());
            mTvClose.setText("退出");
        }
    }

    private void setDrawableWidth() {
        ViewGroup.LayoutParams layoutParams = mLayout.getLayoutParams();
        layoutParams.width= ScreenUtils.instance().getWidth(getContext())*4/5;
        mLayout.setLayoutParams(layoutParams);
    }

    private void setAdapter() {
        mMViewPagerAdapter = new MViewPagerAdapter(getChildFragmentManager(), mFragments, mStringArray);
        mViewpager.setAdapter(mMViewPagerAdapter);
        mViewpager.setOffscreenPageLimit(2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventCategrayPos event) {
        /* Do something */
        if (event.pos < 1000) {
            mViewpager.setCurrentItem(event.pos);
        }
    }
    private void setListener() {
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mTablayout.setScrollPosition(position, 0, true);
                EventBus.getDefault().post(new EventShowSecond(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTablayout.setupWithViewPager(mViewpager);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateHeader(EventUpdateHeader eventUpdateHeader){
        if(MyApplication.userInfo!=null){
            Glide.with(getContext()).load(MyApplication.userInfo.getImg()).into(mCircleImageView);
            mTvName.setText(MyApplication.userInfo.getName());
            mTvSig.setText("签名："+MyApplication.userInfo.getText());
            mTvClose.setText("退出");
        }
    }

    @OnClick({R2.id.iv_chat, R2.id.iv_main, R2.id.head, R2.id.iv_close, R2.id.rl_collect, R2.id.rl_main,R2.id.iv_search,
            R2.id.rl_movie, R2.id.rl_change_modul, R2.id.rl_me, R2.id.tv_close,R2.id.rl_bg_special,R2.id.iv_share,R.id.rl_down})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_main) {
            if (!mDrawlayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawlayout.openDrawer(Gravity.LEFT);
            }
        } else if (i == R.id.iv_close) {
            closeDrawLayout();
        } else if (i == R.id.rl_main) {
            mViewpager.setCurrentItem(0);
            closeDrawLayout();

        } else if (i == R.id.rl_movie) {//tool
            Intent intentHis = new Intent(getContext(), HistoryTodayActivity.class);
            startActivity(intentHis);
            ((Activity) getContext()).overridePendingTransition(R.anim.rotate, 0);
            closeDrawLayout();

        } else if (i == R.id.rl_change_modul) {
            closeDrawLayout();
            ((PlayMainActivity) getContext()).setNightMode();

        } else if (i == R.id.rl_collect) {
            Intent intentCol = new Intent(getContext(), MyCollectActivity.class);
            startActivity(intentCol);
            ((Activity) getContext()).overridePendingTransition(R.anim.rotate, R.anim.rotate_out);
            closeDrawLayout();

        } else if (i == R.id.rl_me) {
            if(MyApplication.userInfo!=null){
                Intent intentAbus = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intentAbus);
                ((Activity) getContext()).overridePendingTransition(R.anim.rotate, R.anim.rotate_out);
                closeDrawLayout();
            }else {
                Intent intent=new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
            }
        } else if (i == R.id.tv_close) {//退出
            //update UI
            closeDrawLayout();
            mCircleImageView.setImageResource(R.mipmap.snail_image);
            mTvName.setText("$_$");
            mTvSig.setText(getContext().getResources().getString(R.string.title_content));
            mTvClose.setText("登录");
            DataSupport.deleteAll(UserInfo.class);
            MyApplication.setUserInfo(null);
            Intent intent=new Intent(getContext(),LoginActivity.class);
            startActivity(intent);
        } else if (i == R.id.head) {
            if(MyApplication.userInfo!=null){
                Intent intentAbus = new Intent(getContext(), AboutUsActivity.class);
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
                    startActivity(intentAbus, ActivityOptions.makeSceneTransitionAnimation((Activity)getContext() ,mCircleImageView,"shareview").toBundle());
                }else {
                    getContext().startActivity(intentAbus);
                    ((Activity)getContext()).overridePendingTransition(R.anim.rotate,R.anim.rotate_out);
                }
                closeDrawLayout();
            }else {
                Intent intent=new Intent(getContext(),LoginActivity.class);
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT_WATCH){
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity)getContext() ,mCircleImageView,"shareview").toBundle());
                }else {
                    getContext().startActivity(intent);
                    ((Activity)getContext()).overridePendingTransition(R.anim.rotate,R.anim.rotate_out);
                }
            }
        } else if (i == R.id.iv_chat) {
            ChatMemberActivity.start(getContext());
            closeDrawLayout();

        }else if(i == R.id.iv_share){
            Intent intent=new Intent(getContext(), ShareActivity.class);
            startActivity(intent);
        } else if(i==R.id.rl_bg_special){
            count++;
            if(count==3){
                count=0;
                closeDrawLayout();
                Intent intent=new Intent(getContext(), SpecialActivity.class);
                startActivity(intent);
            }
        } else if(i==R.id.rl_down){
            Intent intentDown=new Intent(getContext(), DownLoadActivity.class);
            startActivity(intentDown);
        } else if(i==R.id.iv_search){
            SearchActivity.start(getContext(),mIvSearch);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void closeDrawLayout() {
        mDrawlayout.closeDrawer(Gravity.LEFT);
    }

    //双击退出
    private long firstTime = 0;

}
