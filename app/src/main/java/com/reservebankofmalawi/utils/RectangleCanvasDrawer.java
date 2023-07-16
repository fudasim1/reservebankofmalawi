package com.reservebankofmalawi.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RectangleCanvasDrawer {


    public RectangleCanvasDrawer() {

    }

    public void drawRectangle(int left, int top, int right, int bottom, Bitmap bitmap) {

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);
        right = left + right;
        bottom = top + bottom;
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
