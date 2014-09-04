package com.atm.share.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.atm.share.ShareBean;
import com.atm.share.ShareByQQ;
import com.example.main.R;

/**
 * Created by limingzhang on 2014/9/3.
 */
public class ShareActivity extends Activity {

    private Button shareToQQFriends;
    private Button shareToQQZone;
    private ShareByQQ shareByQQ;
    private String appId="1102291502";
    private ShareBean shareBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        shareBean = new ShareBean();
        shareToQQFriends = (Button)findViewById(R.id.share_qq_friends);
        shareToQQZone    = (Button)findViewById(R.id.share_qq_zone);
        shareBean.setAppName("MyAPPName");
        shareBean.setTitle("Happiness");
        shareBean.setImageUrl("http://img1.jgxfw.com/qqtouxiang/2013/11/04/21/212533-201311041310.jpg");
        shareBean.setSummary("谢谢关注");
        shareBean.setTargetUrl("http://qun.qq.com/tribe.html");
        shareByQQ = new ShareByQQ(ShareActivity.this,appId,shareBean);
        shareToQQFriends.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shareByQQ.shareToQQFriends();
            }
        });
        shareToQQZone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shareByQQ.shareToQzone();
            }
        });
    }
}
