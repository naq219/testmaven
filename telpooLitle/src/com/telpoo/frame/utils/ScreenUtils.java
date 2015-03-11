package com.telpoo.frame.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by naq on 10/22/13.
 */
public class ScreenUtils {
	public static int convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	@SuppressLint("NewApi")
	public static int getWindowsWidth(Activity ct) {
		int Measuredwidth = 0;

		WindowManager w = ct.getWindowManager();
		if (Build.VERSION.SDK_INT >= 13) {
			Point size = new Point();
			w.getDefaultDisplay().getSize(size);
			Measuredwidth = size.x;
		} else {
			Display d = w.getDefaultDisplay();
			Measuredwidth = d.getWidth();
		}

		return Measuredwidth;
	}

	@SuppressLint("NewApi")
	public static int getWindowsHeigh(Activity ct) {
		int Measuredheight = 0;

		WindowManager w = ct.getWindowManager();
		if (Build.VERSION.SDK_INT >= 13) {
			Point size = new Point();
			w.getDefaultDisplay().getSize(size);
			Measuredheight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			Measuredheight = d.getHeight();
		}

		return Measuredheight;
	}
}
