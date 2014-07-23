package com.tencent.easyapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
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
    private SsoHandler mSsoHandler;
    /** 微博 Web 授权类，提供登陆等功能  */
    private WeiboAuth mWeiboAuth;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_login);
        findViewById(R.id.connect_qq).setOnClickListener(this);
        findViewById(R.id.connect_weibo).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                makeLogin();
                break;
            case R.id.connect_qq:
                getTencent().login(this, EappConstant.QQScope, new BaseUiListener());
                break;
            case R.id.connect_weibo:
                if(mWeiboAuth == null){
                    mWeiboAuth = new WeiboAuth(this,EappConstant.WeiBoAppKey,EappConstant.WeiBoRedirectUrl,
                            EappConstant.WeiBoScope);
                }
                if(mSsoHandler == null){
                    mSsoHandler = new SsoHandler(this,mWeiboAuth);
                }
                mSsoHandler.authorize(new AuthListener());
                break;
            default:
                break;
        }
    }

    private void makeLogin(){
        Intent intent = new Intent(this,SampleGridActivity.class);
        startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                EappToastUtil.easyToast("授权成功");
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "auth failed ";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                EappToastUtil.easyToast(message);
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(SampleLoginActivity.this,
                    "auth cancle", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(SampleLoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
