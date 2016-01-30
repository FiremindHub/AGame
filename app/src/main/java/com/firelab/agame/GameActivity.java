package com.firelab.agame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameView(this);
        setContentView(gameView);

        LevelStartDialog levelStartDialog = new LevelStartDialog();

        levelStartDialog.showDialog(gameView.getContext(), "SomeTitle", "Some Message",
                new Runnable(){
                    public void run(){
                        gameView.start();
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
}
