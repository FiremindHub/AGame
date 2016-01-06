package com.firelab.agame.levels;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Level extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;

    public Level(Context context){
        super(context);
        this.context = context;
    }
    public String getCaption() {
        return null;
    }
    public int getLevelSeconds() { return 0; }

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

    }
}
