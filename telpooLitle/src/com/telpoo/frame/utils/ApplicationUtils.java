package com.telpoo.frame.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;

public class ApplicationUtils {
	static String TAG = ApplicationUtils.class.getSimpleName();

	public static String getKeyHash(Activity ac) {
		String re = null;
		try {
			PackageInfo info = ac.getPackageManager().getPackageInfo(ac.getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				re = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Mlog.D("key hash:" + re);
			}
		} catch (NameNotFoundException e) {
			Mlog.E(TAG + " - getKeyHash - NameNotFoundException " + e);

		} catch (NoSuchAlgorithmException e) {
			Mlog.E(TAG + " - getKeyHash - NoSuchAlgorithmException " + e);
		}

		return re;
	}

	public static boolean checkFirstStart(Context ct) {
		String value = Utils.getStringSPR("application fist start", ct);

		if (!value.equalsIgnoreCase("started"))
			return true;

		return false;
	}

	public static void saveFirstStart(Context ct) {
		Utils.saveStringSPR("application fist start", "started", ct);
	}

}
