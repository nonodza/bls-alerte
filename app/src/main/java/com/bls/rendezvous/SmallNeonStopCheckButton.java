package com.bls.rendezvous;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.TextView;

public class SmallNeonStopCheckButton extends TextView {

    public SmallNeonStopCheckButton(Context context) {
        super(context);

        setText("STOP CHECK");
        setTextColor(Color.WHITE);
        setTextSize(11);
        setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        setGravity(Gravity.CENTER);
        setIncludeFontPadding(false);

        GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{
                        Color.rgb(239, 68, 68),
                        Color.rgb(185, 28, 28)
                }
        );

        background.setCornerRadius(dp(12));

        background.setStroke(
                dp(1),
                Color.rgb(252, 120, 120)
        );

        setBackground(background);

        setElevation(dp(5));

        setClickable(true);
        setFocusable(true);
    }

    private int dp(float value) {
        return (int) (value * getResources()
                .getDisplayMetrics().density + 0.5f);
    }
}
