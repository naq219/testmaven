package com.telpoo.frame.adapter;

import java.util.ArrayList;
import java.util.List;

import com.telpoo.frame.object.BaseObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

public class TelpooAdapter extends ArrayAdapter<BaseObject>{
	protected Context context;
	protected int resource;
	protected ArrayList<BaseObject> ojs;
	protected LayoutInflater inflater;

	public TelpooAdapter(Context context, int resource, ArrayList<BaseObject> objects) {
		super(context, resource, objects);
		this.context = context;
		this.resource = resource;
		this.ojs = objects;
		this.inflater = LayoutInflater.from(context);
	}
	
	public void Adds(List<BaseObject> items) {
		if (items != null) {
			for (BaseObject item : items) {
				add(item);
			}
		}
	}
	
	@Override
	public BaseObject getItem(int position) {
		return ojs.get(position);
	}

	public void setData(List<BaseObject> items) {
		clear();
		Adds(items);

	}
	
	public ArrayList<BaseObject> getAll() {
		return ojs;
	}
}
