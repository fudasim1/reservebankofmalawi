package com.reservebankofmalawi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.reservebankofmalawi.HomeActivity;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.postDelayed(postTask, 4000);
    }

    Runnable postTask = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }
    };


    @Override
    protected void onDestroy() {
        handler.removeCallbacks(postTask);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
