package com.coinhark.litecoinbalance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import db.AddressSingleton;

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
    
    private AddressSingleton singleton;
    
    public BalanceAsyncTask(final Activity parent, final ProgressBar progress) {
        this.parent = parent;
        this.progress = progress;
    }
    
	@Override
	protected String doInBackground(String... addresses) {
		singleton = AddressSingleton.getInstance();
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
        TextView t = new TextView(parent); 

        t = (TextView) this.parent.findViewById(R.id.textView2); 
        t.setText(this.balance + " LTC");
    }
	
	public double getBalance(String address) {
        final ExecutorService service;
        final Future<Double>  task;

        service = Executors.newFixedThreadPool(1);        
        task = service.submit(new HttpThread(address));
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

}