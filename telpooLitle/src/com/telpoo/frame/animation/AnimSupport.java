package com.telpoo.frame.animation;

import android.view.View;

public class AnimSupport {
	
	public static void setVisiView(View v,boolean isEnable){
		v.startAnimation(new MyScaler(1.0f, 1.0f, 1.0f, 0.0f, 500, v, isEnable));
	}

	

}
