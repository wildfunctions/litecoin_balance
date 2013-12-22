package com.coinhark.litecoinbalance.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDBHandler {

	  // Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = {
			  MySQLiteHelper.USER_COLUMN_ID,
			  MySQLiteHelper.USER_COLUMN_CURRENCY
	  };

	  public UserDBHandler(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public User createUser(String currency) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.USER_COLUMN_CURRENCY, currency);

	    long insertId = database.insert(MySQLiteHelper.TABLE_USER, null, values);
	    
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
	        allColumns, MySQLiteHelper.USER_COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    User newUser = cursorToUser(cursor);
	    cursor.close();
	    
	    return newUser;
	  }

	  public void deleteAll() {
		  Log.d("[Litecoin Balance]", "Delete ALL DATA!!");
		  database.delete(MySQLiteHelper.TABLE_USER, MySQLiteHelper.USER_COLUMN_ID + " > 0 ", null);
	  }
	  
	  public void deleteAddress(Address address) {
	    long id = address.getId();
	    Log.d("[Litecoin Balance]", "Address deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_USER, MySQLiteHelper.USER_COLUMN_ID
	        + " = " + id, null);
	  }
	  
	  public void deleteAddressById(long id) {
		    Log.d("[Litecoin Balance]", "Address deleted with id: " + id);
		    database.delete(MySQLiteHelper.TABLE_USER, MySQLiteHelper.USER_COLUMN_ID
		        + " = " + id, null);
	  }

	  public List<User> getAllUsers() {
		//Should only ever be of size 1
	    List<User> users = new ArrayList<User>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      User user = cursorToUser(cursor);
	      users.add(user);
	      cursor.moveToNext();
	    }
	    cursor.close();
	    return users;
	  }

	  private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setCurrency(cursor.getInt(1));
	    return user;
	  }
	  
} 