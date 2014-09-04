package com.atm.customimageview.tool;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * 
 * @author limingzhang
 *
 */
public class BitmapCompress {

	public static  Bitmap getCompressBitmap(Bitmap image,int width,int hight) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if( baos.toByteArray().length / 1024>1024) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		int be = 1;//be=1表示不缩放
		if (w > h && w > width) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / width);
		} else if (w < h && h > hight) {//如果高度高的话根据高度固定大小缩放
			be = (int) (newOpts.outHeight / hight);
		}
		if (be <= 0){
			be = 1;
		}
		newOpts.inSampleSize = be;//设置缩放比例
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;
	}
}
