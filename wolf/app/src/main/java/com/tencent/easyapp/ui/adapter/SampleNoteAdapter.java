package com.tencent.easyapp.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.IonImageViewRequestBuilder;
import com.tencent.easyapp.Note;
import com.tencent.easyapp.R;
import com.tencent.easyapp.rest.modle.SampleJustinTvStreamData;

import java.util.List;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleNoteAdapter extends RecyclerView.Adapter<SampleNoteAdapter.ViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Note> mNotes;

    public SampleNoteAdapter(Context context, List<Note> notes){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mNotes = notes;
    }

    public void setContent(List<Note> notes){
        this.mNotes = notes;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = mInflater.inflate(R.layout.sample_item_grid,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Note note = mNotes.get(position);
        viewHolder.textView.setText(note.getTitle());
        Ion.with(mContext).load(note.getResource().getUrl()).intoImageView(viewHolder.imageView);
        mContext.getExternalCacheDir();
    }

    @Override
    public int getItemCount() {
        return mNotes==null?0:mNotes.size();
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
