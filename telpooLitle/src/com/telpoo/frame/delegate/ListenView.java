package com.telpoo.frame.delegate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class ListenView {
	@SuppressLint("NewApi") 
	public static void getSizeView(final View v,final Idelegate idelegate){
		final ViewTreeObserver treo = v.getViewTreeObserver();
		treo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				
				idelegate.callBack( v.getWidth(),0);
				idelegate.callBack( v.getHeight(),1);
				
				
				if (Build.VERSION.SDK_INT<16) {
					v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else { 
                	v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } 
				
			}
		});
	}

}
