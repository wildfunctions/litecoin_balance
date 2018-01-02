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
       		String ret = null;
       		String priceStr = null;
       		double rateDouble = 0d;
	        try {
		        ret = http.sendGet(BalanceAPI.getRateUrl(0));
		        //Log.d("[Litecoin Balance]", rate);
		        JSONArray jsonArr = new JSONArray(ret);

		        JSONObject jsonObj = jsonArr.getJSONObject(0);
				priceStr = jsonObj.getString("price_usd");

		        if(!MathUtils.isNumeric(priceStr)) {
		        	rateDouble = 0d;
		        } else {
			        rateDouble = Double.parseDouble(priceStr);
		        }
			} catch (JSONException e) {
				//e.printStackTrace();
			} catch (Exception e) {
				//e.printStackTrace();
			}

	        if(ret == null) {
	        	return 0d;
	        }
	        
       		if(rateDouble < 0) {
       			return 0d;
       		}
       		return rateDouble;
	   }

}