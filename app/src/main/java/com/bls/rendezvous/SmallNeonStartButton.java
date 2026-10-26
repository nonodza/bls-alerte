package com.bls.rendezvous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class SmallNeonStartButton extends View {

    private boolean isActive = false;
    private String text = "START";
    private Paint bgPaint, textPaint, iconPaint;
    private RectF rect = new RectF();
    private OnClickListener externalClick;

    public SmallNeonStartButton(Context context) {
        super(context);
        init();
    }

    public SmallNeonStartButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setClickable(true);
        setFocusable(true);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(sp(14));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        textPaint.setTextAlign(Paint.Align.LEFT);

        iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);

        setElevation(dp(8));
    }

    public void setActive(boolean active) {
        isActive = active;
        text = active ? "STOP" : "START";
        invalidate();
        requestLayout();
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.externalClick = l;
        super.setOnClickListener(v -> {
            if (externalClick != null) externalClick.onClick(v);
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = (int) (dp(32) + textPaint.measureText(text) + dp(32));
        int h = (int) dp(44);
        if (w < dp(110)) w = (int) dp(110);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.set(0, 0, getWidth(), getHeight());
        float radius = dp(14);

        // Gradient - أزرق لسماوي كيما الصورة، أحمر كي يكون STOP
        int color1 = isActive ? Color.parseColor("#EF4444") : Color.parseColor("#1554F0");
        int color2 = isActive ? Color.parseColor("#DC2626") : Color.parseColor("#00C6DE");

        bgPaint.setShader(new LinearGradient(0, 0, getWidth(), 0, color1, color2, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(rect, radius, radius, bgPaint);

        float iconX = dp(18);
        float centerY = getHeight() / 2f;

        if (!isActive) {
            // أيقونة Play ▶
            Path path = new Path();
            path.moveTo(iconX, centerY - 7);
            path.lineTo(iconX, centerY + 7);
            path.lineTo(iconX + 11, centerY);
            path.close();
            canvas.drawPath(path, iconPaint);
        } else {
            // أيقونة Stop || 
            canvas.drawRect(iconX, centerY - 6, iconX + 4, centerY + 6, iconPaint);
            canvas.drawRect(iconX + 7, centerY - 6, iconX + 11, centerY + 6, iconPaint);
        }

        // النص
        canvas.drawText(text, iconX + 20, centerY + 5, textPaint);
    }

    private float dp(float v) {
        return v * getResources().getDisplayMetrics().density;
    }

    private float sp(float v) {
        return v * getResources().getDisplayMetrics().scaledDensity;
    }
} 
