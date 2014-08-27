package com.tencent.easyapp.ui.activity;

import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.tencent.easyapp.R;
import com.tencent.easyapp.ui.common.EappBaseActivity;
import com.tencent.easyapp.ui.fragment.SampleGridFragment;

/**
 * Created by parrzhang on 2014/7/16.
 */
public class SampleGridActivity extends EappBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_grid);
        SampleGridFragment sampleGridFragment = new SampleGridFragment();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentlayout,sampleGridFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
