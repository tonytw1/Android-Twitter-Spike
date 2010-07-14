package com.gu.twitter.network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpFetcher {

	
	public String getContent(String url) {
		 HttpClient client = new DefaultHttpClient();		 
		 HttpGet get = new HttpGet(url);

		 try {
			HttpResponse response = client.execute(get);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String handleResponse = responseHandler.handleResponse(response);
			return handleResponse;			
						
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
