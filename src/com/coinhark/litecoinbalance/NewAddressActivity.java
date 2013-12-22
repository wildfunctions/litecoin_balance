package com.coinhark.litecoinbalance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coinhark.litecoinbalance.db.AddressDBHandler;
import com.coinhark.litecoinbalance.utils.AddressChecker;

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
		if("".equals(name)) {
			name = "Balance";
		}
		AddressChecker ac = new AddressChecker();
        if(!ac.run(address)) {
        	Log.d("[Litecoin Balance]", "Invalid Address!");
        	
            TextView t = new TextView(this); 
            t = (TextView) this.findViewById(R.id.address_error); 
        	t.setText("Invalid Address!");
        } else {
        	AddressDBHandler datasource = new AddressDBHandler(this);
    		datasource.open();
    	    datasource.createAddress(name, address);
    	    datasource.close();

    		Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
    		startActivity(nextScreen);
        }
	}
	
}
