package com.atm.customimageview.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;

/**
 * created by limingzhang on 2014/8/5
 *
 */
public class CircleRect extends Shape {
	private int outerRadiusRat;
	private float lightness = 1.0f;
	public CircleRect(int outerRadiusRat) {
		this.outerRadiusRat = outerRadiusRat;
	}
	@Override
	public Bitmap getBitmap(Bitmap bitmap) {
		if(lightness==1.0f&&outerRadiusRat==0){
			return bitmap;
		}
		if (lightness < 1.0f && lightness > 0.0f) {
			// 新建一个新的输出图片
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

			// 新建一个矩形
			RectF outerRect = new RectF(rect);

			// 产生一个红色的圆角矩形

			ColorMatrix cMatrix = new ColorMatrix();
			cMatrix.set(new float[] { lightness, 0, 0, 0, 0, 0, lightness, 0,
					0, 0,// 改变亮度
					0, 0, lightness, 0, 0, 0, 0, 0, 1, 0 });

			paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
			canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat,
					paint);
			// 将源图片绘制到这个圆角矩形上
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		} else {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			Paint paint = new Paint();
			final int color = 0xff424242;
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, outerRadiusRat, outerRadiusRat, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			return output;
		}
		
	}

	public void setLightness(float lightness) {
		this.lightness = lightness;
	}

}
