package com.telpoo.frame.animation;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeViewAnimation extends Animation {
	private View mView;
	private float mToHeight;
	private float mFromHeight;

	private float mToWidth;
	private float mFromWidth;
	
	/**
	 * animation đổi kích thức view và giữ nguyên khi hết.
	 * @param v
	 * @param fromWidth =9999: giữ nguyên kích thước
	 * @param fromHeight =9999: giữ nguyên kích thước
	 * @param toWidth =9999: giữ nguyên kích thước
	 * @param toHeight =9999: giữ nguyên kích thước
	 */
	public ResizeViewAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight) {
		mToHeight = toHeight;
		mToWidth = toWidth;
		mFromHeight = fromHeight;
		mFromWidth = fromWidth;

		if (fromWidth == 9999)
			mFromWidth = v.getWidth();
		if (toWidth == 9999)
			mToWidth = v.getWidth();
		if (fromHeight == 9999)
			mFromHeight = v.getHeight();
		if (toHeight == 9999)
			mToHeight = v.getHeight();

		mView = v;

		setDuration(300);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float height = (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
		float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
		LayoutParams p = mView.getLayoutParams();
		p.height = (int) height;
		p.width = (int) width;
		mView.requestLayout();
	}
}