package com.telpoo.frame.ui;

import android.content.Context;
import android.widget.Toast;

public abstract class TemplateUi {
	
	public void showProgressDialog(Context context, String message) {
		Toast.makeText(context, message, 1).show();
	}

}
