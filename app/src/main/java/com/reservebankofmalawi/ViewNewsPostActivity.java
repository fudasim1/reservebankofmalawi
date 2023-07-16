package com.reservebankofmalawi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.reservebankofmalawi.ViewPostPhotoActivity;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ViewNewsPostActivity extends AppCompatActivity {
    ImageView post_placeholder;
    ImageView post_photo;
    TextView post_message;
    TextView post_time;


    String postPhoto;
    Bitmap postBitmap;
    String postMessage;
    String postTime;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news_post);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_light);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("View Post");

        }

        post_placeholder = findViewById(R.id.post_placeholder);
        post_photo = findViewById(R.id.post_photo);
        post_message = findViewById(R.id.post_message);
        post_time = findViewById(R.id.post_time);

        postPhoto = getIntent().getStringExtra("post_photo");
        postBitmap = getIntent().getParcelableExtra("post_bitmap");
        postMessage = getIntent().getStringExtra("post_message");
        postTime = getIntent().getStringExtra("post_time");

        showNewsPost();
    }

    private void showNewsPost() {
        if(postPhoto != null) {
            post_placeholder.setVisibility(View.VISIBLE);
            post_photo.setVisibility(View.VISIBLE);

            post_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ViewNewsPostActivity.this, ViewPostPhotoActivity.class);
                    intent.putExtra("post_photo", postPhoto);
                    intent.putExtra("post_bitmap", postBitmap);
                    startActivity(intent);
                }
            });

            if (postBitmap != null) {
                Glide.with(mContext).load(bitmapToByte(postBitmap)).asBitmap().thumbnail(0.1f).transform(new BlurTransformation(this, 3, 3)).into(post_placeholder);
            }

            Glide.with(mContext).load(postPhoto).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    post_photo.setImageBitmap(bitmap);
                    post_placeholder.setVisibility(View.GONE);
                }
            });
        }else{
            post_placeholder.setVisibility(View.GONE);
            post_photo.setVisibility(View.GONE);
        }

        if(postMessage != null){
            post_message.setVisibility(View.VISIBLE);
            post_message.setText(postMessage);
        }else{
            post_message.setVisibility(View.GONE);
        }

        post_time.setText(postTime);
    }

    private byte[] bitmapToByte(Bitmap bitmap){
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
