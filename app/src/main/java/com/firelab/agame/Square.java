package com.firelab.agame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Square {

    private Bitmap bitmap;
    private int x;
    private int y;
    private long startTime;
    private boolean forward;

    public Square(Bitmap image){
        bitmap = image;
        x = 0;
        y = 600;
        forward = true;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
        long elapsed = (System.nanoTime() - startTime)/1000000;

        //if (elapsed > 10){
        if (x >= 690){
            forward = false;
        }
        if (x <= 0){
            forward = true;
        }
        if (forward) {
            x += 3;
        } else {
            x -= 3;
        }
        startTime = System.nanoTime();
        //}
    }
}