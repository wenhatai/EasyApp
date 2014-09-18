package com.atm.customimageview.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

/**
 * created by limingzhang on 2014/8/5
 */
public class Circle extends Shape {

	private float lightness = 1.0f;

	@Override
	public Bitmap getBitmap(Bitmap bitmap) {
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPX = bitmap.getWidth() / 2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(new float[] { lightness, 0, 0, 0, 0, 0, lightness, 0, 0, 0,// 改变亮度
				0, 0, lightness, 0, 0, 0, 0, 0, 1, 0 });

		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

	@Override
	public void setLightness(float lightness) {
		// TODO Auto-generated method stub
		this.lightness = lightness;
	}

}
