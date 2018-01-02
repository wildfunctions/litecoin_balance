package com.coinhark.litecoinbalance.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyrick on 1/1/18.
 */

public class NoticeDBHandler {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.NOTICE_COLUMN_ID,
            MySQLiteHelper.NOTICE_COLUMN_NOTICE_DISPLAYED
    };

    public NoticeDBHandler(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Notice createNotice(int noticeDisplayed) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.NOTICE_COLUMN_NOTICE_DISPLAYED, noticeDisplayed);

        long insertId = database.insert(MySQLiteHelper.TABLE_NOTICE, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTICE,
                allColumns, MySQLiteHelper.NOTICE_COLUMN_NOTICE_DISPLAYED + " = " + noticeDisplayed, null,
                null, null, null);
        cursor.moveToFirst();
        Notice newNotice = cursorToNotice(cursor);
        cursor.close();

        return newNotice;
    }

    public void deleteAll() {
        Log.d("[Litecoin Balance]", "Delete ALL DATA!!");
        database.delete(MySQLiteHelper.TABLE_NOTICE, MySQLiteHelper.NOTICE_COLUMN_ID + " >= 0 ", null);
    }

    public void deleteNotice(Notice notice) {
        long id = notice.getId();
        Log.d("[Litecoin Balance]", "Notice deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NOTICE, MySQLiteHelper.NOTICE_COLUMN_ID
                + " = " + id, null);
    }

    public void deleteNoticeById(long id) {
        Log.d("[Litecoin Balance]", "Notice deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NOTICE, MySQLiteHelper.NOTICE_COLUMN_ID
                + " = " + id, null);
    }

    public List<Notice> getAllNotices() {
        List<Notice> notices = new ArrayList<Notice>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTICE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Notice notice = cursorToNotice(cursor);
            notices.add(notice);
            cursor.moveToNext();
        }
        cursor.close();
        return notices;
    }

    private Notice cursorToNotice(Cursor cursor) {
        Notice notice = new Notice();
        notice.setNoticeDisplayed(cursor.getInt(0));
        return notice;
    }
}
