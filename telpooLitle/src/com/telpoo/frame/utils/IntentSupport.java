package com.telpoo.frame.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

public class IntentSupport {
	private static String TAG= IntentSupport.class.getSimpleName();

	public static void openWeb(String url, Context ct) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		ct.startActivity(browserIntent);
	}

	public static boolean cropImage(Uri picUri, int requsetCode, Activity activity) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			cropIntent.setDataAndType(picUri, "image/*");
			cropIntent.putExtra("crop", "true");
			// cropIntent.putExtra("aspectX", 1);
			// cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("outputX", 200);
			cropIntent.putExtra("outputY", 200);
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("return-data", true);
			cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

			activity.startActivityForResult(cropIntent, requsetCode);
			return true;
		} catch (ActivityNotFoundException anfe) {
			Mlog.E(TAG + " - " + anfe);
			return false;
		}
	}

	public static Bitmap getIntentCropImage(Intent data) {

		if (data != null) {
			Bundle extras = data.getExtras();
			Bitmap selectedBitmap = extras.getParcelable("data");

			return selectedBitmap;
		}
		return null;

	}
	
	public static void call(Context ct,String phone){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
	    callIntent.setData(Uri.parse("tel:"+phone));
	    ct.startActivity(callIntent);
	}
	
	public static void sendMail(String adress,String subject,String msg, Context ct){
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] {adress});
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, msg);
		email.setType("message/rfc822");
		ct.startActivity(Intent.createChooser(email, "Select mail client"));
	}
	
	public static void openGooglePlay(Activity activity) {
		String id = activity.getApplicationContext().getPackageName();
		try {
			Uri marketUri = Uri.parse("market://details?id=" + id);
			Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(marketUri);
			activity.startActivity(marketIntent);
		} catch (Exception e) {
			Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + id));
			activity.startActivity(marketIntent);
		}
	}


    public static String getRealPathFromURI(Context context,Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
