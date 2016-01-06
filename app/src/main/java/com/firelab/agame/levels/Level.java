package com.firelab.agame.levels;

import android.content.Context;

public class Level {
    private Context context;

    public void Level(Context context){
        this.context = context;
    }
    public String getCaption() {
        return null;
    }

    public int getLevelSeconds(){
        return 0;
    }

    protected String getString(int resourceId){
        return context.getString(resourceId);
    }
}
