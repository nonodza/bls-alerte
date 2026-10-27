
package com.bls.rendezvous;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class SmallNeonStartButton extends View {

    private boolean isActive = false;
    private String text = "START";

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rect = new RectF();
    private final RectF ringRect = new RectF();
    private final Path iconPath = new Path();

    private float density;
    private float scaledDensity;
    private float rotation = 0f;

    private ValueAnimator animator;

    private final int blue1 = Color.parseColor("#174CF5");
    private final int blue2 = Color.parseColor("#087EDC");
    private final int cyan = Color.parseColor("#42F5FF");

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

    private float dp(float value) {
        return value * density;
    }

    private float sp(float value) {
        return value * scaledDensity;
    }

    private void init() {
        density = getResources().getDisplayMetrics().density;
        scaledDensity = getResources().getDisplayMetrics().scaledDensity;

        bgPaint.setStyle(Paint.Style.FILL);

        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(dp(2.2f));
        glowPaint.setStrokeCap(Paint.Cap.ROUND);
        glowPaint.setShadowLayer(
                dp(5),
                0,
                0,
                Color.parseColor("#6642F5FF")
        );

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1f));
        borderPaint.setColor(Color.parseColor("#A6EFFF"));

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(sp(12));
        textPaint.setTypeface(
                Typeface.create("sans-serif", Typeface.BOLD)
        );
        textPaint.setTextAlign(Paint.Align.LEFT);

        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);

        setClickable(true);
        setFocusable(true);
        setElevation(dp(3));
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        setContentDescription("Start appointment monitoring");

        animator = ValueAnimator.ofFloat(0f, 360f);
        animator.setDuration(2400);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            rotation = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.start();
    }

    public void setActive(boolean active) {
        if (isActive == active) {
            return;
        }

        isActive = active;
        text = active ? "STOP" : "START";

        setContentDescription(
                active
                        ? "Stop appointment monitoring"
                        : "Start appointment monitoring"
        );

        invalidate();
        requestLayout();
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        int desiredWidth = (int) dp(92);
        int desiredHeight = (int) dp(42);

        setMeasuredDimension(
                resolveSize(desiredWidth, widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec)
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

        float inset = dp(2);
        float radius = dp(13);

        rect.set(
                inset,
                inset,
                width - inset,
                height - inset
        );

        // Blue gradient background
        bgPaint.setShader(
                new LinearGradient(
                        0,
                        0,
                        width,
                        height,
                        new int[]{
                                blue1,
                                blue2,
                                Color.parseColor("#1558C9")
                        },
                        null,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(rect, radius, radius, bgPaint);
        bgPaint.setShader(null);

        // Soft cyan border
        borderPaint.setColor(
                isActive
                        ? Color.parseColor("#FFB7B7")
                        : Color.parseColor("#91F4FF")
        );

        canvas.drawRoundRect(
                rect,
                radius,
                radius,
                borderPaint
        );

        // Animated neon ring around the button
        ringRect.set(
                dp(1.5f),
                dp(1.5f),
                width - dp(1.5f),
                height - dp(1.5f)
        );

        int[] ringColors = isActive
                ? new int[]{
                        Color.TRANSPARENT,
                        Color.parseColor("#FFFF7777"),
                        Color.parseColor("#FFFF3333"),
                        Color.TRANSPARENT
                }
                : new int[]{
                        Color.TRANSPARENT,
                        Color.parseColor("#5542F5FF"),
                        cyan,
                        Color.WHITE,
                        cyan,
                        Color.TRANSPARENT
                };

        float[] positions = isActive
                ? new float[]{0f, 0.35f, 0.48f, 1f}
                : new float[]{0f, 0.25f, 0.40f, 0.48f, 0.62f, 1f};

        SweepGradient sweep = new SweepGradient(
                width / 2f,
                height / 2f,
                ringColors,
                positions
        );

        glowPaint.setShader(sweep);

        canvas.save();
        canvas.rotate(rotation, width / 2f, height / 2f);

        canvas.drawRoundRect(
                ringRect,
                radius,
                radius,
                glowPaint
        );

        canvas.restore();

        glowPaint.setShader(null);

        // Center icon and label together
        float centerY = height / 2f;
        float iconSize = dp(11);
        float gap = dp(6);
        float textWidth = textPaint.measureText(text);
        float totalWidth = iconSize + gap + textWidth;
        float startX = (width - totalWidth) / 2f;

        if (!isActive) {
            // Play icon
            iconPath.reset();
            iconPath.moveTo(startX, centerY - iconSize / 2f);
            iconPath.lineTo(startX, centerY + iconSize / 2f);
            iconPath.lineTo(startX + iconSize, centerY);
            iconPath.close();

            canvas.drawPath(iconPath, iconPaint);
        } else {
            // Stop icon
            float barWidth = dp(3);
            float barHeight = dp(10);
            float barGap = dp(2.5f);

            canvas.drawRoundRect(
                    startX,
                    centerY - barHeight / 2f,
                    startX + barWidth,
                    centerY + barHeight / 2f,
                    dp(1),
                    dp(1),
                    iconPaint
            );

            canvas.drawRoundRect(
                    startX + barWidth + barGap,
                    centerY - barHeight / 2f,
                    startX + barWidth * 2 + barGap,
                    centerY + barHeight / 2f,
                    dp(1),
                    dp(1),
                    iconPaint
            );
        }

        Paint.FontMetrics metrics = textPaint.getFontMetrics();

        float textY = centerY
                - (metrics.ascent + metrics.descent) / 2f;

        canvas.drawText(
                text,
                startX + iconSize + gap,
                textY,
                textPaint
        );
    }

    @Override
    protected void onDetachedFromWindow() {
        if (animator != null) {
            animator.cancel();
        }

        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }
}
