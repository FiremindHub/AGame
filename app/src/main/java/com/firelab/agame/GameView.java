package com.firelab.agame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameThread gameThread;
    private int x = 0;
    public TimeLabel timeLabel;
    public Square square;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                gameThread.setRunning(false);
                gameThread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
                retry = false;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        timeLabel = new TimeLabel();
        square = new Square(BitmapFactory.decodeResource(getResources(), R.drawable.square));
    }

    public void start(){
        gameThread.setRunning(true);
        gameThread.start();
    }

    public void update(){
        //timeLabel.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        timeLabel.draw(canvas);
        square.draw(canvas);
    }
}
