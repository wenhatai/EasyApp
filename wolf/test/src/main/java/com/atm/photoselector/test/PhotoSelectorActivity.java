package com.atm.photoselector.test;

import java.util.ArrayList;
import java.util.List;

import com.atm.photoselector.show.PhotoFileListActivity;
import com.atm.photoselector.show.ShowImageActivity;
import com.atm.photoselector.tool.GetSelectedPath;
import com.example.main.R;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

public class PhotoSelectorActivity extends Activity {
	private GridView gridView;
	private int selectNum = 7;
	private Bitmap addphoto;
	private PhotoGridAdapter pga;
	private List<Bitmap> list = new ArrayList<Bitmap>();
	private ArrayList<String> pathList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_selector);
		addphoto = BitmapFactory.decodeResource(getResources(),
				R.drawable.addphoto);
		gridView = (GridView) findViewById(R.id.photogrid);
		list.add(addphoto);
		pga = new PhotoGridAdapter(list, pathList, selectNum, this);
		gridView.setAdapter(pga);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position == list.size() - 1) {
					GetSelectedPath.startActivity(PhotoSelectorActivity.this,
							pathList, selectNum);
				}
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		list.remove(list.size() - 1);
		if (resultCode == PhotoFileListActivity.FILELIST_CODE
				|| resultCode == ShowImageActivity.COMFIRMTO_FILELIST) {
			List<String> tempSelectedList = GetSelectedPath
					.getSelectedPhoto(data);
			for (int i = 0; i < pathList.size(); i++) {
				if (!tempSelectedList.contains(pathList.get(i))) {
					pathList.remove(i);
					list.remove(i);
					i--;
				}
			}

			for (String temppath : tempSelectedList) {
				if (!pathList.contains(temppath)) {
					pathList.add(temppath);
					list.add(getSelectImage(temppath));
				}
			}

			reSetGridView();
		}
		if (resultCode == ShowImageActivity.CAMERA_BACKTOADD) {
			String cameraphoto = data.getStringExtra("cameraphoto");
			pathList.add(cameraphoto);
			list.add(getSelectImage(cameraphoto));
			reSetGridView();
		}
        list.add(addphoto);
	}

	public void reSetGridView() {

		pga.setBitmapList(list);
		pga.setPathList(pathList);
		pga.notifyDataSetChanged();
	}

	public Bitmap getSelectImage(String path) {

		Bitmap bitmap;
		Matrix matrix = new Matrix();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 1;
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opt);
		int bitmapSize = opt.outHeight * opt.outWidth * 4;
		opt.inSampleSize = bitmapSize / (1000 * 2000);
		opt.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, opt);
		int index = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth()
				: bitmap.getHeight();
		matrix.setScale(1, 1);
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, index, index,
				matrix, false);

		return bitmap2;
	}
}
