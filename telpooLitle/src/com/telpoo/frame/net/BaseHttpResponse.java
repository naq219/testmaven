package com.telpoo.frame.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class BaseHttpResponse {
    
    private int status;
    private String url;
    private Header[] headers;
    private String stringRespone;
    private HttpResponse httpResponse;
    public BaseHttpResponse() {
		// TODO Auto-generated constructor stub
	}
    
//    public BaseHttpResponse(HttpURLConnection urlConnection) {
//        try {
//            this.setStatus(urlConnection.getResponseCode());
//            this.setUrl(urlConnection.getURL().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.setHeaders(urlConnection.getHeaderFields());
//    }
    
    
    
    public int getStatus() {
        return status;
    }
    
    public String getUrl() {
        return url;
    }

    public Header[] getHeaders() {
        return headers;
    }

	public String getStringRespone() {
		return stringRespone;
	}

	public void setStringRespone(String stringRespone) {
		this.stringRespone = stringRespone;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setStatus(int status) {
		this.status = status;
	}

//	public void setHeaders(Header[] headers2) {
//		this.headers = headers2;
//	}
	
	public Header[] getHeader(String key){
		return httpResponse.getHeaders(key);
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
   
    
}

