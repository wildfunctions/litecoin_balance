package com.coinhark.litecoinbalance;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.coinhark.litecoinbalance.api.BalanceAPI;
import com.coinhark.litecoinbalance.utils.MathUtils;

import android.util.Log;

import org.json.JSONObject;

public class HttpBalanceThread implements Callable<Double> {

	private String sentUrl = BalanceAPI.getSentUrl();
	private String receivedUrl = BalanceAPI.getReceivedUrl();
	private String infoUrl = BalanceAPI.getInfoUrl();
	
	private String address;
	double balance = 0d;
	double receivedBalance = 0d;
	double sentBalance = 0d;
	
	   public HttpBalanceThread(String address) {
	       this.address = address;
	   }

	   public Double call() {
       		HttpURLConnecter http = new HttpURLConnecter();
       		String sentAmount = null;
       		Double balance = -1d;
       		String ret = null;
       		try {
       			ret = http.sendGet(infoUrl + address);
				JSONObject json = new JSONObject(ret);
				balance = json.getDouble("balance");
       		} catch (Exception e) {
       			//e.printStackTrace();
       		}

       		if(balance < 0) {
       			return 0d;
       		}
       		return balance;
	   }
	   
		public double getBalance() {
	        final ExecutorService service;
	        final Future<Double>  task;

	        service = Executors.newFixedThreadPool(1);        
	        task = service.submit(new HttpBalanceThread(address));
	        Double ret = null;
	        try {
	            ret = task.get();
	        } catch(Exception e) {
	        	//Log.e("[Litecoin Balance]", e.toString());
	        	//e.printStackTrace();
	        }
	        return ret;
		}

}