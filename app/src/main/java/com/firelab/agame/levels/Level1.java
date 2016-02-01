package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.firelab.agame.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Level1 extends Level {
    private String caption = getString(R.string.Level1Caption);
    private String message = "You must tap 10 squares in 10 seconds";
    private int levelSeconds = 15;
    int x = 100;
    int y  = 100;
    float tapX = 0;
    float tapY = 0;
    int squareWidth = 0;
    int squareHeight = 0;

    public Level1(Context context) {
        super(context);
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
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            tapX = event.getX();
            tapY = event.getY();
            if (tapX >= x && tapX <= x + squareWidth && tapY >= y && tapY <= y + squareHeight) {
                Random rn = new Random();
                x = rn.nextInt(getWidth() - squareWidth);
                y = rn.nextInt(getHeight() - squareHeight);
            }
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        //timeLabel.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Bitmap square = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        squareWidth = square.getWidth();
        squareHeight = square.getHeight();
        canvas.drawBitmap(square, x, y, null);
        drawBounds(canvas);
        //timeLabel.draw(canvas);
        //square.draw(canvas);
    }

    public void drawTime(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);

        String now = now();

        Rect rect = new Rect();
        paint.getTextBounds(now, 0, now.length(), rect);

        canvas.drawText(now, 10, rect.height() + 10, paint);
    }

    public void drawBounds(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);

        String now = now();

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

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }
}
