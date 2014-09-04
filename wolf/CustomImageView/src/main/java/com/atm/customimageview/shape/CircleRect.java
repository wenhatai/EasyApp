package com.atm.customimageview.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
/**
 *created by limingzhang on 2014/8/5
 *
 */
public class CircleRect extends Shape{
    private int outerRadiusRat;
    private float lightness = 1.0f;
    public CircleRect(int outerRadiusRat){
    this.outerRadiusRat = outerRadiusRat;	
    }
    
	@Override
	public Bitmap getBitmap(int width, int height) {
		// TODO Auto-generated method stub
		 Bitmap bitmap = Bitmap.createBitmap(width, height,
	                Config.ARGB_8888);
	        Canvas canvas = new Canvas(bitmap);
	        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        paint.setColor(Color.BLACK);
	        canvas.drawRoundRect(new RectF(0.0f, 0.0f, width, height),outerRadiusRat, outerRadiusRat, paint);
	        return bitmap;
	}

	@Override
	public Bitmap getBitmap(Bitmap bitmap) {
        // 新建一个新的输出图片  
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output); 
        final Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        // 新建一个矩形  
        RectF outerRect = new RectF(rect); 
 
        // 产生一个红色的圆角矩形  
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); 
        
        ColorMatrix cMatrix = new ColorMatrix();  
        cMatrix.set(new float[] { lightness,0, 0, 0,0,
        						    0,lightness, 0, 0,0,// 改变亮度   
                                    0, 0, lightness, 0, 0,
                                    0, 0, 0, 1, 0 });  

        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));  
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint); 
        // 将源图片绘制到这个圆角矩形上  
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); 
        canvas.drawBitmap(bitmap, rect, rect, paint);
       // canvas.drawARGB(150, 0, 0, 0);
        
        
        return output;  
	}


	public void setLightness(float lightness) {
		this.lightness = lightness;
	}
    

}
