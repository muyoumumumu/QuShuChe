package com.muyoumumumu.QuShuChe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Traffic_db extends SQLiteOpenHelper {

	public Traffic_db(Context context) {
		super(context, "notes", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+TABLE_NAME_data+"(" +
				COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
				COLUMN_NAME_data_NAME+" TEXT NOT NULL DEFAULT \"\"," +
				COLUMN_NAME_data_REMARK +" TEXT DEFAULT \"\"," 	+
				COLUMN_NAME_data_DATE+" TEXT NOT NULL DEFAULT \"\"" +
				")");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public static final String TABLE_NAME_data = "data";
	public static final String COLUMN_NAME_ID = "_id";
	public static final String COLUMN_NAME_data_NAME = "name";
	public static final String COLUMN_NAME_data_REMARK = "remark";
	public static final String COLUMN_NAME_data_DATE = "date";



}
