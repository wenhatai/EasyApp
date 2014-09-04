package com.atm.photoselector.tool;

import java.util.ArrayList;

import com.atm.photoselector.show.ShowImageActivity;

import android.app.Activity;
import android.content.Intent;
/**
 * 
 * created by limingzhang on 2014/8/8
 * 用于给用户的类提供接口。
 */
public class GetSelectedPath {
   /**
    * 
    * @param data 
    * @return 选择的图片。
    */
	public static ArrayList<String> getSelectedPhoto(Intent data) {
		 
		return data.getStringArrayListExtra("imagePathList");
	}
  
    /**
     * 
     * @param activity  当前的Acitivity  
     * @param pathList  已经选择的图片的路径集合
     * @param selectNum 可以选择的最大图片数
     */
	public static void startActivity(Activity activity, ArrayList<String> pathList,
			int selectNum) {
		Intent intent = null;
		intent = new Intent(activity, ShowImageActivity.class);
		intent.putStringArrayListExtra("selectedList", pathList);
		intent.putExtra("selectNum", selectNum);
		activity.startActivityForResult(intent, ShowImageActivity.COMFIRMTO_FILELIST);
	}
}
