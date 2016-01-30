package com.firelab.agame;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class LevelStartDialog {
    Dialog dialog;
    Vibrator vib;
    RelativeLayout layout;
    Context context;

    @SuppressWarnings("static-access")
    public void showDialog(final Context context, String title, String message,
                       final Runnable task) {
        //Typeface typeface = Typeface.createFromAsset(context.getAssets(), "a_LCDNova Regular.ttf");
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom);
        dialog.setCancelable(false);
        TextView messageTextView = (TextView) dialog.findViewById(R.id.message);
        TextView titleTextView = (TextView) dialog.findViewById(R.id.title);
        final Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
        layout = (RelativeLayout) dialog.findViewById(R.id.levelStartDialogLayout);
        FontHelper.ApplyFont(layout, context);
        titleTextView.setText(bold(title));
        messageTextView.setText(message);
        //messageTextView.setTypeface(typeface);
        dialog.show();
        btnOK.setText(bold(context.getString(R.string.AlertStartButtonCaption)));
        vib = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*
                TODO Request VIBRATE permission
                vib.vibrate(20);
                */
                dialog.dismiss();
                task.run();
            }
        });
    }
    //customize text style bold italic....
    public SpannableString bold(String s) {
        //Typeface typeface = Typeface.createFromAsset(context.getAssets(), "a_LCDNova Regular.ttf");
        SpannableString spanString = new SpannableString(s);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0,
                spanString.length(), 0);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        //spanString.setSpan(typeface, 0, spanString.length(), 0);
        // spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0,
        // spanString.length(), 0);
        return spanString;
    }
}