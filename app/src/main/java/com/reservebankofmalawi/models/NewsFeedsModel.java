package com.reservebankofmalawi.models;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsFeedsModel {

    private String postMessage;
    private String postPhoto;
    private String postTime;

    public NewsFeedsModel(String postMessage, String postPhoto, String postTime) {
        this.postMessage = postMessage;
        this.postPhoto = postPhoto;
        this.postTime = postTime;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public String getPostPhoto() {
        return postPhoto;
    }

    public String getPostTime() {

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.US);
            Date date1 = df.parse(postTime.replace("+0000", ".000+0000Z"));
            DateFormat outputFormatter1 = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);
            String output1 = outputFormatter1.format(date1); //
            return output1;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Error ", e.toString());
        }
        return postTime;
    }
}
