package com.telpoo.frame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeUtils {
	public static Long getTimeMillis() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	public static String cal2String(Calendar cal, String format) {
		String format1 = format.toLowerCase();

		String res = format1.replaceAll("dd", "" + daylength2(cal.get(Calendar.DAY_OF_MONTH)));
		res = res.replaceAll("mm", "" + monthlength2((cal.get(Calendar.MONTH) + 1)));
		if (format.contains("yyyy"))
			res = res.replaceAll("yyyy", "" + cal.get(Calendar.YEAR));

		if (format.contains("yy"))
			res = res.replaceAll("yy", ("" + cal.get(Calendar.YEAR)).substring(2));
		return res;
	}
	
	public static Calendar String2Calendar(String value,String format){
		if(value==null){
			Mlog.E("String2Calendar- null value");
			return null;
		}
		 Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat(format);
		    try {
				cal.setTime(sdf.parse(value));
				return cal;
			} catch (ParseException e) {
				Mlog.E("String2Calendar - "+e);
				return null;
			}
		    
		    
	}

	private static String monthlength2(int i) {
		if(i<10) return "0"+i;
		return i+"";
	}

	private static String daylength2(int i) {
		if(i<10) return "0"+i;
		return i+"";
	}

}
