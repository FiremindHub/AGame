package com.firelab.agame.levels;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firelab.agame.FontHelper;
import com.firelab.agame.R;

import java.util.Set;

public class LevelDialog {
    Dialog dialog;
    Vibrator vib;
    RelativeLayout layout;
    Context context;
    Button btnGo;
    Button btnRetry;
    Button btnCancel;
    Runnable btnGoHandler;
    Runnable btnRetryHandler;
    Runnable btnCancelHandler;
    int captionTextColor = 0;

    public LevelDialog(Context context){
        this.context = context;
        Initialize();
    }

    private void Initialize(){
        try {
            dialog = new Dialog(context);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.level_dialog);
        dialog.setCancelable(false);
        layout = (RelativeLayout) dialog.findViewById(R.id.levelDialogLayout);
        btnGo = (Button)dialog.findViewById(R.id.btnGo);
        btnRetry = (Button)dialog.findViewById(R.id.btnRetry);
        btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
        vib = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        SetButtonsListeners();
    }

    @SuppressWarnings("static-access")
    public void showDialog(String title, String message, String goButtonCaption) {
        TextView messageTextView = (TextView) dialog.findViewById(R.id.message);
        TextView titleTextView = (TextView) dialog.findViewById(R.id.title);
        FontHelper.ApplyFont(layout, context);
        titleTextView.setText(bold(title));
        if (captionTextColor != 0){
            titleTextView.setTextColor(captionTextColor);
        }
        messageTextView.setText(message);
        dialog.show();
        btnGo.setText(bold(goButtonCaption));


    }

    private SpannableString bold(String s) {
        SpannableString spanString = new SpannableString(s);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0,
                spanString.length(), 0);
        //spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        return spanString;
    }

    public void setRetryButtonVisible(Boolean visible){
        if (!visible) {
            btnRetry.setVisibility(View.INVISIBLE);
        }
        else{
            btnRetry.setVisibility(View.VISIBLE);
        }
    }

    public void setCancelButtonVisible(Boolean visible){
        if (!visible) {
            btnCancel.setVisibility(View.INVISIBLE);
        }
        else{
            btnCancel.setVisibility(View.VISIBLE);
        }
    }

    public void setGoButtonVisible(Boolean visible){
        if (!visible) {
            btnGo.setVisibility(View.INVISIBLE);
        }
        else{
            btnGo.setVisibility(View.VISIBLE);
        }
    }

    public void setGoButtonHandler(Runnable handler){
        btnGoHandler = handler;
    }

    public void setRetryButtonHandler(Runnable handler){
        btnRetryHandler = handler;
    }

    public void setCancelButtonHandler(Runnable handler){
        btnCancelHandler = handler;
    }

    private void SetButtonsListeners(){
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*
                TODO Request VIBRATE permission
                vib.vibrate(20);
                */
                dialog.dismiss();
                if (btnGoHandler != null){
                    btnGoHandler.run();
                }
            }
        });

        btnRetry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                dialog.dismiss();
                if (btnRetryHandler != null){
                    btnRetryHandler.run();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                dialog.cancel();
                if (btnCancelHandler != null){
                    btnCancelHandler.run();
                }
            }
        });
    }

    public void setCaptionTextColor(int value) {
        captionTextColor = value;
    }
}
