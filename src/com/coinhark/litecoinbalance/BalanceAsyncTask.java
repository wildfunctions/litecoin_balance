package com.coinhark.litecoinbalance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BalanceAsyncTask extends AsyncTask<String, Void, String> {
	
    private int mProgressStatus = 10;
    private Handler mHandler = new Handler();
    
    private double balance = 0;
    private final ProgressBar progress;
    private final Activity parent;
    private final String[] addresses;
    
    public BalanceAsyncTask(final Activity parent, final ProgressBar progress, String[] addresses) {
        this.parent = parent;
        this.progress = progress;
        this.addresses = addresses;
    }
    
	@Override
	protected String doInBackground(String... addresses) {
		
    	int i = 0;
        while (mProgressStatus < 100) {
        	balance += getBalance(this.addresses[i]); i++;
            mProgressStatus += 100/this.addresses.length;

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