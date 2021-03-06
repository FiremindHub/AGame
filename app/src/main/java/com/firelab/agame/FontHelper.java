package com.firelab.agame;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public final class FontHelper {

    private static Typeface typeface;

    public static final void ApplyFont(View parentView, Context context){
        final String typeFaceName = "KellySlab-Regular.otf";
        typeface = Typeface.createFromAsset(context.getAssets(), typeFaceName);
        SetFont((ViewGroup)parentView, typeface);
    }

    private static void SetFont(ViewGroup parentView, Typeface typeface){
        for (int i = 0; i < parentView.getChildCount(); i++) {

            View view = parentView.getChildAt(i);

            if (view instanceof EditText) {
                ((EditText) view).setTypeface(typeface);
            }
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
            if (view instanceof Button){
                ((Button)view).setTypeface(typeface);
            }
            else if (view instanceof ViewGroup
                    && ((ViewGroup)view).getChildCount() > 0) {
                SetFont((ViewGroup) view, typeface);
            }
        }
    }

    public static Typeface getTypeface(){
        return typeface;
    }
}
