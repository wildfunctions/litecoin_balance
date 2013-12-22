package com.coinhark.litecoinbalance;

import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;

import com.coinhark.litecoinbalance.api.BalanceAPI;
import com.coinhark.litecoinbalance.api.CurrencyEnum;
import com.coinhark.litecoinbalance.utils.MathUtils;

public class HttpExchangeThread implements Callable<Double> {

	int currency = 0;
	
	   public HttpExchangeThread(int currency) {
	       this.currency = currency;
	   }

	   public Double call() {
       		HttpURLConnecter http = new HttpURLConnecter();
       		String rate = null;
       		double rateDouble = 0;
	        JSONObject obj = new JSONObject();
	        try {
				obj.put("pair", CurrencyEnum.getPostParam(currency));
		        rate = http.sendGet(BalanceAPI.getRateUrl(0) + CurrencyEnum.getString(currency));
		        //Log.d("[Litecoin Balance]", rate);
		        JSONObject json = new JSONObject(rate);
		        rateDouble = json.getDouble("value");
		        if(!MathUtils.isNumeric(rateDouble + "")) {
		        	rateDouble = 0;
		        }
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        if(rate == null) {
	        	return 0.00;
	        }
	        
       		if(rateDouble < 0) {
       			return 0.00;
       		}
       		return rateDouble;
	   }

}