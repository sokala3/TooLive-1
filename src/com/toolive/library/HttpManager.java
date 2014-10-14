package com.toolive.library;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

public class HttpManager 
{
	public enum Request
	{
		GET,
		POST,
		PUT,
		DELETE
	}
	
	public static String getContent(String uri, Request type)
	{
		return HttpManager.getContent(uri, type, null);
	}
	
	public static String getContent(String uri, Request type, List<NameValuePair> params)
	{
		AndroidHttpClient client = AndroidHttpClient.newInstance("AdnroidAgent");
		HttpResponse response;
		HttpGet getRequest = null;
		HttpPost postRequest = null;
		
		if(type == Request.GET)
		{
			getRequest = new HttpGet(uri);
			getRequest.setHeader("Auth", "bd23b7c490cc473dacea0e8dd818e579");
		}
		else if(type == Request.POST)
		{
			postRequest = new HttpPost(uri);
			postRequest.setHeader("Auth", "bd23b7c490cc473dacea0e8dd818e579");
			try {
				postRequest.setEntity(new UrlEncodedFormEntity(params));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			response = client.execute(getRequest != null ? getRequest : postRequest);
			return EntityUtils.toString(response.getEntity());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			client.close();
		}
	}
}
