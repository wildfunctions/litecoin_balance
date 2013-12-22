package com.coinhark.litecoinbalance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;
 
public class HttpURLConnecter {
 
	private final String USER_AGENT = "Mozilla/5.0 Litecoin Balance Android App by Tyrick";
 
	public static void main(String[] args) throws Exception {
		HttpURLConnecter http = new HttpURLConnecter();
		Log.d("[Litecoin Balance]", "Testing 1 - Send Http GET request");
		http.sendGet("http://explorer.litecoin.net/chain/Litecoin/q/getsentbyaddress/LM8DBiYEBjHkzgSGVDvwPe5oeVosnggx5a");
	}
 
	// HTTP GET request
	public String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		//int responseCode = con.getResponseCode();
		//Log.d("[Litecoin Balance]", "\nSending 'GET' request to URL : " + url);
		//Log.d("[Litecoin Balance]", "Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		return response.toString();
	}
	
    public String sendPost(String url, JSONObject obj) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpPost request = new HttpPost(url);
            request.setHeader("User-Agent", USER_AGENT);
            
            StringEntity params =new StringEntity(obj.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();

        	BufferedReader rd = new BufferedReader(
        	        new InputStreamReader(response.getEntity().getContent()));
         
        	StringBuffer result = new StringBuffer();
        	String line = "";
        	while ((line = rd.readLine()) != null) {
        		result.append(line);
        	}
        	
            //Log.d("[Litecoin Balance]", response.getStatusLine().toString());
            //Log.d("[Litecoin Balance]", result.toString());
            if (entity != null) {
                entity.consumeContent();
            }
            
            return result.toString();
            
        }catch (Exception ex) {
        	Log.e("[Litecoin Balance]", ex.toString());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }	
 
}