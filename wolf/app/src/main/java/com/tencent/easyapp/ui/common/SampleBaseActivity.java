package com.tencent.easyapp.ui.common;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.tencent.easyapp.DaoMaster;
import com.tencent.easyapp.DaoSession;
import com.tencent.easyapp.EappData;
import com.tencent.easyapp.constant.EappConstant;
import com.tencent.tauth.Tencent;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleBaseActivity extends Activity {
    private static Tencent mTencent;
    private static DaoSession mDaoSession;

    public static Tencent getTencent() {
        if(mTencent == null){
            mTencent = Tencent.createInstance(EappConstant.QQApiID, EappData.getContext());
        }
        return mTencent;
    }

    public static DaoSession getDaoSession() {
        if(mDaoSession == null){
            DaoMaster.DevOpenHelper helper =
                    new DaoMaster.DevOpenHelper(EappData.getContext(),"easyapp.db",null);
            SQLiteDatabase db = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            mDaoSession = daoMaster.newSession();
        }
        return mDaoSession;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
