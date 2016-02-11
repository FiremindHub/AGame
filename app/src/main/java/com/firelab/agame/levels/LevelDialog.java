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

public class LevelDialog {
    Dialog dialog;
    Vibrator vib;
    RelativeLayout layout;
    Context context;
    Button btnGo;
    Button btnRetry;
    Button btnCancel;

    public LevelDialog(Context context){
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.level_dialog);
        dialog.setCancelable(false);
        layout = (RelativeLayout) dialog.findViewById(R.id.levelDialogLayout);
        btnGo = (Button)dialog.findViewById(R.id.btnGo);
        btnRetry = (Button)dialog.findViewById(R.id.btnRetry);
        btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
    }

    @SuppressWarnings("static-access")
    public void showDialog(String title, String message, String goButtonCaption, final Runnable task) {
        TextView messageTextView = (TextView) dialog.findViewById(R.id.message);
        TextView titleTextView = (TextView) dialog.findViewById(R.id.title);
        FontHelper.ApplyFont(layout, context);
        titleTextView.setText(bold(title));
        messageTextView.setText(message);
        dialog.show();
        btnGo.setText(bold(goButtonCaption));
        vib = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        btnGo.setOnClickListener(new View.OnClickListener() {
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

    public SpannableString bold(String s) {
        SpannableString spanString = new SpannableString(s);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0,
                spanString.length(), 0);
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        return spanString;
    }
}
