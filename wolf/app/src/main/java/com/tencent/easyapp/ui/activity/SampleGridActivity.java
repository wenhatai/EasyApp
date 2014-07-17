package com.tencent.easyapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;

import com.google.gson.Gson;
import com.tencent.easyapp.R;
import com.tencent.easyapp.rest.api.SampleApiClient;
import com.tencent.easyapp.rest.modle.SampleJustinTvStreamData;
import com.tencent.easyapp.ui.adapter.SampleBaseGridAdapter;
import com.tencent.easyapp.ui.adapter.SampleGridAdapter;
import com.tencent.easyapp.ui.common.BaseActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleGridActivity extends BaseActivity{
    private SampleGridAdapter mSampleGridAdapter;
    private GridView mGridView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSampleGridAdapter = new SampleGridAdapter(SampleGridActivity.this,null);
        mRecyclerView.setAdapter(mSampleGridAdapter);
        setContentView(mRecyclerView);
//        setContentView(R.layout.sample_activity_grid);
//        mGridView = (GridView)findViewById(R.id.grid);
        init();
    }

    public void init(){
        SampleApiClient.getEappApiClient().getStreams(10,0,new Callback<List<SampleJustinTvStreamData>>() {
            @Override
            public void success(List<SampleJustinTvStreamData> sampleJustinTvStreamDatas, Response response) {
                Log.e("Tag",new Gson().toJson(sampleJustinTvStreamDatas));
                mSampleGridAdapter.setContent(sampleJustinTvStreamDatas);
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }
}
