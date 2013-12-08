package com.coinhark.litecoinbalance;

import java.util.ArrayList;
import java.util.List;

import db.Address;
import db.AddressDBHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	
    private ProgressBar mProgress;
    
    private String[] addresses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		
		mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		
		AddressDBHandler db = new AddressDBHandler(this);
		List<Address> addressList = new ArrayList<Address>();
	    db.open();
		addressList = db.getAllAddresses();
		db.close();
		addresses = new String[addressList.size()];
		for(int i = 0; i < addressList.size(); i++) {
			addresses[i] = addressList.get(i).getAddress();
		}
		if(addressList.size() > 0) {
			new BalanceAsyncTask(this, mProgress, addresses).execute("");
		} else {
			setContentView(R.layout.main);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_addresses:
				getAddressesView();
				break;
			case R.id.menu_donate:
				getDonateView();
				break;
			case R.id.menu_refresh:
				getMainView();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getAddressesView() {
		Intent nextScreen = new Intent(getApplicationContext(), AddressesActivity.class);
		startActivity(nextScreen);
	}
	
	public void getDonateView() {
		Intent nextScreen = new Intent(getApplicationContext(), DonateActivity.class);
		startActivity(nextScreen);
	}
	
	public void getMainView() {
		Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(nextScreen);
	}

}
