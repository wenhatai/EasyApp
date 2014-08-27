package com.tencent.easyapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tencent.easyapp.R;
import com.tencent.easyapp.ui.common.EappBaseActivity;
import com.tencent.easyapp.ui.fragment.SamplePlanetFragment;

/**
 * Created by parrzhang on 2014/7/23.
 */
public class SampleTabActivity extends EappBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_tab);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.main_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getId()){
                    case R.id.tab1:
                    case R.id.tab2:
                    case R.id.tab3:
                    case R.id.tab4:
                        changeFragment(i);
                        break;
                    default:
                        break;
                }
            }
        });
        changeFragment(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.sample_more,menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_tab:
                Intent tabIntent = new Intent(this,SampleTabActivity.class);
                startActivity(tabIntent);
                break;
            case R.id.menu_drawlayout:
                Intent drawLayoutIntent = new Intent(this,SampDrawLayoutActivity.class);
                startActivity(drawLayoutIntent);
                break;
            default:
                break;
        }
        return  true;
    }

    private void changeFragment(int position){
        Fragment samplePlanetFragment = SamplePlanetFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content_frame, samplePlanetFragment).commit();
    }

}
