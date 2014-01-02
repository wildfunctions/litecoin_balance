package com.coinhark.litecoinbalance;

import java.util.concurrent.Callable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coinhark.litecoinbalance.api.BalanceAPI;
import com.coinhark.litecoinbalance.utils.MathUtils;

public class HttpExchangeThread implements Callable<Double> {

	int currency = 0;
	
	   public HttpExchangeThread(int currency) {
	       this.currency = currency;
	   }

	   public Double call() {
       		HttpURLConnecter http = new HttpURLConnecter();
       		String rate = null;
       		double rateDouble = 0d;
	        try {
		        rate = http.sendGet(BalanceAPI.getRateUrl(0) + BalanceAPI.getKrakenParam(currency));
		        //Log.d("[Litecoin Balance]", rate);
		        JSONObject json = new JSONObject(rate);
		        json = json.getJSONObject("result");
		        json = json.getJSONObject(BalanceAPI.getKrakenReturnParam(currency));
		        
		        JSONArray jsonArray = json.getJSONArray("b");
		        String rateString = jsonArray.getString(0);
		        if(!MathUtils.isNumeric(rateString)) {
		        	rateDouble = 0d;
		        } else {
			        rateDouble = Double.parseDouble(jsonArray.getString(0));
		        }
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        if(rate == null) {
	        	return 0d;
	        }
	        
       		if(rateDouble < 0) {
       			return 0d;
       		}
       		return rateDouble;
	   }

}