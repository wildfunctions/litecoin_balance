package com.coinhark.litecoinbalance.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_ADDRESSES = "Addresses";
  public static final String TABLE_USER = "User";
  public static final String TABLE_NOTICE = "Notice";

  public static final String ADDRESSES_COLUMN_ID = "_id";
  public static final String ADDRESSES_COLUMN_NAME = "name";
  public static final String ADDRESSES_COLUMN_ADDRESS = "address";
  
  public static final String USER_COLUMN_ID = "_id";
  public static final String USER_COLUMN_CURRENCY = "currency";

  public static final String NOTICE_COLUMN_ID = "_id";
  public static final String NOTICE_COLUMN_NOTICE_DISPLAYED = "noticed_displayed";

  private static final String DATABASE_NAME = "litecoin_balance.db";
  private static final int DATABASE_VERSION = 3;

  // Table creation sql statements
  private static final String CREATE_ADDRESSES_TABLE = "create table "
      + TABLE_ADDRESSES + " (" + ADDRESSES_COLUMN_ID
      + " integer primary key autoincrement, " + ADDRESSES_COLUMN_NAME
      + " text not null, " + ADDRESSES_COLUMN_ADDRESS + " text not null);";
  
  private static final String CREATE_USER_TABLE = "create table "
	      + TABLE_USER + " (" + USER_COLUMN_ID
	      + " integer primary key autoincrement, " + USER_COLUMN_CURRENCY
	      + " integer default 0);";

  private static final String CREATE_NOTICE_TABLE = "create table "
          + TABLE_NOTICE + " (" + NOTICE_COLUMN_ID
          + " integer primary key autoincrement, " + NOTICE_COLUMN_NOTICE_DISPLAYED
          + " integer default 0);";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
	// Create required tables
    db.execSQL(CREATE_ADDRESSES_TABLE);
    db.execSQL(CREATE_USER_TABLE);
    db.execSQL(CREATE_NOTICE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which may destroy all old data");
    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSES);
    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    
    // Create new tables
    //db.execSQL(CREATE_USER_TABLE);
    db.execSQL(CREATE_NOTICE_TABLE);
    //onCreate(db);
  }
  
  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

} 