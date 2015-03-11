/**
 * 
 */
package com.telpoo.frame.object;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * @author NAQ219
 * 
 */
public class BaseObject implements Parcelable {

	protected ContentValues params = null;

	public ArrayList<String> getKeys(){
		if(params==null) return null;
		
		Iterator<Entry<String, Object>> interrator = params.valueSet().iterator();
		ArrayList<String> listKey=new ArrayList<String>();
		while (interrator.hasNext()) {
		listKey.add(interrator.next().getKey());
		}
		return listKey;
	}

    public BaseObject clone(){
        BaseObject ojNew=new BaseObject();

        if(params!=null){
            ContentValues par=new ContentValues(params);
            ojNew.setParams(par);
        }

        return ojNew;
    }

	public void setParams(ContentValues params) {
		this.params = params;
	}

	public ContentValues getParams() {
		return params;
	}

	public String get(String key) {
		if (params != null)
			return params.getAsString(key);
		else
			return null;
	}

	public void set(String key, String value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, value);

	}

	public void set(String key, int value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, "" + value);
	}

	public void set(String key, long value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, "" + value);
	}

	public boolean getBool(String key) {
		if (params != null) {
			return Boolean.parseBoolean(params.getAsString(key));

		} else
			return false;
	}

	public void set(String key, boolean value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, "" + value);

	}

	public Integer getInt(String key) {
		if (params != null)
			if (params.containsKey(key)) {
				Integer vl= params.getAsInteger(key);
				if(vl==null) return Integer.MIN_VALUE;
				return vl;
			}
		return Integer.MIN_VALUE;
	}
	
	
	

	public double getDouble(String key) {

		if (params != null) {

			if (params.containsKey(key)) {

				return Double.parseDouble((params.getAsString(key)));
			}
			else return Double.MIN_VALUE;
		}

		return Double.MIN_VALUE;

	}
	
	public Float getFloat(String key) {

		if (params != null) {

			if (params.containsKey(key)) {

				return Float.parseFloat((params.getAsString(key)));
			}
			else return Float.MIN_VALUE;
		}

		return Float.MIN_VALUE;

	}

	public long getLong(String key) {
		// nth
		if (params != null)
			if (params.containsKey(key) && params.getAsLong(key) != null)
				return params.getAsLong(key);
		return 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelable(this.params, i);

	}

	public BaseObject(Parcel parcel) {
		this.params = parcel.readParcelable(getClass().getClassLoader());
	}

	public BaseObject() {

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public BaseObject createFromParcel(Parcel in) {
			return new BaseObject(in);
		}

		public BaseObject[] newArray(int size) {
			return new BaseObject[size];
		}
	};

}
