package com.coinhark.litecoinbalance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import db.AddressDBHandler;

public class NewAddressActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_address);
	}
	
	public void create(View view) {
		View nameView = this.findViewById(R.id.name);
		View addressView = this.findViewById(R.id.address);
		String name = ((EditText) nameView).getText().toString();
		String address = ((EditText) addressView).getText().toString();
		
		HttpThread http = new HttpThread(address);
		double balance = http.getBalance();
		Log.d("[Litecoin Balance]", balance + " <--- Balance");
		
        if(balance < 0 || "".equals(address)) {
        	Log.d("[Litecoin Balance]", "Empty");
        } else {
        	AddressDBHandler datasource = new AddressDBHandler(this);
    		datasource.open();
    	    datasource.createAddress(name, address);
    	    datasource.close();
    		Intent nextScreen = new Intent(getApplicationContext(), AddressesActivity.class);
    		startActivity(nextScreen);
        }
	}
	
}
