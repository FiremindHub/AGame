package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.firelab.agame.FontHelper;
import com.firelab.agame.GameThread;
import com.firelab.agame.R;
import com.firelab.agame.Square;
import com.firelab.agame.TimeLabel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Level extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private GameThread gameThread;
    private int width = 0;
    private int height = 0;
    private final long nanoFactor = 1000000;
    private final int milliFactor = 1000;
    long levelEndTime = 0;

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
    public int getLevelHeight(){return height;}
    public int getLevelWidth(){return width;}

    public void start(){
        gameThread.setRunning(true);
        gameThread.start();
        levelEndTime = System.currentTimeMillis() + (getLevelSeconds() * milliFactor);
    }

    protected String getString(int resourceId){
        return context.getString(resourceId);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
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
        drawTimer(canvas);
        //timeLabel.draw(canvas);
        //square.draw(canvas);
    }

    public void drawTimer(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(FontHelper.getTypeface());

        String value = getTimerValue();

        Rect rect = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect);

        canvas.drawText(value, getWidth() - rect.width() - 10, rect.height() + 10, paint);
    }

    private String getTimerValue() {
        long diff = levelEndTime - System.currentTimeMillis();
        //return String.valueOf((float)(diff / milliFactor));
        /*return String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(diff),
                TimeUnit.MILLISECONDS.toSeconds(diff) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));*/
        return String.valueOf(diff);

    }

    private String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }
}
