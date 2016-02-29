package com.firelab.agame;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;

public class LevelButton extends Button {

    private int levelNumber = 0;

    public LevelButton(Context context) {
        super(context);
    }

    public LevelButton(Context context, AttributeSet attrs){
        super(context, attrs);
        int attributeCount = attrs.getAttributeCount();
        for(int i = 0; i < attributeCount; i++) {
            if("levelNumber".equals(attrs.getAttributeName(i))) {
                setLevelNumber(attrs.getAttributeIntValue(i, 0));
            }
        }
        DBHelper dbHelper = new DBHelper(context);
        boolean isLevelUnlocked = dbHelper.getIsLevelUnlocked(getLevelNumber());
        if (!isLevelUnlocked){
            setEnabled(isLevelUnlocked);
            setTextColor(Color.GRAY);
        }
    }

    public int getLevelNumber(){
        return levelNumber;
    }

    public void setLevelNumber(int number){
        levelNumber = number;
    }
}
