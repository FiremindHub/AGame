package com.firelab.agame.levels;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.firelab.agame.DBHelper;
import com.firelab.agame.FontHelper;
import com.firelab.agame.GameActivity;
import com.firelab.agame.GameThread;
import com.firelab.agame.LevelSelectActivity;
import com.firelab.agame.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

enum LevelState {
    LOADED,
    STARTED,
    FINISHED,
    NONE
}

enum LevelResult {
    SUCCESS,
    FAILED,
    NONE
}

enum LevelLockState{
    LOCKED,
    UNLOCKED
}

public class Level extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    private GameThread gameThread;
    private int width = 0;
    private int height = 0;
    private final int milliFactor = 1000;
    long levelEndTime = 0;
    int timerLabelX = 0;
    int timerLabelY = 0;
    int paintColor = Color.YELLOW;
    LevelState levelState;
    LevelResult levelResult = LevelResult.NONE;
    long diff = getLevelSeconds() * milliFactor;
    Paint timerPaint;
    Rect timerRect;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if (levelState == LevelState.FINISHED){
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish(LevelResult.FAILED);
        }
    };

    public Level(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
        levelState = LevelState.LOADED;
        timerPaint = CreateTimerPaint();
    }

    public String getCaption() {
        return null;
    }
    public String getMessage() {
        return null;
    }
    public int getNumber(){
        return 0;
    }
    private int getNextLevelNumber(){
        return getNumber() + 1;
    }
    public int getLevelSeconds() { return 0; }
    public int getLevelHeight(){return height;}
    public int getLevelWidth(){return width;}

    private String getGoButtonCaption(){
        return getString(R.string.DialogGoButtonCaption);
    }

    private String getNextButtonCaption(){
        return getString(R.string.DialogNextButtonCaption);
    }

    public void start(){
        showStartDialog();
    }

    public void startInternal(){
        gameThread.setRunning(true);
        gameThread.start();
        levelEndTime = System.currentTimeMillis() + (getLevelSeconds() * milliFactor);
        levelState = LevelState.STARTED;
        handler.sendEmptyMessageDelayed(0, (getLevelSeconds() * milliFactor));
    }

    private void showStartDialog(){
        LevelDialog levelStartDialog = new LevelDialog(context);
        levelStartDialog.setRetryButtonVisible(false);
        levelStartDialog.setGoButtonHandler(getGoButtonHandler());
        levelStartDialog.setCancelButtonHandler(getCancelButtonHandler());
        levelStartDialog.showDialog(
                getCaption(),
                getMessage(),
                getGoButtonCaption());
    }

    public void showFinishDialog(){
        LevelDialog levelFinishDialog = new LevelDialog(context);
        levelFinishDialog.setGoButtonHandler(getNextButtonHandler());
        levelFinishDialog.setCancelButtonHandler(getCancelButtonHandler());
        levelFinishDialog.setRetryButtonHandler(getRetryButtonHandler());
        if (levelResult != LevelResult.SUCCESS){
            levelFinishDialog.setGoButtonVisible(false);
            levelFinishDialog.setCaptionTextColor(Color.RED);
        } else {
            levelFinishDialog.setCaptionTextColor(Color.GREEN);
        }
        levelFinishDialog.showDialog(
                getFinishDialogCaption(),
                getFinishDialogMessage(),
                getNextButtonCaption());
    }

    public void finish(LevelResult levelResult){
        if (levelState == LevelState.FINISHED){
            return;
        }
        levelState = LevelState.FINISHED;
        this.levelResult = levelResult;
        saveLevelState(levelState);
        showFinishDialog();
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
                if (!gameThread.isInterrupted()){
                    gameThread.interrupt();
                }
                gameThread.join();
                retry = false;
            }catch(InterruptedException e){
                e.printStackTrace();
                retry = false;
            }
        }
    }

    public void update(){
        //timeLabel.update();
    }

    private void saveLevelState(LevelState levelState){
        DBHelper dbHelper = new DBHelper(context);
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.put("State", levelState.ordinal());
        contentValues.put("Result", levelResult.ordinal());
        contentValues.put("Time", ((getLevelSeconds()*milliFactor) - diff));
        db.update("Level", contentValues,  String.format("%s = ?", "Name"),
                new String[]{getCaption()});
        if (levelResult == LevelResult.SUCCESS){
            unlockNextLevel();
        }
    }

    private void unlockNextLevel(){
        DBHelper dbHelper = new DBHelper(context);
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        contentValues.put("LockState", LevelLockState.UNLOCKED.ordinal());
        db.update("Level", contentValues,  String.format("%s = ?", "Number"),
                new String[]{Integer.toString(getNextLevelNumber())});
    }

    @Override
    public void draw(Canvas canvas){
        if (levelState == LevelState.FINISHED){
            gameThread.setRunning(false);
            clearCanvas(canvas);
        }
        super.draw(canvas);
        drawTimer(canvas, getTimerValue());
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public void drawTimer(Canvas canvas, String value){
        canvas.drawText(value, getTimerLabelX(value, timerPaint), getTimerLabelY(value, timerPaint), timerPaint);
    }

    private Paint CreateTimerPaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setTextSize(30);
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(true);
        paint.setTypeface(Typeface.create(FontHelper.getTypeface(), Typeface.BOLD));
        return paint;
    }

    private String getTimerValue() {
        diff = levelEndTime - System.currentTimeMillis();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        diff -= TimeUnit.SECONDS.toMillis(seconds);
        long milliseconds = diff / 10;
        if (seconds == 0 && milliseconds < 20){
            milliseconds = 0;
        }
        if(seconds < 5) {
            timerPaint.setColor(Color.RED);
        }

        return String.format("%02d:%02d", seconds, milliseconds);
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

    private Runnable getGoButtonHandler(){
        return new Runnable(){
            public void run(){
                startInternal();
            }
        };
    }

    private Runnable getCancelButtonHandler(){
        return new Runnable(){
            public void run(){
                ((GameActivity)context).selectLevel();
            }
        };
    }

    private Runnable getNextButtonHandler(){
        return new Runnable(){
            public void run(){
                Intent intent = new Intent(context, GameActivity.class);
                context.startActivity(intent);
            }
        };
    }

    private Runnable getRetryButtonHandler(){
        return new Runnable() {
            @Override
            public void run() {
                ((GameActivity)context).startLevel(getNumber());
            }
        };
    }

    private String getFinishDialogCaption (){
        String levelResultString = getString(R.string.LevelResultNone);
        switch (levelResult){
            case SUCCESS:
                levelResultString = getString(R.string.LevelResultSuccess);
                break;
            case FAILED:
                levelResultString = getString(R.string.LevelResultFailed);
                break;
            default:
                break;
        }
        return String.format("%s - %S", getCaption(), levelResultString);
    }

    private String getFinishDialogMessage() {
        switch (levelResult){
            case SUCCESS:
                return getString(R.string.FinishDialogContinueOffer);
            case FAILED:
                return getString(R.string.FinishDialogRetryOffer);
            default:
                return null;
        }
    }

    public void setLevelResult(LevelResult value){
        levelResult = value;
    }
}
