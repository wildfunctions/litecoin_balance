package com.coinhark.litecoinbalance;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.coinhark.litecoinbalance.db.AddressDBHandler;
import com.coinhark.litecoinbalance.db.User;
import com.coinhark.litecoinbalance.db.UserDBHandler;
import com.coinhark.litecoinbalance.db.UserSingleton;

public class MainActivity extends Activity {
	
    private ProgressBar mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		UserSingleton singleton = UserSingleton.getInstance();
		
		UserDBHandler userDB = new UserDBHandler(this);
		userDB.open();
		List<User> users = userDB.getAllUsers();
		userDB.close();
		if(users.size() > 0) {
			singleton.currency = 0;
			Log.w("[Litecoin Balance]", users.size() + " Get User Info!");
		}
		
		mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		
		AddressDBHandler db = new AddressDBHandler(this);
	    db.open();
		singleton.addressList = db.getAllAddresses();
		db.close();

		if(singleton.addressList.size() > 0) {
			new BalanceAsyncTask(this, mProgress).execute("");
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
