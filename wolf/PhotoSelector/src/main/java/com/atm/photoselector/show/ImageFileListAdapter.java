package com.atm.photoselector.show;

import java.util.List;

import com.atm.photoselector.R;
import com.atm.photoselector.bean.ImageBean;
import com.atm.photoselector.tool.ImageLoader;
import com.atm.photoselector.tool.ImageLoader.onImageLoaderListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * created by limingzhang on 2014/8/8
 *
 */
public class ImageFileListAdapter extends BaseAdapter {
	private List<ImageBean> list;
	private LayoutInflater layoutInflater;
	private ImageLoader mImageDownLoader ;
	public ImageFileListAdapter(List<ImageBean> list, Context context,
			ListView mListView) {
		this.list = list;
		this.layoutInflater = LayoutInflater.from(context);
		mImageDownLoader = ImageLoader.getInstance(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder viewHolder ;
		String path = list.get(position).getTopImagePath();
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.listitem_file, null);
			ImageView mImageView = (ImageView) convertView
					.findViewById(R.id.imageView1);
			TextView nameView = (TextView) convertView
					.findViewById(R.id.filename);

			viewHolder = new ViewHolder();
			viewHolder.mImageView = mImageView;
			viewHolder.nameText = nameView;
		// 给ImageView设置路径Tag,这是异步加载图片的小技巧
			viewHolder.mImageView.setTag(path);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 利用NativeImageLoader类加载本地图片
		Bitmap bitmap = mImageDownLoader.loadNativeImage(path,new onImageLoaderListener() {

					@Override
					public void onImageLoader(Bitmap bitmap, String path){
						if (bitmap != null && viewHolder.mImageView != null) {
							viewHolder.mImageView.setImageBitmap(bitmap);
						}
					}
				});
		if(bitmap != null){
			viewHolder.mImageView.setImageBitmap(bitmap);
		}else{
			viewHolder.mImageView.setImageResource(R.drawable.default_bar_icon);
		}
		viewHolder.nameText.setText(list.get(position).getFolderName() + "("
				+ list.get(position).getImageCounts() + ")");
		return convertView;
	}
    
	class ViewHolder {
		ImageView mImageView;
		TextView nameText;
	}
}
