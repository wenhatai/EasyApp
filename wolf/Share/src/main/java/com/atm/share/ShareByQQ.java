package com.atm.share;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
/**
 * 
 * @author limingzhang
 * created on 2014/8/25
 *
 */
public class ShareByQQ {
	private ShareBean shareBean;
	private Bundle bundle;
	private int mExtarFlag = 0x00;
	
	private Activity activity;
	private Tencent mTencent;
	/**
	 * 
	 * @param context   当前Activity的Context
	 * @param appId     兴趣部落QQ分享的APPID
	 * @param shareBean 分享的实体类
	 */
	public ShareByQQ(Context context,String appId,ShareBean shareBean) {
		
		this.shareBean = shareBean;
		this.activity  = (Activity)context;
		mTencent = Tencent.createInstance(appId, context);
	}
	/**
	 * 调用即可分享给QQ好友
	 */
	public void shareToQQFriends(){
		bundle = new Bundle();
		bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareBean.getTitle());
		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareBean.getTargetUrl());
		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareBean.getSummary());
		bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareBean.getImageUrl());
		bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareBean.getAppName());
		bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);
		new Thread(new Runnable() {
	            
	            @Override
	            public void run() {
	                // TODO Auto-generated method stub
	            	mTencent.shareToQQ(activity, bundle, new IUiListener() {

	                    @Override
	                    public void onCancel() {
	                    }

	                    @Override
	                    public void onComplete(Object response) {
	                        // TODO Auto-generated method stub
	                    }

	                    @Override
	                    public void onError(UiError e) {
	                        // TODO Auto-generated method stub
	                    }

	                });
	            }
	        }).start();
	}
	/**
	 * 调用即可分享到QQ空间
	 */
	public void shareToQzone () {
		bundle = new Bundle();
		ArrayList<String> imageList = new ArrayList<String>();
		imageList.add(shareBean.getImageUrl());
		bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
		bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareBean.getTitle());//必填
		bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareBean.getTargetUrl());
		bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareBean.getSummary());
		bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,imageList);
		bundle.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, shareBean.getAppName());
		mTencent.shareToQzone(activity, bundle, new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Object arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		}
}
