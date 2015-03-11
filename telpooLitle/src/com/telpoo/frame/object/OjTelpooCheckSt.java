package com.telpoo.frame.object;

import com.telpoo.frame.utils.Cons;
import com.telpoo.frame.utils.SPRSupport;

import android.content.Context;

public class OjTelpooCheckSt {

	public static final String keys[] ={"app_id","app_name","msg_id","msg_content","msg_url",
	"msg_action",	"ads_id","ads_type","framework_serial","analytic_id"
	};
	
	
	public static final String APP_ID = keys[0];
	public static final String APP_NAME = keys[1]; 
	
	public static final String MSG_ID = keys[2]; 
	public static final String MSG_CONTENT = keys[3]; 
	public static final String MSG_URL = keys[4];
	public static final String  MSG_ACTION = keys[5];
	public static final String  ADS_ID = keys[6];
	public static final String ADS_TYPE = keys[7];
	public static final String FRAMEWORK_SERIAL = keys[8];
    public static final String ANALYTIC_ID = keys[9];

    public static final String getAdsId(Context context){
		return SPRSupport.getString("telpoo"+ADS_ID, context);
	}
    public static final Integer getAdsType(Context context){
        return SPRSupport.getAsInt("telpoo" + ADS_TYPE, context);
    }

    public static final  String getAnalytic(Context context){
        String id= SPRSupport.getString("telpoo"+ANALYTIC_ID, context);
        if(id.equals(Cons.Defi.SPR_GET_FALL)) return "";
        return id;
    }
	
}
