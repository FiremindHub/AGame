package com.firelab.agame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.firelab.agame.levels.Level;

public class GameThread extends Thread
{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private Level level;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, Level level) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.level = level;
    }

    @Override
    public void run() {
        long startTime = 0;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.level.update();
                    this.level.draw(canvas);
                }
            } catch(Exception e){}
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        timeMillis = (System.nanoTime() - startTime) / 1000000;
        waitTime = targetTime - timeMillis;
        try{
            this.sleep(waitTime);
        }catch(Exception e){}
        totalTime += System.nanoTime() - startTime;
        frameCount++;
        if (frameCount == FPS) {
            averageFPS = 1000/((totalTime/frameCount)/100000);
            frameCount = 0;
            totalTime = 0;
        }
    }

    public void setRunning(boolean value){
        running = value;
    }
}
