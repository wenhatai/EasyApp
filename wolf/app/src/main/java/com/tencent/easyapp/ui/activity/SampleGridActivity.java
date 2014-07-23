package com.tencent.easyapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.tencent.easyapp.rest.api.SampleApiClient;
import com.tencent.easyapp.rest.modle.SampleJustinTvStreamData;
import com.tencent.easyapp.ui.adapter.SampleGridAdapter;
import com.tencent.easyapp.ui.common.SampleBaseActivity;
import com.tencent.easyapp.util.EappLog;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleGridActivity extends SampleBaseActivity {
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
        init();
    }

    public void init(){
        SampleApiClient.getEappApiClient().getStreams(10,0,new Callback<List<SampleJustinTvStreamData>>() {
            @Override
            public void success(List<SampleJustinTvStreamData> sampleJustinTvStreamDatas, Response response) {
                EappLog.e(new Gson().toJson(sampleJustinTvStreamDatas));
                mSampleGridAdapter.setContent(sampleJustinTvStreamDatas);
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }
}
