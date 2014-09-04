package com.atm.photoscanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by parrzhang on 2014/8/6.
 * 多图浏览控件
 * 传递类型
 * http://表示网络格式
 * file://表示文件格式
 */
public class GalleryActivity extends Activity {

    public static final String IndexExtra = "Index";
    public static final String GalleyDataExtra = "GalleyData";

    public static final int ResizeWidth = 800;//限制大图长度不要超过800
    public static final int ResizeHeight = 800;//限制大图高度不要超过800
    ArrayList<String> mGalleyData = new ArrayList<String>();
    private GalleryAdapter mGalleryAdapter;
    ViewPager mViewPage;
    int mCurrentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mViewPage = (ViewPager) findViewById(R.id.viewpager);
        Intent intent = getIntent();
        mGalleyData = intent.getStringArrayListExtra(GalleyDataExtra);
        mCurrentIndex = intent.getIntExtra(IndexExtra, 0);
        mGalleryAdapter = new GalleryAdapter();
        mViewPage.setAdapter(mGalleryAdapter);
        mViewPage.setCurrentItem(mCurrentIndex);
    }
    @Override
    protected void onStart() {
    	overridePendingTransition(R.anim.fade_in, 0);
    	super.onStart();
    }

    @Override
    public void finish() {
    	super.finish();
    	overridePendingTransition(0, R.anim.fade_out);
    }
    
    public class GalleryAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mGalleyData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_gallery,null);
            PhotoView photoView = (PhotoView)view.findViewById(R.id.photoview);
            /**
             * 轻点之后finish掉
             */
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
            Ion.with(container.getContext()).load(mGalleyData.get(position)).
                    withBitmap()
                    .error(R.drawable.ic_launcher).intoImageView(photoView);
            container.addView(view);
            return view;
        }
            /**
             * 大图往往占用内存较多，这里考虑手动回收
             * @param container
             * @param position
             * @param object
             */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View)object;
            PhotoView photoView = (PhotoView)view.findViewById(R.id.photoview);
            container.removeView(view);
            Drawable drawable = photoView.getDrawable();
            if(drawable instanceof BitmapDrawable){
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                if(bitmap!=null&&!bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }
        }
    }
}
