package com.telpoo.frame.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.telpoo.frame.utils.Mlog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Stack;

public class BetaBaseFmActivity extends BetaBaseActionbarActivity {
	protected HashMap<String, Stack<Fragment>> mStacks;
    protected String mCurrentTab;
    protected BetaBaseFragment currentFragment = null;
	protected int resource_home;
    protected String toastAskExit;
	String[] TabIds;
	private String TAG = BetaBaseFmActivity.class.getSimpleName();

	public BetaBaseFmActivity(String[] TabIds, int resource_home,
			String toastAskExit) {
		this.TabIds = TabIds;
		this.resource_home = resource_home;
		this.toastAskExit = toastAskExit;
	}

    public BetaBaseFmActivity(){

    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mStacks = new HashMap<String, Stack<Fragment>>();

		for (String tabid : TabIds) {
			mStacks.put(tabid, new Stack<Fragment>());
		}

		// pushFragments(tag, fragment, shouldAdd, TAG)

	}
	
	public BetaBaseFragment getCurrentFm(){
		return currentFragment;
	}

	@Override
	public void pushFragments(String tab, Fragment fragment, boolean shouldAdd,
			String TAG) {
		super.pushFragments(tab, fragment, shouldAdd, TAG);

		if (shouldAdd) {
			mStacks.get(tab).push(fragment);
		}
		if (tab != null)
			mCurrentTab = tab;
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(resource_home, fragment, TAG);

		ft.commit();
		currentFragment = (BetaBaseFragment) fragment;

	}

	public void popFragments() {
		int at = mStacks.get(mCurrentTab).size() - 2;
		if (at < 0)
			return;
		Fragment fragment = mStacks.get(mCurrentTab).elementAt(at);
		currentFragment = (BetaBaseFragment) fragment;

		mStacks.get(mCurrentTab).pop();

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(resource_home, fragment);
		ft.commit();

	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {

		super.onSuccess(taskType, list, msg);
//		if (currentFragment != null)
//			currentFragment.onSuccess(taskType, list, msg);
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);
//		if (currentFragment != null)
//			currentFragment.onFail(taskType, msg);
	}

	@Override
	public void onBackPressed() {

		backFragment(mCurrentTab);

	}
	
	public void setCurrentTab(String tab){
		mCurrentTab=tab;
	}

	private long lastedClickBack = 0;

	public void backFragment(String tabID) {
		if (tabID == null) {
			Mlog.w(TAG + " -backFragment: TabId==null");
			return;
		}
		Stack<Fragment> a = mStacks.get(tabID);
		Fragment tBaseFragment = null;
		if (a.size() != 0)
		 tBaseFragment = a.lastElement();
		
		if (a.size()==0||((BetaBaseFragment) tBaseFragment).onBackPressed() == false) {
			if (a.size() == 1||a.size() == 0) {

				Calendar cal = Calendar.getInstance();
				long curTime = cal.getTimeInMillis();
				if (curTime - lastedClickBack > 5000)
					
				{
					if(toastAskExit!=null)
						Toast.makeText(getBaseContext(), toastAskExit,
								Toast.LENGTH_SHORT).show();
				}

				else {

					finish();
				}
				lastedClickBack = curTime;
			} else {
				popFragments();
			}
		} else {
			// do nothing.. fragment already handled back button press.
		}

	}
	
	public  String getCurrentTab(){
		return mCurrentTab;
	}
	
	public void clearSavedFmInTab(String tabId){
		mStacks.get(tabId).clear();
	}

}
