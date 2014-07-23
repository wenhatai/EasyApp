package com.tencent.easyapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.tencent.easyapp.DaoSession;
import com.tencent.easyapp.Note;
import com.tencent.easyapp.NoteDao;
import com.tencent.easyapp.ResourceDao;
import com.tencent.easyapp.rest.api.SampleApiClient;
import com.tencent.easyapp.ui.adapter.SampleNoteAdapter;
import com.tencent.easyapp.ui.common.SampleBaseActivity;
import com.tencent.easyapp.util.EappLog;

import de.greenrobot.dao.query.LazyList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleGridActivity extends SampleBaseActivity{
    private SampleNoteAdapter mSampleNoteAdapter;
    private RecyclerView mRecyclerView;

    private static final int Limit = 10;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DaoSession daoSession = getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        LazyList<Note> notes = noteDao.queryBuilder().limit(Limit).listLazyUncached();
        mSampleNoteAdapter = new SampleNoteAdapter(SampleGridActivity.this,notes);
        mRecyclerView.setAdapter(mSampleNoteAdapter);
        setContentView(mRecyclerView);
        init();
    }

    public void init(){
        SampleApiClient.getEappApiClient().getStreams(Limit,page,new Callback<List<Note>>() {
            @Override
            public void success(List<Note> notes, Response response) {
                EappLog.e(new Gson().toJson(notes));
                NoteDao noteDao = getDaoSession().getNoteDao();
                ResourceDao resourceDao = getDaoSession().getResourceDao();
                for(int i = 0;i<notes.size();i++){
                    resourceDao.insertOrReplace(notes.get(i).getResource());
                    noteDao.insertOrReplace(notes.get(i));
                }
                mSampleNoteAdapter.setContent(notes);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                EappLog.e(retrofitError.getMessage());
            }
        });
    }
}
