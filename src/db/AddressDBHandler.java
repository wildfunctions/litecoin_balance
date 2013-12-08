package db;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AddressDBHandler {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_ADDRESS };

  public AddressDBHandler(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Address createAddress(String name, String address) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_NAME, name);
    values.put(MySQLiteHelper.COLUMN_ADDRESS, address);

    long insertId = database.insert(MySQLiteHelper.TABLE_ADDRESSES, null, values);
    
    Cursor cursor = database.query(MySQLiteHelper.TABLE_ADDRESSES,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Address newAddress = cursorToAddress(cursor);
    cursor.close();
    
    return newAddress;
  }

  public void deleteAddress(Address address) {
    long id = address.getId();
    Log.d("[Litecoin Balance]", "Address deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_ADDRESSES, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }
  
  public void deleteAddressById(long id) {
	    Log.d("[Litecoin Balance]", "Address deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_ADDRESSES, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
  }

  public List<Address> getAllAddresses() {
    List<Address> addresses = new ArrayList<Address>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_ADDRESSES,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Address address = cursorToAddress(cursor);
      addresses.add(address);
      cursor.moveToNext();
    }
    cursor.close();
    return addresses;
  }

  private Address cursorToAddress(Cursor cursor) {
	Address address = new Address();
	address.setId(cursor.getLong(0));
	address.setName(cursor.getString(1));
	address.setAddress(cursor.getString(2));
    return address;
  }
  
} 