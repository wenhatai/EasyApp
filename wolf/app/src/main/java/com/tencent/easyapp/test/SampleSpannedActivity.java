package com.tencent.easyapp.test;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.easyapp.R;
import com.tencent.easyapp.module.spann.BaseSpannData;
import com.tencent.easyapp.module.spann.ImageSpannData;
import com.tencent.easyapp.module.spann.TextSpannData;
import com.tencent.easyapp.module.spann.UrlSpannData;
import com.tencent.easyapp.ui.view.SpannedTextView;

import java.util.ArrayList;

/**
 * Created by zhangpengyu on 14-8-27.
 */
public class SampleSpannedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity_spann);
        SpannedTextView imageSpann = (SpannedTextView)findViewById(R.id.sample_spanntext1);
        SpannedTextView textSpann = (SpannedTextView)findViewById(R.id.sample_spanntext2);
        imageSpann.setText(initImageSpann());
        textSpann.setText(initTextSpann());
    }

    private ArrayList<BaseSpannData> initImageSpann(){
        ArrayList<BaseSpannData> baseSpannDatas = new ArrayList<BaseSpannData>();
        baseSpannDatas.add(new BaseSpannData("这是图文混排"));
        ImageSpannData imageSpannData = new ImageSpannData("earth");
        imageSpannData.setImageRes(R.drawable.sample_earth);
        imageSpannData.setImageHeight(100);
        imageSpannData.setImageWidth(100);
        baseSpannDatas.add(imageSpannData);
        UrlSpannData urlSpannData = new UrlSpannData("alloyteam");
        urlSpannData.setFlag(UrlSpannData.WebViewFlag);
        urlSpannData.setUrl("http://www.alloyteam.com/");
        baseSpannDatas.add(urlSpannData);
        return baseSpannDatas;
    }

    private ArrayList<BaseSpannData> initTextSpann(){
        ArrayList<BaseSpannData> baseSpannDatas = new ArrayList<BaseSpannData>();
        TextSpannData replySapnnData = new TextSpannData("allen");
        replySapnnData.setFontColor(getResources().getColor(android.R.color.holo_blue_light));
        baseSpannDatas.add(replySapnnData);
        baseSpannDatas.add(new BaseSpannData("回复"));
        TextSpannData replyedSpannData = new TextSpannData("herry");
        replyedSpannData.setFontColor(getResources().getColor(android.R.color.holo_red_light));
        replyedSpannData.setFontSize(20);
        baseSpannDatas.add(replyedSpannData);
        return baseSpannDatas;
    }
}
