package com.atm.richtextview;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by zhangpengyu on 14-8-14.
 */
public class SpannedTextView extends TextView {

    private SpannableStringBuilder mSpannableBuilder;

    public SpannedTextView(Context context) {
        super(context);
    }

    public SpannedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpannedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setText(ArrayList<BaseSpannData> baseSpannDatas) {
        if (baseSpannDatas != null && baseSpannDatas.size() != 0) {
            mSpannableBuilder = new SpannableStringBuilder();
            int start = 0;
            for (BaseSpannData baseSpannData : baseSpannDatas) {
                if(!(baseSpannData instanceof TextSpannData)){
                    mSpannableBuilder.append(baseSpannData.getContent());
                }
                handleSpann(baseSpannData, start);
                start += baseSpannData.getLength();
            }
            super.setText(mSpannableBuilder);
        }
    }

    private void handleSpann(BaseSpannData baseSpannData, int start) {
        if (baseSpannData instanceof TextSpannData) {
            handleTextSpann((TextSpannData) baseSpannData, start);
        } else if (baseSpannData instanceof UrlSpannData) {
            setMovementMethod(LinkMovementMethod.getInstance());
            handleUrlSpann((UrlSpannData) baseSpannData, start);
        } else if (baseSpannData instanceof ImageSpannData) {
            handleImageSpann((ImageSpannData) baseSpannData, start);
        } else {
            handleBaseSpann(baseSpannData);
        }
    }

    private void handleTextSpann(TextSpannData textSpannData, int start) {
        int fontSize = textSpannData.getFontSize();
        if (textSpannData.getLeftDrawable() != 0) {
            mSpannableBuilder.append(textSpannData.getLeftDescription());
            int end = start + textSpannData.getLeftDescription().length();
            mSpannableBuilder.setSpan(new DrawableSpan(fontSize, textSpannData.getLeftDrawable()),
                    start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }
        mSpannableBuilder.append(textSpannData.getContent());
        int end = start + textSpannData.getContent().length();
        //如果没有设置字体的大小。那么就取当前字体大小
        if (fontSize != 0) {
            mSpannableBuilder.setSpan(new AbsoluteSizeSpan(fontSize, true),
                    start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            fontSize = (int) getTextSize();
        }
        if (textSpannData.getFontColor() != 0) {
            mSpannableBuilder.setSpan(new ForegroundColorSpan(textSpannData.getFontColor())
                    , start, end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (textSpannData.getRightDrawable() != 0) {
            mSpannableBuilder.append(textSpannData.getRightDescription());
            start = end;
            end = start+textSpannData.getRightDescription().length();
            mSpannableBuilder.setSpan(new DrawableSpan(fontSize, textSpannData.getRightDrawable()),
                    start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }

    private void handleUrlSpann(UrlSpannData urlSpann, int start) {
        if (TextUtils.isEmpty(urlSpann.getUrl())) {
            return;
        }
        int end = start+urlSpann.getLength();
        switch (urlSpann.getFlag()) {
            case UrlSpannData.LocalFlag:
                mSpannableBuilder.setSpan(new UrlLocalSpan(urlSpann),
                        start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case UrlSpannData.WebViewFlag:
                mSpannableBuilder.setSpan(new UrlWebSpann(urlSpann),
                        start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case UrlSpannData.BrowserFlag:
                mSpannableBuilder.setSpan(new URLSpan(urlSpann.getUrl()),
                        start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            default:break;
        }
    }

    private void handleImageSpann(ImageSpannData imageSpann, int start) {
        int end = start+imageSpann.getLength();
        mSpannableBuilder.setSpan(new ImageSpan(getContext(),imageSpann.getBitmap()),
                start,end,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void handleBaseSpann(BaseSpannData baseSpannData) {
    }

}
