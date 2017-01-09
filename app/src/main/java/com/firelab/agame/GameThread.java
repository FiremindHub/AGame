package com.firelab.agame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.firelab.agame.levels.Level;
import com.firelab.agame.levels.LevelResult;

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
        long finishTime = System.currentTimeMillis() + (level.getLevelSeconds() * level.getMilliFactor());
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
            } catch(Exception e){
                Log.e("LOCKCANVAS_Exception", e.getMessage() + ": ");
            }
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            if (waitTime > 0){
                try{
                    this.sleep(waitTime);
                    Log.e("GAMETHREAD_SLEEP", String.valueOf(waitTime));
                }catch(Exception e){
                    Log.e("GAMETHREAD_Exception", e.getMessage() + ": " + waitTime);
                }
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                Log.e("AVERAGE_FPS", String.valueOf(averageFPS));
                frameCount = 0;
                totalTime = 0;
            }
            if (System.currentTimeMillis() > finishTime){
                level.SendFinishMessage(LevelResult.FAILED);
                setRunning(false);
            }
        }
        level = null;
        canvas = null;
        surfaceHolder = null;
    }

    public void setRunning(boolean value){
        running = value;
    }
}
