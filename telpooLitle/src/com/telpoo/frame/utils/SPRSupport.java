package com.telpoo.frame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPRSupport {

	public static void save(String key, Object value, Context context) {

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();

		if (value instanceof String)
			editor.putString(key, (String) value);
		if (value instanceof Long)
			editor.putLong(key, (Long) value);
		if (value instanceof Integer)
			editor.putInt(key, (Integer) value);
		if (value instanceof Boolean)
			editor.putBoolean(key, (Boolean) value);
		if (value instanceof Float)
			editor.putFloat(key, (Float) value);

		editor.commit();

	}

	public static int getInt(String key, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getInt(key, 0);
	}
    public static int getAsInt(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
       String k= preferences.getString(key, Cons.Defi.SPR_GET_FALL);
        int a=Integer.MIN_VALUE;
        try {
            a= Integer.parseInt(k);
        }
        catch (Exception e){

        };

        return a;
    }

	public static String getString(String key, Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, Cons.Defi.SPR_GET_FALL);
	}

}
