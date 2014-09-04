package com.atm.photoselector.tool;

import java.util.ArrayList;
import java.util.List;

import com.atm.photoselector.bean.SQLThumbnailBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * created by limingzhang on 2014//8/31
 *
 */
public class PhotoSelectorDao {
	private PhotoSqliteHelper psHelper;

	public PhotoSelectorDao(Context context) {
		this.psHelper = new PhotoSqliteHelper(context);
	}

	public void add(SQLThumbnailBean stbBean) {
		SQLiteDatabase sdb = psHelper.getWritableDatabase();
		sdb.execSQL(
				"insert into thumandsrc (thumbnailpath,srcpath,fathername,createtime) values (?,?,?,?)",
				new Object[] { stbBean.getThumbnailPath(),
								stbBean.getSrcPaht(), 
								stbBean.getfatherFoldName(),
								stbBean.getCreateTime()});
		sdb.close();
	}

	public void addAll(List<SQLThumbnailBean> stbList) {
		SQLiteDatabase sdb = psHelper.getWritableDatabase();
		for(SQLThumbnailBean stbBean : stbList){
			sdb.execSQL(
					"insert into thumandsrc (thumbnailpath,srcpath,fathername,createtime) values (?,?,?,?)",
					new Object[] { stbBean.getThumbnailPath(),
									stbBean.getSrcPaht(), 
									stbBean.getfatherFoldName(),
									stbBean.getCreateTime()});
		}
		sdb.close();
	}
	public void delete(int id){
		SQLiteDatabase sdb = psHelper.getWritableDatabase();
		sdb.execSQL("delete from thumandsrc person where id = ?",new Object[]{id});
		sdb.close();
	}
	public List<SQLThumbnailBean> findAll(){
		SQLiteDatabase sdb = psHelper.getReadableDatabase();
		Cursor cursor = sdb.rawQuery("select * from thumandsrc", null);
		List<SQLThumbnailBean> stbList = new ArrayList<SQLThumbnailBean>();
		while(cursor.moveToNext()){
			SQLThumbnailBean sqlBean = new SQLThumbnailBean();
			sqlBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
			sqlBean.setThumbnailPath(cursor.getString(cursor.getColumnIndex("thumbnailpath")));
			sqlBean.setSrcPaht(cursor.getString(cursor.getColumnIndex("srcpath")));
			sqlBean.setfatherFoldName(cursor.getString(cursor.getColumnIndex("fathername")));
			sqlBean.setCreateTime(cursor.getLong(cursor.getColumnIndex("createtime")));
			stbList.add(sqlBean);
		}
		cursor.close();
		sdb.close();
	   return stbList;
	}
}
