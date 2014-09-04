package com.atm.photoselector.show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.atm.photoselector.R;
import com.atm.photoselector.bean.ImageBean;
import com.atm.photoselector.tool.ImageLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
 * created by limingzhang on 2014/8/8
 *
 */
public class PhotoFileListActivity extends Activity {

	private ArrayList<String> selectedImageList;
	private ArrayList<ImageBean> imageBeanList;
	private HashMap<String, List<String>> mGruopMap;
	private HashMap<String,String> thumbAndSrcMapping;
	private ListView listView;
	private ImageFileListAdapter adapter;
	public static final int FILELIST_CODE = 9;
	public static final int FROMSHOW_IMAGEACTIVITY1 = 2; // 按确定返回
	public static final int FROMSHOWIMAGEACTIVITY2 = 3; // 按返回键返回
	public static final String SELECTEDIMAGE_BUDDLE = "bundle";
	public static final int FOR_FINISH = 600;
	public int selectNum;
	public boolean flag = true;// 表明PhotoFileLIstActivity出现过。

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_photofile);
		Intent intent = getIntent();
		selectedImageList = intent.getStringArrayListExtra("selectedList");
		imageBeanList = (ArrayList<ImageBean>) intent
				.getSerializableExtra("imageBeanList");
		selectNum = getIntent().getIntExtra("selectNum", 7);
		mGruopMap = (HashMap<String, List<String>>) getIntent()
				.getSerializableExtra("data");
		thumbAndSrcMapping = (HashMap<String,String>)getIntent().getSerializableExtra("thumbAndSrcMapping");
		if (selectedImageList == null) {
			selectedImageList = new ArrayList<String>();
		}
		listView = (ListView) findViewById(R.id.imagefilelist);
		adapter = new ImageFileListAdapter(imageBeanList, this, listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				List<String> childList = mGruopMap.get(imageBeanList.get(
						position).getFolderName());
				Intent mIntent = new Intent(PhotoFileListActivity.this,
						ShowImageActivity.class);
				mIntent.putStringArrayListExtra("data",
						(ArrayList<String>) childList);
				mIntent.putExtra("imageBeanList", imageBeanList);
				mIntent.putStringArrayListExtra("selectedList",
						(ArrayList<String>) selectedImageList);
				mIntent.putExtra("mGruopMap", mGruopMap);
				mIntent.putExtra("selectNum", selectNum);
				mIntent.putExtra("flag", flag);
				mIntent.putExtra("thumbAndSrcMapping", thumbAndSrcMapping);
				startActivityForResult(mIntent, 0);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == ShowImageActivity.COMFIRMTO_FILELIST) {
			setResult(FILELIST_CODE, data);
			finish();
		} else if (resultCode == ShowImageActivity.BACKTO_FILELIST) {
			selectedImageList = data.getStringArrayListExtra("selectedList");
			imageBeanList = (ArrayList<ImageBean>) data
					.getSerializableExtra("imageBeanList");
			thumbAndSrcMapping = (HashMap<String,String>)data.getSerializableExtra("thumbAndSrcMapping");
			selectNum = data.getIntExtra("selectNum", 7);
			mGruopMap = (HashMap<String, List<String>>) data
					.getSerializableExtra("data");
		} else if (resultCode == ShowImageActivity.CAMERA_BACKTOADD) {
			setResult(ShowImageActivity.CAMERA_BACKTOADD, data);
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(FOR_FINISH);
			//关闭线程池
			ImageLoader.getInstance(PhotoFileListActivity.this).cancelTask();
			//将缩略图信息插入数据库
			new Thread() {
				public void run() {
					ImageLoader.getInstance(PhotoFileListActivity.this)
							.insertPrepareData(getApplicationContext());
				}
			}.start(); // 插入缩略图.
			finish();
		}
		return false;
	}

}
