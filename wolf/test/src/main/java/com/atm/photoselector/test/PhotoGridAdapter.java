package com.atm.photoselector.test;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.main.R;

/**
 * 
 * created by limingzhang on 2014/8/10
 *
 */
@SuppressLint("ViewHolder")
public class PhotoGridAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Bitmap> BitmapList;
	private int selectNum;
    private List<String> pathList;
	public PhotoGridAdapter(List<Bitmap> BitmapList,List<String> pathList,int selectNum,
			Context context) {
		this.BitmapList = BitmapList;
		inflater = LayoutInflater.from(context);
		this.selectNum = selectNum;
		this.pathList = pathList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return BitmapList.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return BitmapList.get(location);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public List<Bitmap> getBitmapList() {
		return BitmapList;
	}

	public void setBitmapList(List<Bitmap> bitmapList) {
		BitmapList = bitmapList;
	}
    
	public List<String> getPathList() {
		return pathList;
	}

	public void setPathList(List<String> pathList) {
		this.pathList = pathList;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.cutimageview, null);
		ImageView photoView = (ImageView) view.findViewById(R.id.photo);
		ImageView cancel = (ImageView) view.findViewById(R.id.cancel);
		if(BitmapList.get(position)!=null){
		 photoView.setImageBitmap(BitmapList.get(position));
		}
        if(position==BitmapList.size()-1){
        	cancel.setVisibility(View.INVISIBLE);
        }else{
            cancel.setVisibility(View.VISIBLE);
        }
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BitmapList.remove(position);
				pathList.remove(position);
				notifyDataSetChanged();
			}
		});

		return view;
	}
}