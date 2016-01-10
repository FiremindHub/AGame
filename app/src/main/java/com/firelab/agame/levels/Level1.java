package com.firelab.agame.levels;

import android.content.Context;

import com.firelab.agame.R;

public class Level1 extends Level {
    private String caption = getString(R.string.Level1Caption);
    private int levelSeconds = 15;

    public Level1(Context context) {
        super(context);
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public int getLevelSeconds(){
        return levelSeconds;
    }
}
