package com.telpoo.frame.utils;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telpoo.frame.object.BaseObject;

public class JsonSupport {
	private static String TAG = JsonSupport.class.getSimpleName();

	@Deprecated
	/**
	 * thừa trường keys
	 * @param baseObject
	 * @param keys
	 * @return
	 */
	protected static String BaseObject2Json(BaseObject baseObject, String[] keys) {
		JSONObject jSend = new JSONObject();
		try {
			for (String value : keys) {
				if (baseObject.get(value) != null)
					jSend.put(value, baseObject.get(value));

			}

		} catch (Exception e) {
			Mlog.E(TAG + "-putObjectToJson =6523f3= " + e);
			return "";
		}
		return jSend.toString();
	}

	@Deprecated
	/**
	 * thừa trường keys
	 * @param value
	 * @param keys
	 * @return
	 */
	public static BaseObject Json2BaseObject(String value, String[] keys) {
		JSONObject jsonObject;
		BaseObject baseObject = new BaseObject();
		try {
			jsonObject = new JSONObject(value);
			for (String key : keys) {
				if (jsonObject.has(key)) {

					String valuekey = jsonObject.getString(key);
					baseObject.set(key, valuekey);
				}

			}
		} catch (JSONException e) {
			Mlog.E("=672345=parseJsonToBaseObject=" + e);
			e.printStackTrace();
		}
		return baseObject;
	}

	/**
	 * chuyển từ jsonobject sang object kiểu {@link BaseObject}
	 * 
	 * @param joj
	 * @return
	 * @throws JSONException
	 */
	public static BaseObject jsonObject2BaseOj(JSONObject joj) throws JSONException {
		Iterator<?> keys = joj.keys();
		BaseObject oj = new BaseObject();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			oj.set(key, joj.getString(key));

		}

		return oj;

	}

	/**
	 * chuyển từ jsonobject sang object kiểu {@link BaseObject}
	 * 
	 * @param joj
	 * @return
	 * @throws JSONException
	 */
	public static BaseObject jsonObject2BaseOj(String strjoj) throws JSONException {
		JSONObject joj = new JSONObject(strjoj);
		return jsonObject2BaseOj(joj);

	}

	public static ArrayList<BaseObject> jsonArray2BaseOj(String jarr) throws JSONException {
		JSONArray arr = new JSONArray(jarr);
		return jsonArray2BaseOj(arr);
	}

	public static ArrayList<BaseObject> jsonArray2BaseOj(JSONArray jarr) throws JSONException {
		ArrayList<BaseObject> ojs = new ArrayList<BaseObject>();
		for (int i = 0; i < jarr.length(); i++) {
			BaseObject oj = jsonObject2BaseOj(jarr.getJSONObject(i));
			ojs.add(oj);
		}
		return ojs;
	}

}
