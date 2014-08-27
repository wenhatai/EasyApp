package com.tencent.easyapp.module.spann;


import android.support.annotation.NonNull;

/**
 * Created by zhangpengyu on 14-8-14.
 * 富文本基类
 */
public class BaseSpannData {

    public BaseSpannData(String content){
        this.content = content;
    }

    /**
     * 必须参数
     */
    @NonNull
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getLength(){
        return content.length();
    }

}
