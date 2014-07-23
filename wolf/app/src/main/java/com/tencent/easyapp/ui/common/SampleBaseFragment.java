package com.tencent.easyapp.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by parrzhang on 2014/7/23.
 */
public class SampleBaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected SampleBaseActivity getBaseActivity(){
        if(getActivity() instanceof SampleBaseActivity){
            return (SampleBaseActivity)getActivity();
        }
        return  null;
    }
}
