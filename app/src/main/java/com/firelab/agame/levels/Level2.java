package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.firelab.agame.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Level2 extends Level{
    private final String caption = getString(R.string.Level2Caption);
    private final int number = 2;
    private final int levelSeconds = 20;
    private int squareCounter = 0;
    private final int squareCount = 5;
    private final String message = "You must catch " + squareCount + " squares in " + levelSeconds + " seconds";
    private final Bitmap square;
    private int offsetY = 0;
    private int squareX;
    private int squareY = 0;
    private int squareWidth;
    private int squareHeight;
    private Random random = new Random();
    int tapOffset = 10;

    public Level2(Context context) {
        super(context);
        square = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        squareWidth = square.getWidth();
        squareHeight = square.getHeight();
        squareX = 40;
    }

    @Override
    public void update(int averageFPS){
        if (squareY >= getLevelHeight() - squareHeight){
            squareY = 0;
            squareX = random.nextInt(getWidth() - squareWidth);
        }
        /*long diff = levelEndTime - System.currentTimeMillis();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        int height = getLevelHeight();
        int distanceRemain = height - squareY - squareHeight;
        offsetY = distanceRemain / (int)seconds;
        if (offsetY == 0){
            offsetY = height / levelSeconds;
        }*/
        if (averageFPS == 0){
            averageFPS = 30;
        }
        offsetY = (getLevelHeight() - squareHeight) / averageFPS;
        squareY += offsetY;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(square, squareX, squareY, null);
        drawCounter(canvas, squareCounter, squareCount);
        //drawAnimation(canvas);
        //drawCounter(canvas);
        /*if (levelState == LevelState.FINISHED){
            clearCanvas(canvas);
        }*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (levelState == LevelState.FINISHED){
            return super.onTouchEvent(event);
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float tapX = event.getX();
            float tapY = event.getY();
            if (tapX >= squareX-tapOffset && tapX <= squareX + squareWidth + tapOffset &&
                    tapY >= squareY-tapOffset && tapY <= squareY + squareHeight + tapOffset) {
                squareCounter++;
                ProcessSquareTap(squareX, squareY);
                if (squareCounter >= squareCount){
                    finish(LevelResult.SUCCESS);
                }
                Random rn = new Random();
                squareX = rn.nextInt(getWidth() - squareWidth);
                squareY = 0;
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){}
        return super.onTouchEvent(event);
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
