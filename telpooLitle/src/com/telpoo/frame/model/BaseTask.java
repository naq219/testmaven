package com.telpoo.frame.model;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

/**
 * 
 * @author NAQ219
 * 
 */
public class BaseTask extends AsyncTask<TaskParams, Void, Boolean> {

	protected static final Boolean TASK_FAILED = false;
	protected static final Boolean TASK_DONE = true;
	protected BaseModel baseModel;
	protected int taskType;
	protected int code = -1;
	protected String msg = null;
	protected Context context;
	protected ArrayList<?> dataFromModel = null;
	protected ArrayList<?> dataReturn = null;
	protected ArrayList<View> views;
	
	
	

	public BaseTask(BaseModel baseModel, int taskType) {
		this.baseModel = baseModel;
		this.taskType = taskType;
	}

	public BaseTask(BaseModel baseModel, int taskType, ArrayList<?> list, Context context) {
		this.baseModel = baseModel;
		this.taskType = taskType;
		this.dataFromModel = list;
		this.context = context;
	}
	
	public void exe(){
		this.baseModel.exeTask(null, this);
	}
	

	@Override
	protected void onPreExecute() {
		switch (taskType) {
		default:
		}
	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {
		if (baseModel == null)
			return TASK_FAILED;
		// DBSupport dbSupport = taskListener.getDBSupport();

		switch (taskType) {
		default:
			break;
		}

		if ((dataFromModel != null && dataFromModel.size() > 0)) {
			return TASK_DONE;
		}
		return TASK_FAILED;

	}


	@Override
	protected void onPostExecute(Boolean result) {

		if (!result) {
			if (baseModel != null)
				baseModel.onFail(taskType,msg);

		} else {
			if (baseModel != null) {
				baseModel.onSuccess(taskType, dataReturn,  msg);

			}
		}
	}

}
