package com.coinhark.litecoinbalance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.coinhark.litecoinbalance.api.CurrencyEnum;
import com.coinhark.litecoinbalance.db.UserSingleton;
import com.coinhark.litecoinbalance.utils.MathUtils;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BalanceAsyncTask extends AsyncTask<String, Void, String> {
	
    private int mProgressStatus = 10; // To ease user's anxiety while waiting
    private Handler mHandler = new Handler();
    
    private double balance = 0;
    private final ProgressBar progress;
    private final Activity parent;
    
    private UserSingleton singleton;
    
    public BalanceAsyncTask(final Activity parent, final ProgressBar progress) {
        this.parent = parent;
        this.progress = progress;
    }
    
	@Override
	protected String doInBackground(String... addresses) {
		this.singleton = UserSingleton.getInstance(); //Very important to get instance here vs constructor
		double rate = getRate(singleton.currency);
		if(rate > 0) {
			singleton.rate = MathUtils.trimFiat(rate);
		}
		//Log.e("[Litecoin Balance]", rate + "");

		progress.setProgress(mProgressStatus);
    	int i = 0;
        while (mProgressStatus < 100) {
        	double ithBalance = getBalance(singleton.addressList.get(i).getAddress());
        	singleton.addressList.get(i).setBalance(ithBalance);
        	balance += ithBalance; i++;
            mProgressStatus += 100/singleton.addressList.size();

            // Update the progress bar
            mHandler.post(new Runnable() {
                public void run() {
                	progress.setProgress(mProgressStatus);
                }
            });
        }
		return "";

	}
	
    @Override public void onPostExecute(String result) {
    	this.parent.setContentView(R.layout.main);
        TextView t1 = new TextView(parent); 
        TextView t2 = new TextView(parent); 
        
        t1 = (TextView) this.parent.findViewById(R.id.textView2); 
        t1.setText(MathUtils.trimCrypto(this.balance) + " LTC");
        
        t2 = (TextView) this.parent.findViewById(R.id.textView3); 
        t2.setText(MathUtils.trimFiat(this.balance * singleton.rate) + " " + CurrencyEnum.getString(singleton.currency));
    }
	
	public double getBalance(String address) {
        final ExecutorService service;
        final Future<Double> task;

        service = Executors.newFixedThreadPool(1);        
        task = service.submit(new HttpBalanceThread(address));
        Double ret = null;
        try {
            ret = task.get();
        } catch(Exception e) {
        	Log.e("[Litecoin Balance]", e.toString());
        	e.printStackTrace();
        }
        if(ret < 0) {
        	return 0.00;
        }
        return ret;
	}
	
	//Pass in currency value in User table
	public double getRate(int currency) {
        final ExecutorService service;
        final Future<Double> task;

        service = Executors.newFixedThreadPool(1);        
        task = service.submit(new HttpExchangeThread(currency));
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