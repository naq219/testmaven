package com.telpoo.frame.utils;

import android.app.Activity;
import android.graphics.PorterDuff;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
 * @author: Nguyen Anh Que
 */
public class ViewUtils {

	public static void HighlightView(final View[] imageViews, final Integer colorClick, final Integer color, final Integer drawable) {

		for (final View imageView : imageViews) {

			OnTouchListener listener = new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent motionEvent) {

					if (imageView != null) {
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

							imageView.getBackground().setColorFilter(color, PorterDuff.Mode.LIGHTEN);

						} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							if(color!=null)
							imageView.getBackground().setColorFilter(color, PorterDuff.Mode.CLEAR); 
							else 
								imageView.setBackgroundResource(drawable);
																												
						}
					} else {

					}
					return false;

				}
			};

			imageView.setClickable(true);
			imageView.setOnTouchListener(listener);
		}

	}

	public static void HighlightImageView(final ImageView[] imageViews, int color) {
		final int TRANSPARENT_GREY = Color.argb(0, 185, 185, 185);
		final int FILTERED_GREY = color;// = Color.argb(200, 42, 18, 191);
		// final ImageView imageView = null;

		for (final ImageView imageView : imageViews) {
			OnTouchListener listener = new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent motionEvent) {

					if (imageView != null) {
						if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
							imageView.setColorFilter(FILTERED_GREY);
						} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
							imageView.setColorFilter(TRANSPARENT_GREY); // or
																		// null
						}
					} else {

					}
					return false;

				}
			};

			imageView.setClickable(true);
			imageView.setOnTouchListener(listener);
		}

	}

	public static void HideKeyboarOutside(View view, final Activity activity) {

		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {

			view.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					Utils.hideSoftKeyboard(activity);
					return false;
				}

			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {

			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

				View innerView = ((ViewGroup) view).getChildAt(i);

				HideKeyboarOutside(innerView, activity);
			}
		}
	}

	public static void loadDataWv(WebView wv, String data) {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		wv.loadDataWithBaseURL(null, header + data, "text/html", "charset=UTF-8", null);
	}

	public static Integer[] getWHCustomView(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = 100;
		int desiredHeight = 100;

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			width = Math.min(desiredWidth, widthSize);
		} else {
			width = desiredWidth;
		}

		int height;
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			height = Math.min(desiredHeight, heightSize);
		} else {
			height = desiredHeight;
		}

		Integer[] res = { width, height };
		return res;

	}
	
	

}
