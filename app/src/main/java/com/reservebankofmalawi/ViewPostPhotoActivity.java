package com.reservebankofmalawi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ViewPostPhotoActivity extends AppCompatActivity {

    ImageView post_placeholder;
    SubsamplingScaleImageView post_photo;

    String postPhoto;
    Bitmap postBitmap;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post_photo);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_light);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");

        }


        post_placeholder = findViewById(R.id.post_placeholder);
        post_photo = findViewById(R.id.post_photo);

        postPhoto = getIntent().getStringExtra("post_photo");
        postBitmap = getIntent().getParcelableExtra("post_bitmap");


        showPostPhoto();
    }

    private void showPostPhoto() {
        if (postBitmap != null) {
            Glide.with(mContext).load(bitmapToByte(postBitmap)).asBitmap().thumbnail(0.1f).transform(new BlurTransformation(mContext, 3, 3)).into(post_placeholder);
        }

        Glide.with(mContext).load(postPhoto).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                post_photo.setImage(ImageSource.bitmap(bitmap));
                post_placeholder.setVisibility(View.GONE);

            }
        });
    }

    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
