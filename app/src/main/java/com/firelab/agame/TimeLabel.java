package com.firelab.agame;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class TimeLabel {

    public TimeLabel(){}

    public void draw(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);

        String now = now();

        Rect rect = new Rect();
        paint.getTextBounds(now, 0, now.length(), rect);

        canvas.drawText(now, 10, rect.height() + 10, paint);
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }
}