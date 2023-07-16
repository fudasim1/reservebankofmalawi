package com.reservebankofmalawi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.reservebankofmalawi.utils.RectangleCanvasDrawer;


public class ViewK20CurrencyActivity extends AppCompatActivity {

    ImageView currency_photo;

    Context mContext;


    boolean isFlippedFront = true;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;

    RectangleCanvasDrawer rectCanvas = new RectangleCanvasDrawer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_k20_currency);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_light);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        currency_photo = findViewById(R.id.currency_photo);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);


        layouts = new int[]{
                R.layout.k2000_front_security_1,
                R.layout.k2000_front_security_2,
                R.layout.k2000_front_security_3,
                R.layout.k2000_front_security_4};

        addBottomDots(0);

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Bitmap finalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k20_front);
        showBanknoteAndDrawRegions(finalBitmap);
    }

    private void showBanknoteAndDrawRegions(Bitmap rawBitmap) {
        Bitmap finalBitmap = rawBitmap.copy(Bitmap.Config.ARGB_8888, true);

        if (isFlippedFront) {
            rectCanvas.drawRectangle(155, 60, 120, 710, finalBitmap);
            rectCanvas.drawRectangle(1195, 560, 440, 220, finalBitmap);
            rectCanvas.drawRectangle(1330, 220, 230, 280, finalBitmap);
            rectCanvas.drawRectangle(1060, 240, 230, 280, finalBitmap);
        }else{
            rectCanvas.drawRectangle(130, 125, 260, 400, finalBitmap);
            rectCanvas.drawRectangle(700, 30, 100, 710, finalBitmap);
            rectCanvas.drawRectangle(815, 250, 100, 310, finalBitmap);
        }

        currency_photo.setImageBitmap(finalBitmap);
    }

    private void isFlipCurrency() {
        if (!isFlippedFront) {
            isFlippedFront = true;
            Bitmap finalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k20_front);
            showBanknoteAndDrawRegions(finalBitmap);
            Toast.makeText(mContext, "Bank Note Flipped To Front Side", Toast.LENGTH_SHORT).show();
        } else {
            isFlippedFront = false;
            Bitmap finalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k20_back);
            showBanknoteAndDrawRegions(finalBitmap);
            Toast.makeText(mContext, "Bank Note Flipped To Back Side", Toast.LENGTH_SHORT).show();
        }

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private ViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_currency, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

            case R.id.rotate:
                /*if (currency_photo.getOrientation() < 1) {
                    currency_photo.setOrientation(90);
                    Toast.makeText(mContext, "Bank Note Rotated To Landscape", Toast.LENGTH_SHORT).show();
                } else {
                    currency_photo.setOrientation(0);
                    Toast.makeText(mContext, "Bank Note Rotated To Portrait", Toast.LENGTH_SHORT).show();
                }*/
                break;

            case R.id.flip:
                isFlipCurrency();
                break;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
