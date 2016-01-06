package com.firelab.agame.levels;

import com.firelab.agame.R;

public class Level1 extends Level {
    private String caption = getString(R.string.Level1Caption);
    private int levelSeconds = 15;

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public int getLevelSeconds(){
        return levelSeconds;
    }
}
