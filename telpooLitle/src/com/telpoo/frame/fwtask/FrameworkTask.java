package com.telpoo.frame.fwtask;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;

import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Cons;
import com.telpoo.frame.utils.JsonSupport;
import com.telpoo.frame.utils.SPRSupport;

public class FrameworkTask extends BaseTask implements FwTasktype{

	public FrameworkTask(BaseModel baseModel, int taskType, ArrayList<?> list,
			Context context) {
		super(baseModel, taskType, list, context);
	}
	
	@Override
	protected Boolean doInBackground(TaskParams... params) {
		
		switch (taskType) {
		case TASK_GET_SETTING:
			
			
			String urlCheckApp= (String) dataFromModel.get(0);
			String res =BaseNetSupportBeta.getInstance().method_GET(urlCheckApp);
		 	if(res==null) return TASK_FAILED;
			
			try {
				BaseObject ojSetting= JsonSupport.jsonArray2BaseOj(res).get(0);
				
				ArrayList<BaseObject> ojsCk=new ArrayList<BaseObject>();
				ojsCk.add(ojSetting);
				dataReturn=ojsCk;
				return TASK_DONE;
				
				
			} catch (JSONException e) {
				e.printStackTrace();
				return TASK_FAILED;
			}
			
			
			
			
			

		default:
			break;
		}
		
		
		return super.doInBackground(params);
	}
}
