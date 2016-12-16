package com.muyoumumumu.QuShuChe.ui.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.muyoumumumu.QuShuChe.R;


public class Welcome extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        setContentView(R.layout.welcome);

        //Display the current version number
//        PackageManager pm = getPackageManager();
//        try {
//            PackageInfo pi = pm.getPackageInfo("com.example.tdcs", 0);
//            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
//            versionNumber.setText("Version " + pi.versionName);
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(Welcome.this, Main_activity.class);
                Welcome.this.startActivity(mainIntent);
                Welcome.this.finish();
            }
        }, 1500); //2900 for release

    }
}