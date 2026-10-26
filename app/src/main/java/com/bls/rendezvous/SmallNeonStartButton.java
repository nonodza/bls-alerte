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

    private Paint bgPaint;
    private Paint textPaint;
    private Paint iconPaint;

    private final RectF rect = new RectF();
    private final Path iconPath = new Path();

    private float density;
    private float scaledDensity;

    private int startColor1;
    private int startColor2;
    private int stopColor1;
    private int stopColor2;

    public SmallNeonStartButton(Context context) {
        super(context);
        init();
    }

    public SmallNeonStartButton(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init();
    }

    private void init() {

        density = getResources()
                .getDisplayMetrics().density;

        scaledDensity = getResources()
                .getDisplayMetrics().scaledDensity;

        startColor1 = Color.parseColor("#1554F0");
        startColor2 = Color.parseColor("#00BFEA");

        stopColor1 = Color.parseColor("#EF4444");
        stopColor2 = Color.parseColor("#B91C1C");

        bgPaint = new Paint(
                Paint.ANTI_ALIAS_FLAG
        );

        bgPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(
                Paint.ANTI_ALIAS_FLAG
        );

        textPaint.setColor(Color.WHITE);

        textPaint.setTextSize(sp(12));

        textPaint.setTypeface(
                Typeface.create(
                        "sans-serif",
                        Typeface.BOLD
                )
        );

        textPaint.setTextAlign(
                Paint.Align.LEFT
        );

        iconPaint = new Paint(
                Paint.ANTI_ALIAS_FLAG
        );

        iconPaint.setColor(Color.WHITE);

        iconPaint.setStyle(
                Paint.Style.FILL
        );

        setClickable(true);
        setFocusable(true);

        setElevation(dp(4));

        setLayerType(
                View.LAYER_TYPE_SOFTWARE,
                null
        );

        updateAccessibility();
    }

    public void setActive(boolean active) {

        if (isActive == active) {
            return;
        }

        isActive = active;

        text = active ? "STOP" : "START";

        updateAccessibility();

        requestLayout();
        invalidate();
    }

    public boolean isActive() {
        return isActive;
    }

    private void updateAccessibility() {

        setContentDescription(
                isActive
                        ? "Stop appointment monitoring"
                        : "Start appointment monitoring"
        );
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {

        float iconWidth = dp(12);
        float gap = dp(8);
        float horizontalPadding = dp(14);

        int desiredWidth = (int) (
                horizontalPadding * 2
                        + iconWidth
                        + gap
                        + textPaint.measureText(text)
        );

        int desiredHeight = (int) dp(42);

        int measuredWidth = resolveSize(
                Math.max((int) dp(92), desiredWidth),
                widthMeasureSpec
        );

        int measuredHeight = resolveSize(
                desiredHeight,
                heightMeasureSpec
        );

        setMeasuredDimension(
                measuredWidth,
                measuredHeight
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        float radius = dp(13);

        rect.set(
                dp(1),
                dp(1),
                width - dp(1),
                height - dp(1)
        );

        int color1 = isActive
                ? stopColor1
                : startColor1;

        int color2 = isActive
                ? stopColor2
                : startColor2;

        bgPaint.setShader(
                new LinearGradient(
                        0,
                        0,
                        width,
                        height,
                        color1,
                        color2,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(
                rect,
                radius,
                radius,
                bgPaint
        );

        bgPaint.setShader(null);

        float centerY = height / 2f;

        float iconX = dp(13);
        float iconSize = dp(12);

        if (!isActive) {

            // START: Play icon

            iconPath.reset();

            iconPath.moveTo(
                    iconX,
                    centerY - iconSize / 2f
            );

            iconPath.lineTo(
                    iconX,
                    centerY + iconSize / 2f
            );

            iconPath.lineTo(
                    iconX + iconSize,
                    centerY
            );

            iconPath.close();

            canvas.drawPath(
                    iconPath,
                    iconPaint
            );

        } else {

            // STOP: Two vertical bars

            float barWidth = dp(3);
            float barHeight = dp(11);
            float barGap = dp(3);

            canvas.drawRoundRect(
                    iconX,
                    centerY - barHeight / 2f,
                    iconX + barWidth,
                    centerY + barHeight / 2f,
                    dp(1),
                    dp(1),
                    iconPaint
            );

            canvas.drawRoundRect(
                    iconX + barWidth + barGap,
                    centerY - barHeight / 2f,
                    iconX + barWidth * 2 + barGap,
                    centerY + barHeight / 2f,
                    dp(1),
                    dp(1),
                    iconPaint
            );
        }

        // Center the icon and label together

        float gap = dp(7);

        float textWidth = textPaint.measureText(text);

        float totalWidth = dp(12) + gap + textWidth;

        float contentStart = (width - totalWidth) / 2f;

        float textX = contentStart + dp(12) + gap;

        // Redraw the icon at its centered position

        canvas.save();

        canvas.translate(
                contentStart - iconX,
                0
        );

        if (!isActive) {

            iconPath.reset();

            iconPath.moveTo(
                    iconX,
                    centerY - iconSize / 2f
            );

            iconPath.lineTo(
                    iconX,
                    centerY + iconSize / 2f
            );

            iconPath.lineTo(
                    iconX + iconSize,
                    centerY
            );

            iconPath.close();

            canvas.drawPath(
                    iconPath,
                    iconPaint
            );

        } else {

            float barWidth = dp(3);
            float barHeight = dp(11);
            float barGap = dp(3);

            canvas.drawRoundRect(
                    iconX,
                    centerY - barHeight / 2f,
                    iconX + barWidth,
                    centerY + barHeight / 2f,
                    dp(1),
                    dp(1),
                    iconPaint
            );

            canvas.drawRoundRect(
                    iconX + barWidth + barGap,
                    centerY - barHeight / 2f,
                    iconX + barWidth * 2 + barGap,
                    centerY + barHeight / 2f,
                    dp(1),
                    dp(1),
                    iconPaint
            );
        }

        canvas.restore();

        Paint.FontMetrics metrics =
                textPaint.getFontMetrics();

        float textY = centerY
                - (metrics.ascent + metrics.descent) / 2f;

        canvas.drawText(
                text,
                textX,
                textY,
                textPaint
        );
    }

    private float dp(float value) {
        return value * density;
    }

    private float sp(float value) {
        return value * scaledDensity;
    }
}
