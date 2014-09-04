package com.tencent.easyapp.test;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tencent.easyapp.ui.activity.SampleLoginActivity;


/**
 * Created by zhangpengyu on 14-8-27.
 * 展示测试模块
 */
public class SampleListActivity extends ListActivity {
    public static final String[] SampleLists = new String[]{"富文本", "登录"};
    public static final Class<?>[] ActivityLists = new Class[]{SampleSpannedActivity.class, SampleLoginActivity.class};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_activated_1, SampleLists);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ActivityLists[position]);
        startActivity(intent);
    }
}
