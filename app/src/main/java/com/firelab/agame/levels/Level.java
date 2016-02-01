package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.firelab.agame.GameThread;
import com.firelab.agame.R;
import com.firelab.agame.Square;
import com.firelab.agame.TimeLabel;

public class Level extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private GameThread gameThread;

    public Level(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }
    public String getCaption() {
        return null;
    }
    public String getMessage() {
        return null;
    }
    public int getLevelSeconds() { return 0; }

    public void start(){
        gameThread.setRunning(true);
        gameThread.start();
    }

    protected String getString(int resourceId){
        return context.getString(resourceId);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    public void update(){
        //timeLabel.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        //timeLabel.draw(canvas);
        //square.draw(canvas);
    }
}
