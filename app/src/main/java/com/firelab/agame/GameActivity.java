package com.firelab.agame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.firelab.agame.levels.Level;
import com.firelab.agame.levels.Level1;

public class GameActivity extends AppCompatActivity {

    Level level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        level = getLevel();
        setContentView(level);

        LevelStartDialog levelStartDialog = new LevelStartDialog();
        levelStartDialog.showDialog(level.getContext(), level.getCaption(), level.getMessage(),
                new Runnable(){
                    public void run(){
                        level.start();
                    }
                });

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

    private Level getLevel(){
        return new Level1(this);
    }
}
