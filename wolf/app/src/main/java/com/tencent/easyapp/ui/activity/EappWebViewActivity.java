package com.tencent.easyapp.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tencent.easyapp.ui.common.EappBaseActivity;

/**
 * Created by zhangpengyu on 14-8-27.
 */
public class EappWebViewActivity extends EappBaseActivity {
    private WebView mWebView;
    private String mTitle;

    public static final String Title = "Title";
    public static final String Url = "Url";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mTitle = getIntent().getExtras().getString(Title);
        String url = getIntent().getExtras().getString(Url);
        mWebView = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new EappWebViewClient());
        mWebView.loadUrl(url);
        setContentView(mWebView);
    }


    private class EappWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Return false so the WebView loads the url
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }

}
