package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.firelab.agame.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Level2 extends Level{
    private final String caption = getString(R.string.Level2Caption);
    private final int number = 2;
    private final int levelSeconds = 30;
    private final int squareCount = 10;
    private final String message = "You must catch " + squareCount + " squares in " + levelSeconds + " seconds";
    private final Bitmap square;
    private int offsetY = 0;
    private int squareX;
    private int squareY = 0;
    private int squareWidth;
    private int squareHeight;
    private Random random = new Random();

    public Level2(Context context) {
        super(context);
        square = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        squareWidth = square.getWidth();
        squareHeight = square.getHeight();
        squareX = 40;
    }

    @Override
    public void update(){
        /*if (squareY >= getLevelHeight() - squareHeight){
            squareY = 0;
            squareX = random.nextInt(getWidth() - squareWidth);
        }*/
        /*long diff = levelEndTime - System.currentTimeMillis();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        int height = getLevelHeight();
        int distanceRemain = height - squareY - squareHeight;
        offsetY = distanceRemain / (int)seconds;
        if (offsetY == 0){
            offsetY = height / levelSeconds;
        }*/
        /*offsetY = 100;
        squareY += offsetY;*/
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        //canvas.drawBitmap(square, squareX, squareY, null);
        //drawAnimation(canvas);
        //drawCounter(canvas);
        /*if (levelState == LevelState.FINISHED){
            clearCanvas(canvas);
        }*/
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getMessage(){return message;}

    @Override
    public int getLevelSeconds(){
        return levelSeconds;
    }

    @Override
    public int getNumber(){
        return number;
    }

}
