package com.atm.customimageview.show;
/**
 *  this class is from github, modifid by limingzhang on 2014/8/5
 */
public interface GifAction {

	/**
	 * @author liao
	 * gif解码观察者
	 * @param parseStatus 解码是否成功，成功会为true
	 * @param frameIndex 当前解码的第几帧，当全部解码成功后，这里为-1
	 */
	public void parseOk(boolean parseStatus, int frameIndex);
}
