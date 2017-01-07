package com.firelab.agame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SquareTapAnimation {

    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private Bitmap spreadSheet;
    private long delay;
    private boolean playedOnce;
    private boolean started;

    public SquareTapAnimation(Context context){
        spreadSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.square_terminated);
        int imageWidth = spreadSheet.getWidth();
        int imageHeight = spreadSheet.getHeight();
        delay = 50;
        frames = new Bitmap[5];
        for (int i = 0; i < 5; i++){
            frames[i] = Bitmap.createBitmap(spreadSheet, 0, i*(imageHeight/5), imageWidth, (imageHeight/5));
        }
        started = false;
    }

    public void Start(){
        playedOnce = false;
        currentFrame = 0;
        startTime = System.nanoTime();
        started = true;
    }

    public void update(){
        if (playedOnce){
            return;
        }

        long elapsed = (System.nanoTime() - startTime)/1000000;

        if (elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }

        if (currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
            started = false;
        }
    }

    public Bitmap getImage(){
        if (playedOnce == true || !started){
            return null;
        }
        return frames[currentFrame];
    }
}
