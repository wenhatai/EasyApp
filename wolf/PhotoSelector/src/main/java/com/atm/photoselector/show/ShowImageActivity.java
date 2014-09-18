package com.atm.photoselector.show;

import java.io.File;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.atm.photoscanner.GalleryActivity;
import com.atm.photoselector.R;
import com.atm.photoselector.bean.ImageBean;
import com.atm.photoselector.bean.SQLThumbnailBean;
import com.atm.photoselector.tool.ImageLoader;
import com.atm.photoselector.tool.PhotoSelectorDao;

/**
 * 
 * created by limingzhang on 2014/8/8
 *
 */
public class ShowImageActivity extends Activity {
	private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
	// 缩略图和原图的路径映射表
	private HashMap<String, String> thumbAndSrcMapping;
	private TreeMap<Long, String> relatedPhotoMap = new TreeMap<Long, String>(
			new Comparator<Long>() {

				@Override
				public int compare(Long lhs, Long rhs) {
					return rhs.compareTo(lhs);
				}
			});
	private ArrayList<ImageBean> imageBeanList;
	private List<SQLThumbnailBean> stbBeanList;
	private final static int SCAN_OK = 1;
	private ProgressDialog mProgressDialog;
	private GridView myGridView;
	private List<String> relatedPhotoList = new ArrayList<String>();
	private PhotoGridViewAdapter adapter;
	private Button scanBtn;
	private Button confirmBtn;
	private ArrayList<String> mSelectedImageList;
	private int selectNum;
	public static final int SHOWIMAGE_CODE = 1;
	public static final int COMFIRMTO_FILELIST = 300;
	public static final int BACKTO_FILELIST = 400;
	private long nowtime;
	public static final int CAMERA_CODE = 200;
	public static final int CAMERA_BACKTOADD = 500;
	public List<String> nowShow;
	public boolean flag = false;
	public static boolean isFirstLoaded = true;
	private PhotoSelectorDao photoSelectorDao;
	private ArrayList<String> dealedSelectedPath = new ArrayList<String>();
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SCAN_OK:
				// 关闭进度条
				mProgressDialog.dismiss();
				imageBeanList = subGroupOfImage(mGruopMap);

				changeBtnState();
				adapter = new PhotoGridViewAdapter(ShowImageActivity.this,
						nowShow, mSelectedImageList, myGridView,
						thumbAndSrcMapping, isFirstLoaded);
				myGridView.setAdapter(adapter);
				break;
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gridimage);
		myGridView = (GridView) findViewById(R.id.child_grid);
		scanBtn = (Button) findViewById(R.id.scanner);
		confirmBtn = (Button) findViewById(R.id.confirm);
		selectNum = getIntent().getIntExtra("selectNum", 7);
		mSelectedImageList = getIntent()
				.getStringArrayListExtra("selectedList");

		thumbAndSrcMapping = (HashMap<String, String>) getIntent()
				.getSerializableExtra("thumbAndSrcMapping");
		if (thumbAndSrcMapping == null) {
			thumbAndSrcMapping = new HashMap<String, String>();
		}
		nowShow = getIntent().getStringArrayListExtra("data");
		flag = getIntent().getBooleanExtra("flag", false);
		relatedPhotoList.add("takephoto");
		photoSelectorDao = new PhotoSelectorDao(this);
		ImageLoader.getInstance(ShowImageActivity.this).setThreadShut(false);
		if (nowShow == null) {

			firstInitImage();

		} else {
			changeBtnState();
			adapter = new PhotoGridViewAdapter(ShowImageActivity.this, nowShow,
					mSelectedImageList, myGridView, thumbAndSrcMapping,
					isFirstLoaded);
			imageBeanList = (ArrayList<ImageBean>) getIntent()
					.getSerializableExtra("imageBeanList");
			mGruopMap = (HashMap<String, List<String>>) getIntent()
					.getSerializableExtra("mGruopMap");
			myGridView.setAdapter(adapter);
		}
		myGridView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				if (!"takephoto".equals(nowShow.get(position))) {
					ImageView mImageView = (ImageView) arg1
							.findViewById(R.id.child_image);
					ImageView mselectImage = (ImageView) arg1
							.findViewById(R.id.select_image);
					if (!mSelectedImageList
							.contains(changeThumbnailToSrc(nowShow
									.get(position)))) {
						if (mSelectedImageList.size() < selectNum) {
							mImageView.setAlpha(127);
							mselectImage.setVisibility(View.VISIBLE);
							mSelectedImageList.add(changeThumbnailToSrc(nowShow
									.get(position)));
						} else {
							Toast.makeText(ShowImageActivity.this,
									"图片不能超过" + selectNum + "张",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						mSelectedImageList.remove(changeThumbnailToSrc(nowShow
								.get(position)));
						mImageView.setAlpha(255);
						mselectImage.setVisibility(View.INVISIBLE);
					}
					changeBtnState();
				} else {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					nowtime = System.currentTimeMillis();
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
							.fromFile(new File(Environment
									.getExternalStorageDirectory()
									+ "/DCIM/Camera/" + nowtime + ".jpg")));
					startActivityForResult(intent, CAMERA_CODE);
				}
			}

		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_CODE) {
			try {
				Intent intent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(new File(Environment
						.getExternalStorageDirectory() + "/DCIM/Camera/",
						nowtime + ".jpg"));
				intent.setData(uri);
				this.sendBroadcast(intent); // 发一个通知给系统。告诉系统，拍好了一张照片。
			} catch (Exception e) {
				e.printStackTrace();
			}

			Intent intent = new Intent();
			intent.putExtra("cameraphoto",
					Environment.getExternalStorageDirectory() + "/DCIM/Camera/"
							+ nowtime + ".jpg");
			setResult(CAMERA_BACKTOADD, intent);
			finish();

		}
		if (resultCode == PhotoFileListActivity.FILELIST_CODE) {
			setResult(COMFIRMTO_FILELIST, data);
			finish();
		}
		if (resultCode == PhotoFileListActivity.FOR_FINISH) {
			finish();
		}
		if (resultCode == CAMERA_BACKTOADD) {
			setResult(CAMERA_BACKTOADD, data);
			finish();
		}
	}

	private void changeBtnState() {
		if (mSelectedImageList.size() > 0) {
			//有选中的图片
			confirmBtn.setEnabled(true);
			scanBtn.setEnabled(true);
			confirmBtn.setText("确定" + "(" + mSelectedImageList.size() + ")");
		} else {
			//未选中
			confirmBtn.setText("确定");
			confirmBtn.setEnabled(false);
			scanBtn.setEnabled(false);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.gc();
	}

	public void confirmClick(View view) {
		Intent intent = new Intent();
		intent.putStringArrayListExtra("imagePathList", mSelectedImageList);
		new Thread() {
			public void run() {
				ImageLoader.getInstance(ShowImageActivity.this)
						.insertPrepareData(getApplicationContext());
			}
		}.start(); // 插入缩略图.
		setResult(COMFIRMTO_FILELIST, intent);
		ImageLoader.getInstance(ShowImageActivity.this).cancelTask();
		finish();
	}

	public void scannerClick(View view) {
		Intent intent = new Intent(ShowImageActivity.this,
				GalleryActivity.class);

		intent.putExtra(GalleryActivity.IndexExtra, 0);
		intent.putStringArrayListExtra(GalleryActivity.GalleyDataExtra,
				mSelectedImageList);
		ShowImageActivity.this.startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// ImageLoader.getInstance(ShowImageActivity.this).cancelTask();
			if (flag == false) {
				Intent intent = new Intent(ShowImageActivity.this,
						PhotoFileListActivity.class);
				intent.putStringArrayListExtra("selectedList",
						mSelectedImageList);
				intent.putExtra("imageBeanList", imageBeanList);
				intent.putExtra("data", mGruopMap);
				intent.putExtra("thumbAndSrcMapping", thumbAndSrcMapping);
				startActivityForResult(intent, 0);
				return true;

			} else {
				Intent intent = new Intent();
				intent.putStringArrayListExtra("selectedList",
						mSelectedImageList);
				intent.putExtra("imageBeanList", imageBeanList);
				intent.putExtra("data", mGruopMap);
				intent.putExtra("thumbAndSrcMapping", thumbAndSrcMapping);
				setResult(ShowImageActivity.BACKTO_FILELIST, intent);
				finish();
				return false;
			}

		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	/**
	 * 初始图片路径页面
	 */
	private void firstInitImage() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}

		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

		new Thread(new Runnable() {

			@SuppressWarnings("rawtypes")
			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = ShowImageActivity.this
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);
				stbBeanList = photoSelectorDao.findAll();
				while (mCursor.moveToNext()) {
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					String parentName = null;
					SQLThumbnailBean sqlBean = containsPath(path);
					if (sqlBean == null) {
						File file = new File(path);
						if (!file.exists() || file.length() <= 0) {
							continue;
						}
						long time = file.lastModified();
						relatedPhotoMap.put(time, path);
						// 获取该图片的父路径名

						File f = new File(path).getParentFile();
						if (f == null) {
							continue;
						} else {
							parentName = f.getName();
						}
					} else {
						relatedPhotoMap.put(sqlBean.getCreateTime(),
								sqlBean.getThumbnailPath());
						path = sqlBean.getThumbnailPath();
						thumbAndSrcMapping.put(sqlBean.getThumbnailPath(),
								sqlBean.getSrcPaht());
						parentName = sqlBean.getfatherFoldName();
					}

					// 根据父路径名将图片放入到mGruopMap
					if (!mGruopMap.containsKey(parentName)) {
						List<String> chileList = new ArrayList<String>();
						chileList.add(path);
						mGruopMap.put(parentName, chileList);
					} else {
						mGruopMap.get(parentName).add(path);
					}
				}
				mCursor.close();
				Set entries = relatedPhotoMap.entrySet();
				Iterator it = null;
				if (entries != null) {
					it = entries.iterator();
					for (int k = 1; it.hasNext(); k++) {
						Map.Entry entry = (Map.Entry) it.next();
						relatedPhotoList.add(entry.getValue().toString());
						if (k > 99) {
							break;
						}
					}
				}
				nowShow = relatedPhotoList;
				mGruopMap.put("最近使用", relatedPhotoList);
				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(SCAN_OK);
			}
		}).start();

	}

	/**
	 * @param mGruopMap
	 * @return 根据获取到的mGruopMap，提取其中的信息，作为ImageFileListAdatper的数据源
	 */
	private ArrayList<ImageBean> subGroupOfImage(
			HashMap<String, List<String>> mGruopMap) {
		if (mGruopMap.size() == 0) {
			return new ArrayList<ImageBean>();
		}
		ArrayList<ImageBean> list = new ArrayList<ImageBean>();

		Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			ImageBean mImageBean = new ImageBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			mImageBean.setFolderName(key);
			if (key.equals("最近使用")) {
				mImageBean.setImageCounts(value.size() - 1);
			} else {
				mImageBean.setImageCounts(value.size());
			}
			mImageBean.setTopImagePath(value.get(0));// 获取该组的第一张图
			if (key.equals("最近使用")) {
				if (list.size() > 1) {
					mImageBean.setTopImagePath(value.get(1));// 如果目录是最近使用就获取第二张。
				}
				list.add(0, mImageBean);
			} else {
				list.add(mImageBean);
			}
		}
		return list;
	}

	public void createFile() {
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/DCIM/Camera");
		if (!file.exists()) {
			file.mkdir();
		}
	}

	private SQLThumbnailBean containsPath(String srcPath) {
		for (SQLThumbnailBean stbBean : stbBeanList) {
			if (stbBean.getSrcPaht().equals(srcPath)) {
				return stbBean;
			}
		}
		return null;
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
