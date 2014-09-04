package com.atm.custompopupwindow.list;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.atm.custompopupwindow.R;

public class PopupListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<String> list;

	public PopupListAdapter(Context context, List<String> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView btn = null;
		if (list.size() == 1) {
			view = inflater.inflate(R.layout.popuplistwindow_item_one, null);
			btn = (TextView) view.findViewById(R.id.itembtn);
		} else if (position == 0) {
			view = inflater.inflate(R.layout.popuplistwindow_item_top, null);
			btn = (TextView) view.findViewById(R.id.itembtn);
		} else if (position == (list.size() - 1)) {
			view = inflater.inflate(R.layout.popuplistwindow_item_bottom, null);
			btn = (TextView) view.findViewById(R.id.itembtn);
		} else{
			view = inflater.inflate(R.layout.popuplistwindow_item_center, null);
			btn = (TextView) view.findViewById(R.id.itembtn);
		}
		btn.setText(list.get(position));
		return view;
	}
}
