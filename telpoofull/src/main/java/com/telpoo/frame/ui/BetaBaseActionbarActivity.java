package com.telpoo.frame.ui;

/**
 *
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NAQ219
 *
 */
public class BetaBaseActionbarActivity extends ActionBarActivity implements ModelListener {

	private static final String TAG = BetaBaseActionbarActivity.class.getSimpleName();
	public BaseModel model = null;
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	protected ProgressDialog loadingProgress;
	public boolean mIsBound;
	private BetaBaseActionbarActivity me;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		if (model == null) {
			model = new BaseModel();
			model.setModelListener1(this);
		}
		me=BetaBaseActionbarActivity.this;
		
		
		
		
	}

	protected void setTrackingId(String trackingId){
		Utils.saveStringSPR("trackingId", trackingId, BetaBaseActionbarActivity.this.getContext());
	}
	@Override
	protected void onStart() {
		super.onStart();
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		model.setModelListener1(this);

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		
	}

	@Override
	public Context getContext() {
		return BetaBaseActionbarActivity.this;
	}



	protected void showToast(int msg){
		Toast.makeText(BetaBaseActionbarActivity.this.getContext(), BetaBaseActionbarActivity.this.getContext().getText(msg), Toast.LENGTH_SHORT).show();
	}
	
	protected void showToast(String msg){
		Toast.makeText(BetaBaseActionbarActivity.this.getContext(), msg, Toast.LENGTH_LONG).show();
	}


	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		Mlog.D(TAG+"-onSuccess- taskType="+taskType+" -list.size="+(list==null?"null":""+list.size())+" -msg="+msg);
		
		final FragmentManager childFragmentManager = getSupportFragmentManager();
		 
	    if (childFragmentManager != null) {
	        final List<Fragment> nestedFragments = childFragmentManager.getFragments();
	 
	        if (nestedFragments == null || nestedFragments.size() == 0) return;
	 
	        for (Fragment childFragment : nestedFragments) {
	            if (childFragment != null && !childFragment.isDetached() && !childFragment.isRemoving()) {
	               if(childFragment instanceof BetaBaseFragment){
	            	   ((BetaBaseFragment) childFragment).onSuccess(taskType, list, msg);
	               }
	            }
	        }
	    }
	}


	@Override
	public void onFail(int taskType, String msg) {
		Mlog.D(TAG+"-onFail- taskType="+taskType+" -msg="+msg);
		
		final FragmentManager childFragmentManager = getSupportFragmentManager();
		 
	    if (childFragmentManager != null) {
	        final List<Fragment> nestedFragments = childFragmentManager.getFragments();
	 
	        if (nestedFragments == null || nestedFragments.size() == 0) return;
	 
	        for (Fragment childFragment : nestedFragments) {
	            if (childFragment != null && !childFragment.isDetached() && !childFragment.isRemoving()) {
	               if(childFragment instanceof BetaBaseFragment){
	            	   ((BetaBaseFragment) childFragment).onFail(taskType, msg);
	               }
	            }
	        }
	    }
	    
	    
	}


	@Override
	public void onProgress(int taskType, int progress) {

	}
	
	public void pushFragments(String tag, Fragment fragment, boolean shouldAdd, String TAG) {
		
	}
	
	public void pushFragmentsSaveCurrent(final String tag, final Fragment fragment, final boolean shouldAdd,Fragment fmSave) {
	

	}
	
	public void showProgressDialog(Context context, String message) {
		closeProgressDialog();

		loadingProgress = new ProgressDialog(context);
		loadingProgress.setMessage(message);
		loadingProgress.setCanceledOnTouchOutside(false);
		loadingProgress.show();
	}

	public void showProgressDialog(Context context) {
		closeProgressDialog();

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
	
	public BetaBaseActionbarActivity getRootFA(){
		return me;
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	 
	    // notifying nested fragments (support library bug fix)
	    final FragmentManager childFragmentManager = getSupportFragmentManager();
	 
	    if (childFragmentManager != null) {
	        final List<Fragment> nestedFragments = childFragmentManager.getFragments();
	 
	        if (nestedFragments == null || nestedFragments.size() == 0) return;
	 
	        for (Fragment childFragment : nestedFragments) {
	            if (childFragment != null && !childFragment.isDetached() && !childFragment.isRemoving()) {
	                childFragment.onActivityResult(requestCode, resultCode, data);
	            }
	        }
	    }
	}

    public void setTitleActionBar(String title) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }


    public void setSubTitleActionBar(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(subtitle);
    }
	
	
	
	
	



}
