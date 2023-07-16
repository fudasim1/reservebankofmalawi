package com.reservebankofmalawi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.reservebankofmalawi.adapters.NewsFeedsAdapter;
import com.reservebankofmalawi.models.NewsFeedsModel;

import java.util.List;

public class SecurityFeaturesActivity extends AppCompatActivity {

    ListView listView;
    List<NewsFeedsModel> mList;
    NewsFeedsAdapter mAdapter;
    Context mContext;

    int currencyPosition;
    String currencyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_features);

        mContext = this;

        currencyPosition = getIntent().getIntExtra("currency_position", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_light);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Security Features");
            getSupportActionBar().setSubtitle(currencyName);

        }


        listView = (ListView) findViewById(R.id.listView);
        getCurrencyPhoto(currencyPosition);
    }

    private void getCurrencyPhoto(int position) {
        /*if (position == 0) {
            currencyFrontName = "MK 20 - Front";
            currencyBackName = "MK 20 - Back";
            currencyFrontPhoto = R.drawable.k20_front;
            currencyBackPhoto = R.drawable.k20_back;

        } else if (position == 1) {
            currencyFrontName = "MK 50 - Front";
            currencyBackName = "MK 50 - Back";
            currencyFrontPhoto = R.drawable.k50_front;
            currencyBackPhoto = R.drawable.k50_back;

        } else if (position == 2) {
            currencyFrontName = "MK 100 - Front";
            currencyBackName = "MK 100 - Back";
            currencyFrontPhoto = R.drawable.k100_front;
            currencyBackPhoto = R.drawable.k100_back;

        } else if (position == 3) {
            currencyFrontName = "MK 200 - Front";
            currencyBackName = "MK 200 - Back";
            currencyFrontPhoto = R.drawable.k200_front;
            currencyBackPhoto = R.drawable.k200_back;

        } else if (position == 4) {
            currencyFrontName = "MK 500 - Front";
            currencyBackName = "MK 500 - Back";
            currencyFrontPhoto = R.drawable.k500_front;
            currencyBackPhoto = R.drawable.k500_back;
        } else if (position == 5) {
            currencyFrontName = "MK 1000 - Front";
            currencyBackName = "MK 1000 - Back";
            currencyFrontPhoto = R.drawable.k1000_front;
            currencyBackPhoto = R.drawable.k1000_back;
        } else if (position == 6) {
            currencyFrontName = "MK 2000 - Front";
            currencyBackName = "MK 2000 - Back";
            currencyFrontPhoto = R.drawable.k2000_front;
            currencyBackPhoto = R.drawable.k2000_back;
        } else {
            currencyFrontName = "MK 5000 - Front";
            currencyBackName = "MK 5000 - Back";
            currencyFrontPhoto = R.drawable.k500_front;
            currencyBackPhoto = R.drawable.k500_back;
        }
    */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
