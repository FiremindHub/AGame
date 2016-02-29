package com.firelab.agame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.Console;

/**
 * Created by Firemind on 21.02.2016.
 */
public class BaseActivity extends Activity implements View.OnClickListener{// AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Log.e("ACTIVITY_CREATED", this.getClass().getSimpleName() + ", " + Thread.currentThread().getName());
    }

    protected void SetButtonsClickListener(ViewGroup parentView){
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View view = parentView.getChildAt(i);
            if (view instanceof Button){
                ((Button)view).setOnClickListener(this);
            }
            else if (view instanceof ViewGroup
                    && ((ViewGroup)view).getChildCount() > 0) {
                SetButtonsClickListener((ViewGroup) view);
            }
        }
    }

    @Override
    public void onClick(View v){

    }
}

