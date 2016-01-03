package com.firelab.agame;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "a_LCDNova Regular.ttf");
        btnPlay.setTypeface(typeface);
        btnPlay.setPaintFlags(btnPlay.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void btnPlay_OnClick(View view) {
        Intent intent = new Intent(MainActivity.this, LevelSelectActivity.class);
        startActivity(intent);
    }
}
