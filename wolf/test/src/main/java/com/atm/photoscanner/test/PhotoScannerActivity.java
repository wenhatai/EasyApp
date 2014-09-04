package com.atm.photoscanner.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.atm.photoscanner.GalleryActivity;
import com.example.main.R;

import java.util.ArrayList;

/**
 * Created by limingzhang on 2014/9/4.
 */
public class PhotoScannerActivity extends Activity{
    private ArrayList<String> imageList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoscanner);
        imageList.add("http://pic4.nipic.com/20091013/1693084_150144066529_2.jpg");
        imageList.add("http://pic9.nipic.com/20100816/4709945_092646058569_2.jpg");
        imageList.add("http://img2.duitang.com/uploads/item/201302/05/20130205211432_3YFTs.thumb.600_0.jpeg");
    }
    public void scannerOnclick(View view){
        Intent intent = new Intent(PhotoScannerActivity.this,
                GalleryActivity.class);
        intent.putExtra(GalleryActivity.IndexExtra, 0);
        intent.putStringArrayListExtra(GalleryActivity.GalleyDataExtra,
                imageList);
        PhotoScannerActivity.this.startActivity(intent);
    }
}
