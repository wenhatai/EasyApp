package com.tencent.easyapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class SampleBaseGridAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<SampleJustinTvStreamData> mSampleJustinTvStreamDatas;
    private DisplayImageOptions mOptions;

    public SampleBaseGridAdapter(Context context,List<SampleJustinTvStreamData> sampleJustinTvStreamDatas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mSampleJustinTvStreamDatas = sampleJustinTvStreamDatas;
        this.mOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

    @Override
    public int getCount() {
        return mSampleJustinTvStreamDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mSampleJustinTvStreamDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        SampleJustinTvStreamData stream = mSampleJustinTvStreamDatas.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.sample_item_grid,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(stream.getTitle());
        ImageLoader.getInstance().displayImage(stream.getChannel().getImage_url_medium(),
                viewHolder.imageView, mOptions);
        return convertView;
    }

    private static class ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.item_grid_text);
            imageView = (ImageView)itemView.findViewById(R.id.item_grid_image);
        }
    }
}