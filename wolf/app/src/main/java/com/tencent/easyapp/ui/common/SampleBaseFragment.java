package com.tencent.easyapp.ui.common;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * Created by parrzhang on 2014/7/23.
 */
public class SampleBaseFragment extends SherlockFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected EappBaseActivity getBaseActivity(){
        if(getActivity() instanceof EappBaseActivity){
            return (EappBaseActivity)getActivity();
        }
        return  null;
    }
}
