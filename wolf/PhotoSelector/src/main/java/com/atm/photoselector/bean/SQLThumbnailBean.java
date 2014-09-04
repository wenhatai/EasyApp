package com.atm.photoselector.bean;
/**
 * 
 * created by limingzhang on 2014//8/31
 *
 */
public class SQLThumbnailBean {
	/**
	 * 该缩略图在数据库中的id
	 */
	private int id;
	/**
	 * 缩略图的路径
	 */
	private String thumbnailPath;
	/**
	 * 原图片的路径
	 */
	private String srcPaht;
	/**
	 * 父文件夹的名字
	 */
	private String fatherFoldName;
    /**
     * 文件的创建时间
     */
	private Long    createTime;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getSrcPaht() {
		return srcPaht;
	}

	public void setSrcPaht(String srcPaht) {
		this.srcPaht = srcPaht;
	}

	public String getfatherFoldName() {
		return fatherFoldName;
	}

	public void setfatherFoldName(String fatherFoldName) {
		this.fatherFoldName = fatherFoldName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

}
