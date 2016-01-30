package com.firelab.agame;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public final class FontHelper {

    public static final void ApplyFont(View parentView, Context context){
        final String typeFaceName = "MainFont.ttf";
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), typeFaceName);
        SetFont((ViewGroup)parentView, typeface);
    }

    private static void SetFont(ViewGroup parentView, Typeface typeface){
        for (int i = 0; i < parentView.getChildCount(); i++) {

            View view = parentView.getChildAt(i);

//You can add any view element here on which you want to apply font

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
}
