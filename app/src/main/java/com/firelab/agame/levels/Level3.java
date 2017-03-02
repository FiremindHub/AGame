package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import com.firelab.agame.R;

import java.util.ArrayList;
import java.util.Random;


public class Level3 extends Level{
    private final String caption = getString(R.string.Level3Caption);
    private final int number = 3;
    private final int levelSeconds = 20;
    private int squareCounter = 0;
    private final int squareCount = 10;
    private final String message = "You must catch " + squareCount + " squares in " + levelSeconds + " seconds";
    private final Bitmap square;
    private int offset = 0;
    private int squareX;
    private int squareY = 0;
    private int squareWidth;
    private int squareHeight;
    private Random random = new Random();
    int tapOffset = 10;
    ArrayList<String> moveDirections = new ArrayList<String>();
    String currentDirection;

    public Level3(Context context) {
        super(context);
        square = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        squareWidth = square.getWidth();
        squareHeight = square.getHeight();
        squareX = 40;
        moveDirections.add("Top2Bottom");
        moveDirections.add("Bottom2Top");
        moveDirections.add("Left2Right");
        moveDirections.add("Right2Left");
        currentDirection = "Top2Bottom";
    }

    @Override
    public synchronized void update(int averageFPS) {
        Log.e("UPDATE BEGIN", currentDirection + " " + String.valueOf(squareX) + " : " + String.valueOf(squareY));
        if (averageFPS == 0) {
            averageFPS = 30;
        }
        switch (currentDirection) {
            case "Top2Bottom":
                MoveFromTopToBottom(averageFPS);
                break;
            case "Bottom2Top":
                MoveFromBottomToTop(averageFPS);
                break;
            case "Left2Right":
                MoveFromLeftToRight(averageFPS);
                break;
            case "Right2Left":
                MoveFromRightToLeft(averageFPS);
                break;
        }
        Log.e("UPDATE END", currentDirection + " " + String.valueOf(squareX) + " : " + String.valueOf(squareY));

        Log.e("LEVELBOUNDS", getLevelWidth() + " : " + getLevelHeight());
    }

    private void MoveFromTopToBottom(int averageFPS){
        if (squareY >= getLevelHeight() - squareHeight){
            ChangeDirection();
            return;
        }
        offset = (getLevelHeight() - squareHeight) / averageFPS;
        squareY += offset;
    }

    private void MoveFromBottomToTop(int averageFPS){
        if (squareY <= 0){
            ChangeDirection();
            return;
        }
        offset = (getLevelHeight() - squareHeight) / averageFPS;
        squareY -= offset;
    }

    private void MoveFromLeftToRight(int averageFPS){
        if (squareX >= getLevelWidth() - squareWidth){
            ChangeDirection();
            return;
        }
        offset = (getLevelWidth() - squareWidth) / averageFPS;
        squareX += offset;
    }

    private void MoveFromRightToLeft(int averageFPS){
        if (squareX <= 0){
            ChangeDirection();
            return;
        }
        offset = (getLevelWidth() - squareWidth) / averageFPS;
        squareX -= offset;
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
                ChangeDirection();
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

    private void ChangeDirection(){
        String changedDirection = currentDirection;
        while (changedDirection == currentDirection){
            changedDirection = moveDirections.get(random.nextInt(moveDirections.size()));
        }
        currentDirection = changedDirection;
        Log.e("UPDATE ChangeDirection", currentDirection + " " + String.valueOf(squareX) + " : " + String.valueOf(squareY));
        switch (currentDirection){
            case "Top2Bottom":
                squareX = random.nextInt(getLevelWidth() - squareWidth);
                squareY = 0;
                break;
            case "Bottom2Top":
                squareX = random.nextInt(getLevelWidth() - squareWidth);
                squareY = getLevelHeight() - squareHeight;
                break;
            case "Left2Right":
                squareX = 0;
                squareY = random.nextInt(getLevelHeight() - squareHeight);
                break;
            case "Right2Left":
                squareX = getLevelWidth() - squareWidth;
                squareY = random.nextInt(getLevelHeight() - squareHeight);
                break;
        }
        Log.e("UPDATE DirectionChanged", currentDirection + " " + String.valueOf(squareX) + " : " + String.valueOf(squareY));
    }

}
