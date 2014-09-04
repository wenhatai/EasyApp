package com.atm.richtextview;

import android.text.TextUtils;

/**
 * Created by zhangpengyu on 14-8-14.
 * 富文本 文本类，定义颜色，字体大小，左边图片，右边图片
 */
public class TextSpannData extends BaseSpannData{
    private int fontColor;
    private int fontSize;
    private int leftDrawable;
    private String leftDescription;
    private int rightDrawable;
    private String rightDescription;

    public TextSpannData(String content) {
        super(content);
    }


    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getLeftDrawable() {
        return leftDrawable;
    }

    public void setLeftDrawable(int leftDrawable,String leftDescription) {
        this.leftDrawable = leftDrawable;
        this.leftDescription = leftDescription;
    }

    public int getRightDrawable() {
        return rightDrawable;
    }

    public void setRightDrawable(int rightDrawable,String rightDescription) {
        this.rightDrawable = rightDrawable;
        this.rightDescription = rightDescription;
    }

    public String getLeftDescription() {
        return leftDescription;
    }

    public String getRightDescription() {
        return rightDescription;
    }

    public int getLength(){
        int length = super.getLength();
        if(!TextUtils.isEmpty(leftDescription)){
            length += leftDescription.length();
        }
        if(!TextUtils.isEmpty(rightDescription)){
            length += rightDescription.length();
        }
        return length;
    }
}
