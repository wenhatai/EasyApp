package com.tencent.easyapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.easyapp.R;
import com.tencent.easyapp.rest.modle.SampleJustinTvStreamData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleGridAdapter extends RecyclerView.Adapter<SampleGridAdapter.ViewHolder>{

    private String testUrl = "http://rack.1.mshcdn.com/media/ZgkyMDE0LzA2LzI1LzU5L0FuZHJvaWQxLjE3YjNiLmpwZwpwCXRodW1iCTEyMDB4NjI3IwplCWpwZw/293f6197/1cb/Android1.jpg";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<SampleJustinTvStreamData> mSampleJustinTvStreamDatas;
    private DisplayImageOptions mOptions;

    public SampleGridAdapter(Context context,List<SampleJustinTvStreamData> sampleJustinTvStreamDatas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mSampleJustinTvStreamDatas = sampleJustinTvStreamDatas;
        this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

    public void setContent(List<SampleJustinTvStreamData> streamDatas){
        this.mSampleJustinTvStreamDatas = streamDatas;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.sample_item_grid,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    //it cannot excute by imageloader.i donot know why
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        SampleJustinTvStreamData stream = mSampleJustinTvStreamDatas.get(position);
        viewHolder.textView.setText(stream.getTitle());
        ImageLoader.getInstance().displayImage(testUrl, viewHolder.imageView, mOptions);
    }

    @Override
    public int getItemCount() {
        return mSampleJustinTvStreamDatas==null?0:mSampleJustinTvStreamDatas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_grid_text);
            imageView = (ImageView)itemView.findViewById(R.id.item_grid_image);
        }
    }
}
