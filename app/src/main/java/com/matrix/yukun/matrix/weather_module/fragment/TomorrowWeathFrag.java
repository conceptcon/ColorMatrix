package com.matrix.yukun.matrix.weather_module.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrix.yukun.matrix.R;
import com.matrix.yukun.matrix.movie_module.BaseFrag;
import com.matrix.yukun.matrix.movie_module.activity.adapter.OnEventpos;
import com.matrix.yukun.matrix.weather_module.present.TomorrowPresent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yukun on 17-3-6.
 */
public class TomorrowWeathFrag extends BaseFrag {

    private TomorrowPresent tomorrowPresent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tomorrowPresent = new TomorrowPresent(this);
        this.basePresent= tomorrowPresent;
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.wea_tomorrow, null);
        return inflate;
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    public void getColor(OnEventpos onEventpos){
        int pos = onEventpos.pos;
        if(pos==1){
//            todayTitile.setBackgroundColor(getResources().getColor(R.color.color_82181818));
        }else if(pos==2){
//            todayTitile.setBackgroundColor(getResources().getColor(R.color.color_00ffffff));
        }
    }
}