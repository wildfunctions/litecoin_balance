package com.coinhark.litecoinbalance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
 
public class HttpURLConnecter {
 
	private final String USER_AGENT = "Mozilla/5.0";
 
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
 
		int responseCode = con.getResponseCode();
		Log.d("[Litecoin Balance]", "\nSending 'GET' request to URL : " + url);
		Log.d("[Litecoin Balance]", "Response Code : " + responseCode);
 
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
 
}