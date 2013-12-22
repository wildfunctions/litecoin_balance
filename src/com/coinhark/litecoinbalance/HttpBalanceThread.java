package com.coinhark.litecoinbalance;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.coinhark.litecoinbalance.api.BalanceAPI;
import com.coinhark.litecoinbalance.utils.MathUtils;

import android.util.Log;

public class HttpBalanceThread implements Callable<Double> {

	private String sentUrl = BalanceAPI.getSentUrl();
	private String receivedUrl = BalanceAPI.getReceivedUrl();
	
	private String address;
	double balance = 0;
	double receivedBalance = 0;
	double sentBalance = 0;
	
	   public HttpBalanceThread(String address) {
	       this.address = address;
	   }

	   public Double call() {
       		HttpURLConnecter http = new HttpURLConnecter();
       		String sentAmount = null;
       		try {
       			sentAmount = http.sendGet(sentUrl + address);
       			if(!MathUtils.isNumeric(sentAmount)) {
       				return -1.00;
       			}
       		} catch (Exception e) {
       			//e.printStackTrace();
       		}
       		String receivedAmount = null;
       		try {
       			receivedAmount = http.sendGet(receivedUrl + address);
       			if(!MathUtils.isNumeric(receivedAmount)) {
       				return -1.00;
       			}
       		} catch (Exception e) {
       			//e.printStackTrace();
       		}
       		
       		balance = Double.parseDouble(receivedAmount) - Double.parseDouble(sentAmount);
       		if(balance < 0) {
       			return 0.00;
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
	        	Log.e("[Litecoin Balance]", e.toString());
	        	e.printStackTrace();
	        }
	        return ret;
		}

}