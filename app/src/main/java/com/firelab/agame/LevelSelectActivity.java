package com.firelab.agame;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LevelSelectActivity extends AppCompatActivity {

    TextView captionTextView;
    Button btnLevel1;
    Button btnLevel2;
    Button btnLevel3;
    Button btnLevel4;
    Button btnLevel5;
    Button btnLevel6;
    Button btnLevel7;
    Button btnLevel8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level_select);
        SetActivityElementsTypeFace();
    }

    private void SetActivityElementsTypeFace(){
        Typeface typeface = Typeface.createFromAsset(getAssets(), "a_LCDNova Regular.ttf");
        captionTextView = (TextView)findViewById(R.id.ActivityCaptionTextView);
        btnLevel1 = (Button)findViewById(R.id.btnLevel1);
        btnLevel2 = (Button)findViewById(R.id.btnLevel2);
        btnLevel3 = (Button)findViewById(R.id.btnLevel3);
        btnLevel4 = (Button)findViewById(R.id.btnLevel4);
        btnLevel5 = (Button)findViewById(R.id.btnLevel5);
        btnLevel6 = (Button)findViewById(R.id.btnLevel6);
        btnLevel7 = (Button)findViewById(R.id.btnLevel7);
        btnLevel8 = (Button)findViewById(R.id.btnLevel8);
        btnLevel1.setTypeface(typeface);
        btnLevel2.setTypeface(typeface);
        btnLevel3.setTypeface(typeface);
        btnLevel4.setTypeface(typeface);
        btnLevel5.setTypeface(typeface);
        btnLevel6.setTypeface(typeface);
        btnLevel7.setTypeface(typeface);
        btnLevel8.setTypeface(typeface);
        captionTextView.setTypeface(typeface);
    }

    public void btnLevel1_OnClick(View view) {
        Intent intent = new Intent(LevelSelectActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void btnLevel2_OnClick(View view) {
    }

    public void btnLevel3_OnClick(View view) {
    }

    public void btnLevel4_OnClick(View view) {
    }

    public void btnLevel5_OnClick(View view) {
    }

    public void btnLevel6_OnClick(View view) {
    }

    public void btnLevel7_OnClick(View view) {
    }

    public void btnLevel8_OnClick(View view) {
    }
}
