package com.atm.richtextview.test;

import android.app.Activity;
import android.os.Bundle;

import com.atm.richtextview.BaseSpannData;
import com.atm.richtextview.ImageSpannData;
import com.atm.richtextview.SpannedTextView;
import com.atm.richtextview.TextSpannData;
import com.atm.richtextview.UrlSpannData;
import com.example.main.R;

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
