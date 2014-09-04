package com.atm.photoselector.show;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.atm.photoselector.R;
import com.atm.photoselector.tool.ImageLoader;
import com.atm.photoselector.tool.ImageLoader.onImageLoaderListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * created by limingzhang on 2014/8/8
 *
 */
@SuppressLint("InflateParams")
public class PhotoGridViewAdapter extends BaseAdapter {
	private GridView mGridView;
	private List<String> list;
	private ImageLoader mImageLoader;
	protected LayoutInflater mInflater;
	private List<String> mSelectedImageList;
   private  Map<String,String> thumbAndSrcMapping;
	public PhotoGridViewAdapter(Context context, List<String> list,
			List<String> mSelectedImageList, GridView mGridView,Map<String,String> thumbAndSrcMapping,
			boolean isFirstLoaded) {
		this.list = list;
		this.mGridView = mGridView;
		this.mSelectedImageList = mSelectedImageList;
		mInflater = LayoutInflater.from(context);
		this.mImageLoader = ImageLoader.getInstance(context);
		this.thumbAndSrcMapping = thumbAndSrcMapping;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		final String path = list.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.griditem_show, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (ImageView) convertView
					.findViewById(R.id.child_image);
			viewHolder.mselectImage = (ImageView) convertView
					.findViewById(R.id.select_image);
			// 用来监听ImageView的宽和高
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if ("takephoto".equals(path)) {
			viewHolder.mImageView.setImageResource(R.drawable.takephoto);
		}
	
		if (mSelectedImageList
				.contains(changeThumbnailToSrc(path))) {
			viewHolder.mImageView.setAlpha(100);
			viewHolder.mselectImage.setVisibility(View.VISIBLE);
		} else {
			viewHolder.mImageView.setAlpha(255);
			viewHolder.mselectImage.setVisibility(View.INVISIBLE);
		}// 如果没有这个else就会出错

		viewHolder.mImageView.setTag(path);
		Bitmap bitmap = mImageLoader.getBitmapFromMemCache(path);
		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
		} else {
			if (!"takephoto".equals(path)) {
				viewHolder.mImageView
						.setImageResource(R.drawable.photoselectorlib_pictures_no);
				mImageLoader.loadNativeImage(path, new onImageLoaderListener() {
					@SuppressLint("NewApi")
					@Override
					public void onImageLoader(Bitmap bitmap, String url) {
						final ImageView mImageView = (ImageView) mGridView
								.findViewWithTag(url);
						if (mImageView != null && bitmap != null) {
							mImageView.setImageBitmap(bitmap);
						}
					}
				});
			} else {
				viewHolder.mImageView.setImageResource(R.drawable.takephoto);
			}
		}
		return convertView;
	}

	public static class ViewHolder {
		public ImageView mImageView;
		public ImageView mselectImage;
	}

	public void cancelTask() {
		mImageLoader.cancelTask();
	}

	public String changeThumbnailToSrc(String path) {
		String str = path.substring(path.lastIndexOf(File.separator) + 1,
				path.length());
		if (str.startsWith("Thumbnail")) {
			return thumbAndSrcMapping.get(path);
		} else {
			return path;
		}
	}
}