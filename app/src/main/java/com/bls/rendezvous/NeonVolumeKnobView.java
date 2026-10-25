
package com.bls.rendezvous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

public class NeonVolumeKnobView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean active = false;

    public NeonVolumeKnobView(Context context) {
        super(context);
        setClickable(true);
        setFocusable(true);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        float radius = Math.min(cx, cy) - dp(5);

        if (radius <= 0) return;

        int neon = active
                ? Color.parseColor("#22F0B8")
                : Color.parseColor("#38A5FF");

        // Outer neon glow
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(3));
        paint.setColor(neon);
        paint.setShadowLayer(dp(12), 0, 0, neon);
        canvas.drawCircle(cx, cy, radius - dp(2), paint);
        paint.clearShadowLayer();

        // Outer ring
        paint.setStrokeWidth(dp(2));
        paint.setColor(Color.parseColor("#7DAFFF"));
        canvas.drawCircle(cx, cy, radius - dp(7), paint);

        // Dark 3D center
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(new LinearGradient(
                cx - radius,
                cy - radius,
                cx + radius,
                cy + radius,
                new int[]{
                        Color.parseColor("#344B70"),
                        Color.parseColor("#111B2D"),
                        Color.parseColor("#070D19")
                },
                null,
                Shader.TileMode.CLAMP
        ));

        canvas.drawCircle(cx, cy, radius - dp(11), paint);
        paint.setShader(null);

        // Inner highlight
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(1));
        paint.setColor(Color.parseColor("#6E9DDB"));

        canvas.drawArc(
                new RectF(
                        cx - radius + dp(17),
                        cy - radius + dp(17),
                        cx + radius - dp(17),
                        cy + radius - dp(17)
                ),
                205,
                125,
                false,
                paint
        );

        // Play / pause symbol
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        if (active) {
            float barWidth = dp(5);
            float barHeight = dp(19);
            float gap = dp(5);

            canvas.drawRoundRect(
                    cx - gap - barWidth,
                    cy - barHeight / 2,
                    cx - gap,
                    cy + barHeight / 2,
                    dp(2),
                    dp(2),
                    paint
            );

            canvas.drawRoundRect(
                    cx + gap,
                    cy - barHeight / 2,
                    cx + gap + barWidth,
                    cy + barHeight / 2,
                    dp(2),
                    dp(2),
                    paint
            );
        } else {
            PathTriangle(canvas, cx, cy);
        }

        // Small state label
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(dp(8));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(neon);

        canvas.drawText(
                active ? "ACTIVE" : "START",
                cx,
                cy + radius - dp(14),
                paint
        );

        paint.setStyle(Paint.Style.FILL);
        paint.setShader(null);
        paint.clearShadowLayer();
    }

    private void PathTriangle(Canvas canvas, float cx, float cy) {
        android.graphics.Path path = new android.graphics.Path();

        path.moveTo(cx - dp(5), cy - dp(9));
        path.lineTo(cx + dp(9), cy);
        path.lineTo(cx - dp(5), cy + dp(9));
        path.close();

        canvas.drawPath(path, paint);
    }

    private float dp(float value) {
        return value * getResources()
                .getDisplayMetrics().density;
    }
   
    // =========================================================
    // SMALL BUTTON — PREMIUM NEON
    // =========================================================

    private Button smallButton(String label) {

        Button b = new Button(this);

        b.setText(label);
        b.setTextSize(10);
        b.setTextColor(Color.WHITE);
        b.setTypeface(Typeface.DEFAULT_BOLD);
        b.setAllCaps(false);
        b.setLetterSpacing(0.04f);

        b.setMinHeight(0);
        b.setMinimumHeight(0);
        b.setMinWidth(0);
        b.setMinimumWidth(0);

        b.setPadding(
                dp(16),
                dp(8),
                dp(16),
                dp(8)
        );

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.parseColor("#245FE8"),
                        Color.parseColor("#5148D8"),
                        Color.parseColor("#783FE0")
                }
        );

        bg.setCornerRadius(dp(11));

        bg.setStroke(
                dp(1),
                Color.parseColor("#8CA8FF")
        );

        b.setBackground(bg);
        b.setBackgroundTintList(null);
        b.setStateListAnimator(null);
        b.setElevation(dp(5));

        return b;
    }
 
}
