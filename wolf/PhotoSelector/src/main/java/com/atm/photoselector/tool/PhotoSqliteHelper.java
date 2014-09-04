package com.atm.photoselector.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * created by limingzhang on 2014//8/31
 *
 */
public class PhotoSqliteHelper extends SQLiteOpenHelper {

	public PhotoSqliteHelper(Context context) {
		super(context, "photoseletor.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table thumandsrc (id integer primary key autoincrement,"
				+ "thumbnailpath varchar(20),srcpath varchar(20),"
				+ "fathername varchar(20),createtime Long)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
