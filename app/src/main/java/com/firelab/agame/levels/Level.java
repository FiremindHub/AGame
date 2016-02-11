package com.firelab.agame.levels;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.firelab.agame.FontHelper;
import com.firelab.agame.GameThread;
import com.firelab.agame.LevelStartDialog;
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
    private final int milliFactor = 1000;
    long levelEndTime = 0;
    boolean alive = true;
    int timerLabelX = 0;
    int timerLabelY = 0;
    int paintColor = Color.YELLOW;

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

    private String getGoButtonCaption(){
        return getString(R.string.AlertStartButtonCaption);
    }

    public void start(){
        showStartDialog();
    }

    public void startInternal(){
        gameThread.setRunning(true);
        gameThread.start();
        levelEndTime = System.currentTimeMillis() + (getLevelSeconds() * milliFactor);
    }

    private void showStartDialog_old(){
        LevelStartDialog levelStartDialog = new LevelStartDialog();
        levelStartDialog.showDialog(context, getCaption(), getMessage(),
                new Runnable(){
                    public void run(){
                        startInternal();
                    }
                });
    }

    private void showStartDialog(){
        LevelDialog levelStartDialog = new LevelDialog(context);
        levelStartDialog.showDialog(getCaption(), getMessage(), getGoButtonCaption(),
                new Runnable(){
                    public void run(){
                        startInternal();
                    }
                });
    }

    public void stop(){
        gameThread.setRunning(false);
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
        // TODO on stopping level time must be 00:00
        if (!alive){
            stop();
            return;
        }
        super.draw(canvas);
        drawTimer(canvas);
        //timeLabel.draw(canvas);
        //square.draw(canvas);
    }

    public void drawTimer(Canvas canvas){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(paintColor);
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(true);
        paint.setTypeface(Typeface.create(FontHelper.getTypeface(), Typeface.BOLD));

        String value = getTimerValue();

        canvas.drawText(value, getTimerLabelX(value, paint), getTimerLabelY(value, paint), paint);
    }

    private String getTimerValue() {
        long diff = levelEndTime - System.currentTimeMillis();
        if (diff <= 0){
            alive = false;
        }
        //return String.valueOf((float)(diff / milliFactor));

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        diff -= TimeUnit.SECONDS.toMillis(seconds);
        long milliseconds = diff / 100;

        if(seconds < 5) {
            paintColor = Color.RED;
        }

        /*return String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(diff),
                TimeUnit.MILLISECONDS.toSeconds(diff) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));*/
        return String.format("%02d.%02d", seconds, milliseconds);
        //return String.valueOf(diff);
    }

    private int getTimerLabelX(String value, Paint paint){
        if (timerLabelX == 0){
            Rect rect = new Rect();
            paint.getTextBounds(value, 0, value.length(), rect);
            timerLabelX = getWidth() - rect.width() - 10;
        }
        return timerLabelX;
    }

    private int getTimerLabelY(String value, Paint paint){
        if(timerLabelY == 0){
            Rect rect = new Rect();
            paint.getTextBounds(value, 0, value.length(), rect);
            timerLabelY = rect.height() + 10;
        }
        return timerLabelY;
    }

    private String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }
}
