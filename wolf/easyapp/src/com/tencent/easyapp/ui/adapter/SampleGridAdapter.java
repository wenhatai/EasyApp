package com.tencent.easyapp.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.easyapp.R;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleGridAdapter extends RecyclerView.Adapter<SampleGridAdapter.ViewHolder>{
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
