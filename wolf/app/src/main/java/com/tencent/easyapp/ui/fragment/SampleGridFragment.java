package com.tencent.easyapp.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.tencent.easyapp.DaoSession;
import com.tencent.easyapp.Note;
import com.tencent.easyapp.NoteDao;
import com.tencent.easyapp.R;
import com.tencent.easyapp.ResourceDao;
import com.tencent.easyapp.rest.api.SampleApiClient;
import com.tencent.easyapp.ui.adapter.SampleBaseGridAdapter;
import com.tencent.easyapp.ui.common.SampleBaseFragment;
import com.tencent.easyapp.util.EappLog;

import java.util.List;

import de.greenrobot.dao.query.LazyList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by parrzhang on 2014/7/23.
 */
public class SampleGridFragment extends SampleBaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeLayout;
    private SampleBaseGridAdapter mSampleNoteAdapter;
    private ListView mListView;

    private static final int Limit = 10;
    private int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_fragment_grid,null);
        init(view);
        return  view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        },3000);
    }

    public void init(View view){
        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.layout_swiprefresh);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView = (ListView)view.findViewById(R.id.listview);

        DaoSession daoSession = getBaseActivity().getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        LazyList<Note> notes = noteDao.queryBuilder().limit(Limit).listLazyUncached();
        mSampleNoteAdapter = new SampleBaseGridAdapter(getActivity(),notes);
        mListView.setAdapter(mSampleNoteAdapter);
    }

    private void loadData(){
        SampleApiClient.getEappApiClient().getStreams(Limit,page,new Callback<List<Note>>() {
            @Override
            public void success(List<Note> notes, Response response) {
                EappLog.e(new Gson().toJson(notes));
                NoteDao noteDao = getBaseActivity().getDaoSession().getNoteDao();
                ResourceDao resourceDao = getBaseActivity().getDaoSession().getResourceDao();
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
