package com.atm.photoselector.bean;

import java.io.Serializable;

/**
 * 
 * created by limingzhang on 2014/8/8
 *
 */
public class ImageBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文件夹的第一张图片的路径
	 */
	private String topImagePath;
	/**
	 * 文件夹名
	 */
	private String folderName; 
	/**
	 * 文件夹中的图片数
	 */
	private int imageCounts;
	
	public String getTopImagePath() {
		return topImagePath;
	}
	public void setTopImagePath(String topImagePath) {
		this.topImagePath = topImagePath;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getImageCounts() {
		return imageCounts;
	}
	public void setImageCounts(int imageCounts) {
		this.imageCounts = imageCounts;
	}


}
