package com.tencent.easyapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.tencent.easyapp.R;
import com.tencent.easyapp.rest.api.SampleApiClient;
import com.tencent.easyapp.rest.modle.SampleJustinTvStreamData;
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
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_grid);
    }

    public void init(){
        SampleApiClient.getEappApiClient().getStreams(10,0,new Callback<List<SampleJustinTvStreamData>>() {
            @Override
            public void success(List<SampleJustinTvStreamData> sampleJustinTvStreamDatas, Response response) {
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }
}
