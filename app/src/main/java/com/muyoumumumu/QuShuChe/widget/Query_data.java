package com.muyoumumumu.QuShuChe.widget;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.amumu.QuShuChe.very_important.R;
import com.muyoumumumu.QuShuChe.model.bean.Mnn;
import com.muyoumumumu.QuShuChe.ui.acitivity.Main_activity;

public class Query_data extends AppCompatActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
     	setContentView(R.layout.shuju_chaxun);
		String  name1= Mnn. getInstance().getName_mark()[0];
     	ListView show=(ListView)findViewById(R.id.list_chaxun);

		Cursor cursor = Main_activity.mDb.query(name1, null, null, null, null,null,null);

		if(cursor != null){

			startManagingCursor(cursor);

		}

		SimpleCursorAdapter adapter_2 = new SimpleCursorAdapter(this, R.layout.list_item_chaxun, cursor, new String[]{"_id","direction", "type","systime"}, new int[]{R.id.num_id, R.id.turn, R.id.type,R.id.data});

		assert show != null;
		show.setAdapter(adapter_2);

		stopManagingCursor(cursor);


	}
	
}
