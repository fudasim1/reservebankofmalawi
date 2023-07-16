package com.reservebankofmalawi;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private int PERMISSIONS_REQUEST_CODE = 100;

    FragmentNews fragmentNews;
    FragmentCurrency fragmentCurrency;
    FragmentScan fragmentScan;
    FragmentExchange fragmentExchange;

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    Context mContext;
    BottomSheetDialog bottomSheetDialog;

    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        bottomSheetDialog = new BottomSheetDialog(mContext);


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(mListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);


    }

    private BottomNavigationView.OnItemSelectedListener mListener = new BottomNavigationView.OnItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.updates:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.currency:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.scan:
                    viewPager.setCurrentItem(2);
                    return true;

                case R.id.exchange:
                    viewPager.setCurrentItem(3);
                    return true;


            }
            return false;
        }
    };

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            switch (position) {
                case 0:
                    break;

                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        fragmentNews = new FragmentNews();
        fragmentCurrency = new FragmentCurrency();
        fragmentScan = new FragmentScan();
        fragmentExchange = new FragmentExchange();

        adapter.addFragment(fragmentNews);
        adapter.addFragment(fragmentCurrency);
        adapter.addFragment(fragmentScan);
        adapter.addFragment(fragmentExchange);
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

    }


    public void viewCurrency(View v) {
        startActivity(new Intent(mContext, CurrencyActivity.class));
    }

    public void scanCurrency(View v) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestAppPermissions();
        } else {
            startActivity(new Intent(mContext, ScanActivity.class));
        }

    }

    public void exchangeCurrency(View v) {
        startActivity(new Intent(mContext, ExchangeActivity.class));
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestAppPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
        } else {
            startActivity(new Intent(mContext, ScanActivity.class));
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestAppPermissions();
            }
        }

    }


    private void showAppAbout() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("About Us");
        mBuilder.setMessage("Visit our website to view info about us");
        mBuilder.setPositiveButton("Visit Site", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Uri uri = Uri.parse("https://www.rbm.mw/About/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mBuilder.create();
        mBuilder.show();
    }



    private void showContact() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Contacts");
        mBuilder.setMessage("For any inqueries, please feel free to contact us");
        mBuilder.setPositiveButton("Contact Us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Uri uri = Uri.parse("https://www.rbm.mw/Home/Contact/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mBuilder.create();
        mBuilder.show();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about:
                showAppAbout();
                return true;

            case R.id.report:
                startActivity(new Intent(HomeActivity.this, ReportActivity.class));
                return true;

            case R.id.contacts:
                showContact();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
