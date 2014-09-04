package com.atm.locationselector.fixedposition;

import java.util.List;

import com.atm.locationselector.R;
import com.atm.locationselector.bean.PoiBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * created by limingzhang on 2014/8/10
 *
 */
public class PoiListAdapter extends BaseAdapter{
    
	private List<PoiBean> listPoiBean;
	private LayoutInflater inflater;
	public PoiListAdapter(List<PoiBean> listPoiBean, Context context){
		this.listPoiBean = listPoiBean;
		inflater = LayoutInflater.from(context);
	}
	@Override
	
	public int getCount() {
		// TODO Auto-generated method stub
		return listPoiBean.size();
	}
	public void setListPoiItem(List<PoiBean> listPoiBean) {
		this.listPoiBean = listPoiBean;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listPoiBean.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder;
		if(view == null){
			viewHolder = new ViewHolder();
			view = inflater.inflate(R.layout.listitem_poi,null);
			viewHolder.poiName = (TextView)view.findViewById(R.id.poiname);
			viewHolder.poiDeteal=(TextView)view.findViewById(R.id.deteal);
			viewHolder.sureImage = (ImageView)view.findViewById(R.id.sure);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)view.getTag();
		}
		// TODO Auto-generated method stub
		viewHolder.poiName.setText(listPoiBean.get(position).getPoiName());
		viewHolder.poiDeteal.setText(listPoiBean.get(position).getPoiAddress());
		if(listPoiBean.get(position).isSelected()){
			viewHolder.sureImage.setImageResource(R.drawable.location_sure);
			viewHolder.poiName.setTextColor(Color.BLUE);
			viewHolder.poiDeteal.setTextColor(Color.BLUE);
		}else{
			viewHolder.sureImage.setImageResource(0);
			viewHolder.poiName.setTextColor(Color.BLACK);
			viewHolder.poiDeteal.setTextColor(Color.argb(255, 153, 153, 153));
		}
		return view;
	}
}
class ViewHolder{
	TextView poiName;
	TextView poiDeteal;
	ImageView sureImage;
}
