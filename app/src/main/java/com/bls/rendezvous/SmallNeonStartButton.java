package com.bls.rendezvous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SmallNeonStartButton extends View {
    private Paint bgPaint, borderPaint, glowPaint, textPaint;
    private RectF rect, borderRect;
    private boolean active = false;

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
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#2B6CFF"));

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1.5f));
        borderPaint.setColor(Color.parseColor("#8DE8FF"));
                glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setStyle(Paint.Style.FILL);
        glowPaint.setColor(Color.parseColor("#2B6CFF"));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dp(16));
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public boolean isActive() { return active; }

    public void setActive(boolean a) {
        active = a;
        bgPaint.setColor(a ? Color.parseColor("#1E50FF") : Color.parseColor("#2B6CFF"));
        invalidate();
        animate().scaleX(0.94f).scaleY(0.94f).setDuration(80)
                .withEndAction(() -> animate().scaleX(1f).scaleY(1f).setDuration(120).start()).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = getWidth(), h = getHeight();
        float r = dp(14);
        rect = new RectF(dp(10), dp(10), w - dp(10), h - dp(10));

        glowPaint.setShadowLayer(dp(18), 0, 0, Color.parseColor("#3B82FF"));
        canvas.drawRoundRect(rect, r, r, glowPaint);
        glowPaint.clearShadowLayer();

        canvas.drawRoundRect(rect, r, r, bgPaint);

        borderRect = new RectF(rect.left + dp(1.2f), rect.top + dp(1.2f), rect.right - dp(1.2f), rect.bottom - dp(1.2f));
        borderPaint.setShadowLayer(dp(8), 0, 0, Color.parseColor("#60D5FF"));
        canvas.drawRoundRect(borderRect, r - dp(1), r - dp(1), borderPaint);
        borderPaint.clearShadowLayer();

        float cy = h / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
        canvas.drawText(active ? "STOP" : "START", w / 2f, cy, textPaint);
    }

        private float dp(float v) { 
        return v * getResources().getDisplayMetrics().density; 
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) dp(110), (int) dp(48));
    }
}
