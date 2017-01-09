package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;

import com.firelab.agame.FontHelper;
import com.firelab.agame.R;
import com.firelab.agame.SquareTapAnimation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Level1 extends Level {
    private String caption = getString(R.string.Level1Caption);
    private final int number = 1;
    private int levelSeconds = 10;
    private int squareCounter = 0;
    private int squareCount = 10;
    private String message = "You must tap " + squareCount + " squares in " + levelSeconds + " seconds";
    int x = 100;
    int y  = 100;
    int animationX = x;
    int animationY = y;
    float tapX = 0;
    float tapY = 0;
    int squareWidth = 0;
    int squareHeight = 0;
    int tapOffset = 10;
    private Bitmap square;
    private SquareTapAnimation squareTapAnimation;
    private Paint counterPaint;
    private Rect counterRect;

    final int MAX_STREAMS = 5;

    SoundPool soundPool;
    int soundIdSquareClick;

    public Level1(Context context) {
        super(context);
        squareTapAnimation = new SquareTapAnimation(context);
        square = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        squareWidth = square.getWidth();
        squareHeight = square.getHeight();
        soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        //soundPool.setOnLoadCompleteListener(context);
        try {
            soundIdSquareClick = soundPool.load(context.getAssets().openFd("SquareClick2.ogg"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        counterPaint = CreateCounterPaint();
        counterRect = CreateCounterRect();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (levelState == LevelState.FINISHED){
            return super.onTouchEvent(event);
        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            tapX = event.getX();
            tapY = event.getY();
            if (tapX >= x-tapOffset && tapX <= x + squareWidth + tapOffset &&
                    tapY >= y-tapOffset && tapY <= y + squareHeight + tapOffset) {
                squareCounter++;
                PlaySound();
                animationX = x;
                animationY = y;
                squareTapAnimation.Start();

                if (squareCounter >= squareCount){
                    finish(LevelResult.SUCCESS);
                }
                Random rn = new Random();
                x = rn.nextInt(getWidth() - squareWidth);
                y = rn.nextInt(getHeight() - squareHeight);
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){}
        return super.onTouchEvent(event);
    }

    private void PlaySound(){
        soundPool.play(soundIdSquareClick, 1, 1, 0, 0, 1);

    }

    public void update(){
        //timeLabel.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        //squareWidth = square.getWidth();
        //squareHeight = square.getHeight();
        canvas.drawBitmap(square, x, y, null);
        drawAnimation(canvas);
        //drawBounds(canvas);
        drawCounter(canvas);
        //timeLabel.draw(canvas);
        //square.draw(canvas);
        if (levelState == LevelState.FINISHED){
            clearCanvas(canvas);
        }
    }

    private void drawAnimation(Canvas canvas){
        Bitmap squareTerminatedFrame = squareTapAnimation.getImage();
        if (squareTerminatedFrame == null) {
            return;
        }
        if (levelState == LevelState.FINISHED){
            return;
        }
        canvas.drawBitmap(squareTerminatedFrame, animationX, animationY, null);
        squareTapAnimation.update();
    }


    private void drawCounter(Canvas canvas){
        String counterString = String.valueOf(squareCounter) + "/" + String.valueOf(squareCount);
        counterPaint.getTextBounds(counterString, 0, counterString.length(), counterRect);
        canvas.drawText(counterString, 10, counterRect.height() + 10, counterPaint);
    }

    private Paint CreateCounterPaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.create(FontHelper.getTypeface(), Typeface.BOLD));
        return paint;
    }

    private Rect CreateCounterRect(){
        Rect rect = new Rect();

        return rect;
    }

    public void drawBounds(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);

        Rect rect = new Rect();

        String boundsString = String.valueOf(getWidth()) + ";" + String.valueOf(getHeight());
        String squareString = String.valueOf(x) + ";" + String.valueOf(y);
        String tapString = String.valueOf(tapX) + ";" + String.valueOf(tapY);
        String squareSizeString = String.valueOf(squareWidth) + ";" + String.valueOf(squareHeight);

        paint.getTextBounds(boundsString, 0, boundsString.length(), rect);
        canvas.drawText(boundsString, 10, rect.height() + 10, paint);

        paint.getTextBounds(squareString, 0, squareString.length(), rect);
        canvas.drawText(squareString, 10, rect.height() + 40, paint);

        paint.getTextBounds(tapString, 0, tapString.length(), rect);
        canvas.drawText(tapString, 10, rect.height() + 70, paint);

        /*paint.getTextBounds(squareSizeString, 0, squareSizeString.length(), rect);
        canvas.drawText(squareSizeString, 10, rect.height() + 110, paint);*/
    }


}
