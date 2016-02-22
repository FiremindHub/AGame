package com.firelab.agame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.Console;

/**
 * Created by Firemind on 21.02.2016.
 */
public class BaseActivity extends Activity {// AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Log.e("ACTIVITY_CREATED", this.getClass().getSimpleName() + ", " + Thread.currentThread().getName());
    }
}

