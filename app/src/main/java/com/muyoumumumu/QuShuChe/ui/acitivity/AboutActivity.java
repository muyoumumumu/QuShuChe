package com.muyoumumumu.QuShuChe.ui.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.muyoumumumu.QuShuChe.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

//        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));
//        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点到图标回退
        if(item.getItemId()== android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }


}
