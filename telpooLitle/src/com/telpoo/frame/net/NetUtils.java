package com.telpoo.frame.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.telpoo.frame.utils.Cons.NetConfig1;


public class NetUtils implements NetConfig1 {
	
	public static boolean download(URL url, OutputStream outputStream) {

		HttpGet httpRequest = null;
		try {
			httpRequest = new HttpGet(url.toURI());
		} catch (URISyntaxException e) {
			Log.i("NAQ", "Downloader - SERVICE_JOB_COMMAND_DOWNLOAD_FILE - URISyntaxException e = " + e);
			return false;
		}
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpClient httpclient = new DefaultHttpClient(params);
		int numberOfRetry = NUMBER_OF_RETRY;
		while(numberOfRetry-->=0) {
			try {
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);

				HttpEntity entity = response.getEntity();

				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);

				InputStream instream = bufHttpEntity.getContent();
				byte[] data = new byte[FILE_BUFFER_SIZE];
				int bytesRead = 0;
				while ((bytesRead = instream.read(data)) > 0) {
					outputStream.write(data, 0, bytesRead);
				}
				return true;
			} catch (Exception e) {				
				Log.i("nth", "Downloader - SERVICE_JOB_COMMAND_DOWNLOAD_FILE - Exception e = " + e);
				return false;
			}
		}
		return false;
	}
	
	public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
	}
	
	public class UserAgent{
		public static final String IPHONE4="Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5";
		
	}
	
	public static String contentValue2bodyPost(ContentValues params){
		String body="";
		
		Set<Entry<String, Object>> s=params.valueSet();
		   Iterator itr = s.iterator();


		   while(itr.hasNext())
		   {
		        Map.Entry me = (Map.Entry)itr.next(); 
		        String key = me.getKey().toString();
		        Object value =  me.getValue();
		        body+=key+"="+value+"&";
		        

		   }
		   
		   return body;
		
	}
	
}