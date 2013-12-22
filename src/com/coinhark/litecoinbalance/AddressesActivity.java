package com.coinhark.litecoinbalance;

import java.util.List;

import com.coinhark.litecoinbalance.db.Address;
import com.coinhark.litecoinbalance.db.AddressDBHandler;
import com.coinhark.litecoinbalance.db.UserSingleton;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class AddressesActivity extends ListActivity {
	
	private static final int DELETE_ID = Menu.FIRST + 1; 

	private List<Address> addressList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addresses);
		
		AddressDBHandler datasource = new AddressDBHandler(this);
	    datasource.open();

	    addressList = UserSingleton.getInstance().addressList;
	    MyAdapter adapter = new MyAdapter(this, R.layout.list, addressList);
	    setListAdapter(adapter);
	    
	    datasource.close();
	    
	    registerForContextMenu(getListView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listmenu, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    menu.add(0, DELETE_ID, 0, R.string.address_remove);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	case DELETE_ID:
	    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    		AddressDBHandler db = new AddressDBHandler(this);
	    		db.open();
	    		db.deleteAddressById(addressList.get((int) info.id).getId());
	    		db.close();
	    	    
	    		Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
	    		startActivity(nextScreen);
	    	return true;
	    }
	    return super.onContextItemSelected(item);
	 }
	  
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.create:
				getAddView();
			break;
			case R.id.menu_home:
				getMainView();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getAddView() {
		Intent nextScreen = new Intent(getApplicationContext(), NewAddressActivity.class);
		startActivity(nextScreen);
	}
	
	public void getMainView() {
		Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(nextScreen);
	}

}
