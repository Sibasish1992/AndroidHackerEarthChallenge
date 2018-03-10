package com.payu.hackerearth.kickstart.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.payu.hackerearth.kickstart.R;

/**
 * Created by Sibasish Mohanty on 11/08/17.
 */

public class SpalshActivity extends AppCompatActivity {
    private static String LOG_TAG = "SpalshActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread timerThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(),ListingActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    Toast.makeText(SpalshActivity.this, R.string.something_wrong, Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        };
        timerThread.start();
    }

}
