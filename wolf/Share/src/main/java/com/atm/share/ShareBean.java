package com.atm.share;

/**
 * 
 * @author limingzhang created on 2014/8/22
 */
public class ShareBean {
	/**
	 * app名字
	 */
	private String appName;
	/**
	 * 标题
	 */
	private String title ;
	/**
	 * 应用链接
	 * 默认为http://qun.qq.com/tribe.html
	 */
	private String targetUrl =  "http://qun.qq.com/tribe.html";
	/**
	 * 概要
	 */
	private String summary;

	/**
	 * 图片链接
	 */

	private String imageUrl;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
