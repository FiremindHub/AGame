package com.firelab.agame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firelab.agame.levels.Level1;

public class LevelSelectActivity extends BaseActivity {

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
        setContentView(R.layout.activity_level_select);
        ViewGroup mainLayout = (ViewGroup)findViewById(R.id.MainLayout);
        FontHelper.ApplyFont(mainLayout, getApplicationContext());
        SetButtonsClickListener(mainLayout);
        SetActivityElementsTypeFace();
    }

    private void SetActivityElementsTypeFace(){
        captionTextView = (TextView)findViewById(R.id.ActivityCaptionTextView);
        btnLevel1 = (Button)findViewById(R.id.btnLevel1);
        btnLevel2 = (Button)findViewById(R.id.btnLevel2);
        btnLevel3 = (Button)findViewById(R.id.btnLevel3);
        btnLevel4 = (Button)findViewById(R.id.btnLevel4);
        btnLevel5 = (Button)findViewById(R.id.btnLevel5);
        btnLevel6 = (Button)findViewById(R.id.btnLevel6);
        btnLevel7 = (Button)findViewById(R.id.btnLevel7);
        btnLevel8 = (Button)findViewById(R.id.btnLevel8);
    }

    @Override
    public void onClick(View v){
        LevelButton button = (LevelButton)v;

        if (button == null){
            return;
        }

        int levelNumber = button.getLevelNumber();

        CharSequence text = String.valueOf(levelNumber);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();

        setResult(levelNumber);
        finish();
    }

    public void btnLevel2_OnClick(View view) {
        Intent intent = new Intent(LevelSelectActivity.this, TestActivity.class);
        startActivity(intent);
    }

    public void btnLevel3_OnClick(View view) {
        Context context = getApplicationContext();
        LevelButton btn = (LevelButton)view;

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
