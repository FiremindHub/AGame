package com.firelab.agame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.firelab.agame.levels.Level;
import com.firelab.agame.levels.Level1;

import java.util.concurrent.TimeUnit;

public class GameActivity extends BaseActivity {

    Level level;
    private final int LEVEL_SELECTED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        selectLevel();

        //level.stop();

        //handler.sendEmptyMessageDelayed(0, level.getLevelSeconds() * 1000);
        /*LevelStartDialog levelStartDialog = new LevelStartDialog();
        levelStartDialog.showDialog(level.getContext(), level.getCaption(), level.getMessage(),
                new Runnable(){
                    public void run(){
                        level.start();
                    }
                });*/

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme);
        builder.setMessage("SomeMessage")
                .setTitle("SomeTitle");
        builder.setPositiveButton(R.string.AlertStartButtonCaption, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                gameView.start();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/
    }

    public void selectLevel(){
        Intent intent = new Intent(GameActivity.this, LevelSelectActivity.class);
        startActivityForResult(intent, LEVEL_SELECTED);
    }



    public void recreate(){
        Intent intent = new Intent(this, this.getClass());
        finish();
        startActivity(intent);
    }

    private Level getLevel(int levelNumber){
        switch (levelNumber){
            case 1:
                return new Level1(this);
            default:
                return null;
        }
    }

    public void startLevel(int levelNumber){
        level = getLevel(levelNumber);
        setContentView(level);
        level.start();
    }

    public void closeActivity(){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode){
            case LEVEL_SELECTED:
                int levelNumber = resultCode;
              startLevel(levelNumber);
                break;
            default:
                break;
        }
    }
}
