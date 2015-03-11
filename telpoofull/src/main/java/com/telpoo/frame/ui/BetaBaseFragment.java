package com.telpoo.frame.ui;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;


import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.utils.Utils;
import com.telpoo.frame.utils.ViewUtils;
//import com.google.analytics.tracking.android.Fields;
//import com.google.analytics.tracking.android.GoogleAnalytics;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.Tracker;

public class BetaBaseFragment extends Fragment implements ModelListener {
	protected String TAG = getClass().getSimpleName();
	public ProgressDialog loadingProgress;
	public BetaBaseActionbarActivity baseActivity;

	private String childName = TAG;

	public static final BetaBaseFragment newInstance(Bundle bundle, BetaBaseFragment fm) {

		fm.setArguments(bundle);
		return fm;
	}

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		baseActivity = (BetaBaseActionbarActivity) getActivity();

	}

	public void setParent(BetaBaseActionbarActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	public BetaBaseActionbarActivity getParent() {
		return baseActivity;
	}

	@Override
	public void onStart() {
		super.onStart();
		

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	
	}

	@Override
	public Context getContext() {
		return this.getActivity().getApplicationContext();

	}

	protected BaseModel getModel() {
		return baseActivity.model;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	public boolean onBackPressed() {
		return false;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	public void showToast(String message) {
		if (message != null)
			Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
	}

	public void showToastMessage(int resId) {
		Toast.makeText(getContext(), getString(resId), Toast.LENGTH_LONG).show();
	}

	public void showProgressDialog(Context context, String message) {
		closeProgressDialog();
		if(context==null)return;
		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage(""+message);
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void showProgressDialog(Context context) {
		closeProgressDialog();
		if(context==null)return;
		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage("Loading");
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void closeProgressDialog() {
		if (loadingProgress != null) {
			if (loadingProgress.isShowing())
				loadingProgress.dismiss();
			loadingProgress = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telpoo.frame.model.ModelListener#onSuccess(int,
	 * java.util.ArrayList, java.lang.String)
	 */
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telpoo.frame.model.ModelListener#onFail(int, java.lang.String)
	 */
	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.telpoo.frame.model.ModelListener#onProgress(int, int)
	 */
	@Override
	public void onProgress(int taskType, int progress) {
		// TODO Auto-generated method stub

	}

	protected void pushFragment(int resource, Fragment fm) {

		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(resource, fm, "");
		ft.commit();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		enableHideKbOutSide();

				trackFm();
		// mTracker=EasyTracker.getInstance(getActivity());

	}

	protected void trackFm() {
		if (childName != null) {
	}
	}

	protected void trackEvent(String ui_action,String button_press,String play_button) {
			}

	protected void trackScreen(String name) {
		childName = name;
	}

	protected void enableHideKbOutSide() {
		//View rootView = getView().findViewById(R.id.root_layout);
		// if(rootView!=null)
		// BHAds.countclick_hidekeyboard1(rootView, getActivity());
		// ViewUtils.HideKeyboarOutside(rootView, getActivity());
	}

}
