package com.tencent.easyapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tencent.easyapp.Note;
import com.tencent.easyapp.R;

import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleBaseGridAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Note> mNotes;

    public SampleBaseGridAdapter(Context context,List<Note> notes){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mNotes = notes;
    }

    public void setContent(List<Note> notes){
        this.mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNotes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Note note = mNotes.get(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.sample_item_grid,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(note.getTitle());
        if(note.getResource()!=null){
            Ion.with(mContext).load(note.getResource().getUrl()).intoImageView(viewHolder.imageView);
        }
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