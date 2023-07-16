package com.reservebankofmalawi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.reservebankofmalawi.ViewK1000CurrencyActivity;
import com.reservebankofmalawi.ViewK100CurrencyActivity;
import com.reservebankofmalawi.ViewK2000CurrencyActivity;
import com.reservebankofmalawi.ViewK200CurrencyActivity;
import com.reservebankofmalawi.ViewK20CurrencyActivity;
import com.reservebankofmalawi.ViewK5000CurrencyActivity;
import com.reservebankofmalawi.ViewK500CurrencyActivity;
import com.reservebankofmalawi.ViewK50CurrencyActivity;
import com.reservebankofmalawi.adapters.CurrencyAdapter;
import com.reservebankofmalawi.models.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

public class CurrencyActivity extends AppCompatActivity {

    ListView listView;
    List<CurrencyModel> mList;
    CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listView = (ListView) findViewById(R.id.listView);
        mList = new ArrayList<>();

        mAdapter = new CurrencyAdapter(this, mList);
        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position = position+2;
                if(position == 0){
                    startActivity(new Intent(CurrencyActivity.this, ViewK20CurrencyActivity.class));
                }else if(position == 1){
                    startActivity(new Intent(CurrencyActivity.this, ViewK50CurrencyActivity.class));
                }else if(position == 2){
                    startActivity(new Intent(CurrencyActivity.this, ViewK100CurrencyActivity.class));
                }else if(position == 3){
                    startActivity(new Intent(CurrencyActivity.this, ViewK200CurrencyActivity.class));
                }else if(position == 4){
                    startActivity(new Intent(CurrencyActivity.this, ViewK500CurrencyActivity.class));
                }else if(position == 5){
                    startActivity(new Intent(CurrencyActivity.this, ViewK1000CurrencyActivity.class));
                }else if(position == 6){
                    startActivity(new Intent(CurrencyActivity.this, ViewK2000CurrencyActivity.class));
                }else if(position == 7){
                    startActivity(new Intent(CurrencyActivity.this, ViewK5000CurrencyActivity.class));
                }
            }

        });

        showCurrency();
    }

    private void showCurrency() {
        mList.clear();
        //mList.add(new CurrencyModel(R.drawable.k20_front, "20 Kwacha"));
        //mList.add(new CurrencyModel(R.drawable.k50_front, "50 Kwacha"));
        mList.add(new CurrencyModel(R.drawable.k100_front, "100 Kwacha"));
        mList.add(new CurrencyModel(R.drawable.k200_front, "200 Kwacha"));
        mList.add(new CurrencyModel(R.drawable.k500_front, "500 Kwacha"));
        mList.add(new CurrencyModel(R.drawable.k1000_front, "1000 Kwacha"));
        mList.add(new CurrencyModel(R.drawable.k2000_front, "2000 Kwacha"));
        mList.add(new CurrencyModel(R.drawable.k5000_front, "5000 Kwacha"));
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
