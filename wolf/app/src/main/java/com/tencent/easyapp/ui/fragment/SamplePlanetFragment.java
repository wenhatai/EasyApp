package com.tencent.easyapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.RoundedImageView;
import com.tencent.easyapp.R;
import com.tencent.easyapp.ui.common.SampleBaseFragment;

import java.util.Random;

/**
 * Created by parrzhang on 2014/7/23.
 */
public  class SamplePlanetFragment extends SampleBaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final String ARG_PLANET_NUMBER = "planet_number";
    private RoundedImageView mRoundedImageView;
    private SwipeRefreshLayout mSwipLayout;

    public SamplePlanetFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static SampleBaseFragment newInstance(int position) {
        SampleBaseFragment fragment = new SamplePlanetFragment();
        Bundle args = new Bundle();
        args.putInt(SamplePlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        View view = inflater.inflate(R.layout.sample_frament_card,null);
        mSwipLayout = (SwipeRefreshLayout)view.findViewById(R.id.layout_swiprefresh);
        mSwipLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipLayout.setOnRefreshListener(this);
        mRoundedImageView = (RoundedImageView)view.findViewById(R.id.round_imageView);
        mRoundedImageView.setCornerRadius((float)(i*20));
        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                mRoundedImageView.setCornerRadius((float)random.nextInt(200));
                mRoundedImageView.invalidate();
                mSwipLayout.setRefreshing(false);
            }
        },3000);
    }
}