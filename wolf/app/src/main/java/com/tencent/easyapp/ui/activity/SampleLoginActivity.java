package com.tencent.easyapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.tencent.easyapp.R;
import com.tencent.easyapp.constant.EappConstant;
import com.tencent.easyapp.helper.EappToastUtil;
import com.tencent.easyapp.ui.common.SampleBaseActivity;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by parrzhang on 2014/7/21.
 */
public class SampleLoginActivity extends SampleBaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_login);
        findViewById(R.id.connect_qq).setOnClickListener(this);
        findViewById(R.id.connect_weibo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.connect_qq:
                getTencent().login(this, EappConstant.QQScope, new BaseUiListener());
                break;
            case R.id.connect_weibo:

                break;
            default:

                break;
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            EappToastUtil.easyToast("qq授权成功");
        }


        @Override
        public void onError(UiError e) {
            EappToastUtil.easyToast("Error:" + e.errorMessage);
        }

        @Override
        public void onCancel() {
            EappToastUtil.easyToast("您已取消qq登录");
        }
    }
}
