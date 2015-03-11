package com.telpoo.frame.system;



import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import com.telpoo.frame.fwtask.FrameworkTask;
import com.telpoo.frame.fwtask.FwTasktype;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.object.OjTelpooCheckSt;
import com.telpoo.frame.utils.Cons;
import com.telpoo.frame.utils.DialogSupport;
import com.telpoo.frame.utils.SPRSupport;

public class CheckApp {
	
	public static void checkSetting(final Context context ,String url,String frAppId,final boolean isUseUI){
		BaseModel model =new BaseModel(){
			@Override
			public void onSuccess(int taskType, ArrayList<?> list, String msg) {
				//Toast.makeText(context, "keke", 1).show();
				
				BaseObject ojSetting= (BaseObject) list.get(0);
				BaseObject oj= getOjSetting(context);
				
				if(isUseUI){
					
					if (ojSetting.getInt(OjTelpooCheckSt.MSG_ID)>oj.getInt(OjTelpooCheckSt.MSG_ID)) {
                        if(ojSetting.getInt(OjTelpooCheckSt.MSG_ID)!=0)
						DialogSupport.Message(context, ojSetting.get(OjTelpooCheckSt.MSG_CONTENT),null);
						
					}
					
				}
				String extra="telpoo";
				ArrayList<String> keys = ojSetting.getKeys();
				for (int i = 0; i < keys.size(); i++) {
					SPRSupport.save(extra+keys.get(i), ojSetting.get(keys.get(i)), context);
				}
				
				
				
				super.onSuccess(taskType, list, msg);
			}
		};
		
		ArrayList<String> ab=new ArrayList<String>();
		ab.add(url+frAppId);
		
		FrameworkTask task= new FrameworkTask(model, FwTasktype.TASK_GET_SETTING, ab, context);
		task.exe();
	}
	
	public static void checkSetting(final Context context,String frAppId, boolean isUseUI){
		checkSetting(context,Cons.urlCheckApp,frAppId,isUseUI);
	}
	
	
	public static BaseObject getOjSetting(Context context){
		BaseObject oj =new BaseObject();
		String extra="telpoo";
		for (int i = 0; i < OjTelpooCheckSt.keys.length; i++) {
			String k= OjTelpooCheckSt.keys[i];
			oj.set(k, SPRSupport.getString(extra+k, context));
		}
		return oj;
	}

}
