/**
 */
package com.telpoo.frame.net;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.Mlog;

/**
 * 
 * @author NAQ
 * 
 */
public class BaseNetSupportBeta {
	protected static String TAG = BaseNetSupportBeta.class.getSimpleName();
	private String contentType;
	private String userAgent;
	private int connectTimeout;
	private int soTimeout;
	private String authorization;
	private int numberRetry = 3;
	private boolean isInited = false;
	// private volatile static BaseNetSupportBeta instance;
	private static BaseNetSupportBeta instance;

	/** Returns singleton class instance */
	// public static BaseNetSupportBeta getInstance() {
	// if (instance == null) {
	// synchronized (ImageLoader.class) {
	// if (instance == null) {
	// instance = new BaseNetSupportBeta();
	// }
	// }
	// }
	// return instance;
	// }

	public static BaseNetSupportBeta getInstance() {
		if (instance == null) {

			if (instance == null) {
				instance = new BaseNetSupportBeta();
			}

		}
		return instance;
	}

	private BaseNetSupportBeta() {

	}

	public void init(NetConfig netConfig) {
		connectTimeout = netConfig.getConnectTimeout();
		soTimeout = netConfig.getSoTimeout();
		authorization = netConfig.getAuthorization();
		contentType = netConfig.getContentType();
		userAgent = netConfig.getUserAgent();
		numberRetry = netConfig.getNumberRetry();
	}

	private HttpClient myHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpConnectionParams.setConnectionTimeout(params, connectTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
		HttpClient client = new DefaultHttpClient(params);
		return client;
	}

	public String method_GET(String url) {
		if (numberRetry == 0)
			return null;
		int retryCount = 0;

		do {
			try {

				URL myUrl = new URL(url.replace(" ", "%20"));

				Mlog.D(TAG + "- method_GET -URl:" + myUrl);

				HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(soTimeout);
				if (authorization != null)
					conn.setRequestProperty("Authorization", authorization);

				conn.setRequestProperty("Content-Type", contentType);
				conn.setRequestProperty("User-Agent", userAgent);

				String jsonContent = FileSupport.readFromInputStream(conn.getInputStream());
				Mlog.D(TAG + "- method_GET - json result=" + jsonContent);

				conn.disconnect();
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E(TAG + "658345234 NetworkSupport - getNetworkData - FileNotFoundException = " + ex.getMessage());

			} catch (Exception ex) {
				Mlog.E(TAG + "789345564 NetworkSupport - getNetworkData - Exception = " + ex.getMessage());
			}
		} while (++retryCount < numberRetry);

		return null;
	}

	public String method_POST(String url, String bodySend) {
		if (numberRetry == 0)
			return null;
		int retryCount = 0;

		Mlog.D(TAG + "-method_POST - url=" + url);
		Mlog.D(TAG + "-method_POST - json sent=" + bodySend);

		do {
			try {

				URL myUrl = new URL(url);
				HttpClient client = myHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(), connectTimeout);
				HttpResponse response;

				InputStream in = null;
				try {
					HttpPost post = new HttpPost(myUrl.toURI());
					if (authorization != null)
						post.setHeader("Authorization", getB64Auth(authorization));

					StringEntity se = new StringEntity(bodySend, "UTF8");

					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));

					se.setContentEncoding(HTTP.UTF_8);

					post.setEntity(se);
					response = client.execute(post);

					/* Checking response */
					if (response != null) {
						in = response.getEntity().getContent(); // Get the data
					}

				} catch (Exception e) {
					Mlog.E(TAG + "4363 method_POST " + e.toString());
					return null;
				}
				String jsonContent = FileSupport.readFromInputStream(in);
				Mlog.D("method_POST - response: " + jsonContent);
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E("method_POST - getNetworkData - FileNotFoundException = " + ex.getMessage());
				return null;

			} catch (Exception ex) {
				Mlog.E("method_POST - getNetworkData - Exception = " + ex.getMessage());
				return null;
			}
		} while (++retryCount < numberRetry);

	}

	public String method_POST_SSL(String url, String bodySend) {
		if (numberRetry == 0)
			return null;
		int retryCount = 0;
		Mlog.D(TAG + "-method_POST - url=" + url);
		Mlog.D(TAG + "-method_POST - json sent=" + bodySend);

		do {
			try {

				URL myUrl = new URL(url);
				HttpClient client = MySSLSocketFactory.getNewHttpClient(); // myHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(), connectTimeout);
				HttpResponse response;

				InputStream in = null;

				try {
					HttpPost post = new HttpPost(myUrl.toURI());
					if (authorization != null)
						post.setHeader("Authorization", getB64Auth(authorization));

					StringEntity se = new StringEntity(bodySend, "UTF8");

					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));

					se.setContentEncoding(HTTP.UTF_8);

					post.setEntity(se);
					response = client.execute(post);

					/* Checking response */
					if (response != null) {
						in = response.getEntity().getContent(); // Get the data
					}

				} catch (Exception e) {
					Mlog.E(TAG + "234234 method_POST_SSL " + e.toString());
					return null;
				}
				String jsonContent = FileSupport.readFromInputStream(in);
				Mlog.D("method_POST - response: " + jsonContent);
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E("method_POST - getNetworkData - FileNotFoundException = " + ex.getMessage());
				return null;

			} catch (Exception ex) {
				Mlog.E("method_POST - getNetworkData - Exception = " + ex.getMessage());
				return null;
			}
		} while (++retryCount < numberRetry);

	}

	public BaseObject method_POST_SSL_N(String url, String bodySend) {
		if (numberRetry == 0)
			return null;
		BaseObject oj = new BaseObject();
		int retryCount = 0;

		Mlog.D(TAG + "-method_POST - json sent=" + bodySend);
		Mlog.D(TAG + "-method_POST - url=" + url);

		do {
			try {

				URL myUrl = new URL(url);
				HttpClient client = MySSLSocketFactory.getNewHttpClient(); // myHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(), connectTimeout);
				HttpResponse response;

				InputStream in = null;

				try {
					HttpPost post = new HttpPost(myUrl.toURI());
					if (authorization != null)
						post.setHeader("Authorization", getB64Auth(authorization));

					StringEntity se = new StringEntity(bodySend, "UTF8");

					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));

					se.setContentEncoding(HTTP.UTF_8);

					post.setEntity(se);
					response = client.execute(post);

					/* Checking response */
					if (response != null) {
						in = response.getEntity().getContent(); // Get the data
					}

					oj.set("code", response.getStatusLine().getStatusCode());

				} catch (Exception e) {
					Mlog.E(TAG + "576237 method_POST_SSL_N " + e.toString());
					return null;
				}
				String jsonContent = FileSupport.readFromInputStream(in);
				Mlog.D("method_POST - response: " + jsonContent);
				if (jsonContent != null) {
					oj.set("res", jsonContent);
				}
				return oj;
			} catch (FileNotFoundException ex) {
				Mlog.E("method_POST - getNetworkData - FileNotFoundException = " + ex.getMessage());
				return null;

			} catch (Exception ex) {
				Mlog.E("method_POST - getNetworkData - Exception = " + ex.getMessage());
				return null;
			}
		} while (++retryCount < numberRetry);

	}

	public String method_GET_SSL(String url) {
		if (numberRetry == 0)
			return null;
		int retryCount = 0;
		Mlog.D(TAG + "-method_GET_SSL - url=" + url);
		do {
			try {

				URL myUrl = new URL(url);
				HttpClient client = MySSLSocketFactory.getNewHttpClient(); // myHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(), connectTimeout);
				HttpResponse response;

				InputStream in = null;
				try {
					HttpGet post = new HttpGet(myUrl.toURI());
					if (authorization != null) {
						post.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials("vnp-mobile-app", "A8aFPkuCmbeBcTXRQVyZNn4hW9q"), "UTF-8", false));
					}
					response = client.execute(post);

					/* Checking response */
					if (response != null) {
						in = response.getEntity().getContent(); // Get the data
					}

				} catch (Exception e) {
					Mlog.E(TAG + "576237 method_GET_SSL " + e.toString());
					return null;
				}
				String jsonContent = FileSupport.readFromInputStream(in);
				Mlog.D("method_POST - response: " + jsonContent);
				return jsonContent;
			} catch (FileNotFoundException ex) {
				Mlog.E("method_POST - getNetworkData - FileNotFoundException = " + ex.getMessage());
				return null;

			} catch (Exception ex) {
				Mlog.E("method_POST - getNetworkData - Exception = " + ex.getMessage());
				return null;
			}
		} while (++retryCount < numberRetry);

	}

	/*
	 * protected static NetData parseResponseToNetData(String response, String[]
	 * keys) { NetData netData = new NetData(); if (response == null ||
	 * response.length() == 0) { netData.code = false; netData.msg =
	 * HomeActivity.str_connect_error;
	 * 
	 * return netData; } return BaseNetSupport.ParseJson(response, keys);
	 * 
	 * }
	 */

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	private String getB64Auth(String string) {
		String ret = "Basic " + Base64.encodeToString(string.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
		return ret;
	}

	// public static String uploadImgur(String path, String clientId) {
	// try {
	// File file = new File(path);
	// final String upload_to = "https://api.imgur.com/3/upload.json";
	// String API_key = "27905d84c9ec40a";
	// if (clientId != null)
	// API_key = clientId;
	// HttpClient httpClient = new DefaultHttpClient();
	// HttpContext localContext = new BasicHttpContext();
	// HttpPost httpPost = new HttpPost(upload_to);
	// httpPost.setHeader("Authorization", "Client-ID " + API_key);
	//
	// final MultipartEntity entity = new
	// MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	// entity.addPart("image", new FileBody(file));
	// entity.addPart("key", new StringBody(API_key));
	// entity.addPart("description", new StringBody("test upimgae"));
	// httpPost.setEntity(entity);
	// final HttpResponse response = httpClient.execute(httpPost, localContext);
	// final String response_string =
	// EntityUtils.toString(response.getEntity());
	// // return response_string;
	// final JSONObject json = new JSONObject(response_string);
	// String link = json.getJSONObject("data").getString("link");
	// // link = "http://i.imgur.com/"+link+"l.png";
	// return link;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// }

	// final DefaultHttpClient client = new DefaultHttpClient();
	// HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
	// HttpHost target = new HttpHost("www.google.com", 443, "https");
	// client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	// HttpProtocolParams
	// .setUserAgent(client.getParams(),
	// "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
	// final HttpGet get = new HttpGet("/");
	//
	// HttpResponse response = client.execute(target, get);

	public BaseHttpResponse tempMethod_POST(String url, String bodySend) {
		if (numberRetry == 0)
			return null;
		int retryCount = 0;
		BaseHttpResponse baseResponse=new BaseHttpResponse();

		Mlog.D(TAG + "-method_POST - url=" + url);
		Mlog.D(TAG + "-method_POST - json sent=" + bodySend);
		baseResponse.setUrl(url);
		do {
			try {

				URL myUrl = new URL(url);
				HttpClient client = myHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(), connectTimeout);
				HttpResponse response;

				InputStream in = null;
				try {
					HttpPost post = new HttpPost(myUrl.toURI());
					if (authorization != null)
						post.setHeader("Authorization", getB64Auth(authorization));

					StringEntity se = new StringEntity(bodySend, "UTF8");

					se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, contentType));

					se.setContentEncoding(HTTP.UTF_8);

					post.setEntity(se);
					response = client.execute(post);

					/* Checking response */
					if (response != null) {
						baseResponse.setStatus(response.getStatusLine().getStatusCode());
						in = response.getEntity().getContent(); // Get the data
						//baseResponse.setHeaders(response.getAllHeaders());
						baseResponse.setHttpResponse(response);
//						Header[] headers = response.getHeaders("Set-Cookie");
//						for (Header h : headers) {
//							System.out.println(h.getValue().toString());
//						}

					}

				} catch (Exception e) {
					Mlog.E(TAG + "4363 method_POST " + e.toString());
					return null;
				}
				String strResponse = FileSupport.readFromInputStream(in);
				baseResponse.setStringRespone(strResponse);
				
				Mlog.D("method_POST - response: " + strResponse);
				return baseResponse;
			} catch (FileNotFoundException ex) {
				Mlog.E("method_POST - getNetworkData - FileNotFoundException = " + ex.getMessage());
				return null;

			} catch (Exception ex) {
				Mlog.E("method_POST - getNetworkData - Exception = " + ex.getMessage());
				return null;
			}
		} while (++retryCount < numberRetry);

	}

}
