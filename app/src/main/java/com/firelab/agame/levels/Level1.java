package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.firelab.agame.R;

public class Level1 extends Level {
    private String caption = getString(R.string.Level1Caption);
    private String message = "You must tap 10 squares in 10 seconds";
    private int levelSeconds = 15;

    public Level1(Context context) {
        super(context);
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

    public void update(){
        //timeLabel.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Bitmap square = BitmapFactory.decodeResource(getResources(), R.drawable.square);
        int x = 300;
        int y = 300;
        canvas.drawBitmap(square, x, y, null);
        //timeLabel.draw(canvas);
        //square.draw(canvas);
    }
}
