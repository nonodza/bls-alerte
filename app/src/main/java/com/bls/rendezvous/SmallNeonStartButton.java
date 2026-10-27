
package com.bls.rendezvous;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class SmallNeonStartButton extends View {

    private boolean isActive = false;
    private String text = "START";

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint neonBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint neonGlowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint neonCorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rect = new RectF();
    private final RectF ringRect = new RectF();
    private final Path borderPath = new Path();
    private final Path segmentPath = new Path();
    private final Path iconPath = new Path();

    private float density;
    private float scaledDensity;
    private float rotation = 0f;

    private ValueAnimator animator;

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

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1f));
        borderPaint.setColor(Color.parseColor("#BCEFFF"));

        neonBasePaint.setStyle(Paint.Style.STROKE);
        neonBasePaint.setStrokeWidth(dp(1.2f));
        neonBasePaint.setStrokeCap(Paint.Cap.ROUND);

        neonGlowPaint.setStyle(Paint.Style.STROKE);
        neonGlowPaint.setStrokeWidth(dp(3.5f));
        neonGlowPaint.setStrokeCap(Paint.Cap.ROUND);
        neonGlowPaint.setStrokeJoin(Paint.Join.ROUND);

        neonCorePaint.setStyle(Paint.Style.STROKE);
        neonCorePaint.setStrokeWidth(dp(1.8f));
        neonCorePaint.setStrokeCap(Paint.Cap.ROUND);
        neonCorePaint.setStrokeJoin(Paint.Join.ROUND);

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

        // Software layer enables the neon shadow glow.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        updateAccessibility();

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2400);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            rotation = (float) animation.getAnimatedValue();
            invalidate();
        });
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
        float gap = dp(7);
        float horizontalPadding = dp(12);

        int desiredWidth = (int) Math.max(
                dp(92),
                horizontalPadding * 2
                        + iconWidth
                        + gap
                        + textPaint.measureText(text)
        );

        int desiredHeight = (int) dp(42);

        setMeasuredDimension(
                resolveSize(desiredWidth, widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec)
        );
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (animator != null) {
            animator.cancel();
        }

        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        if (width <= 0 || height <= 0) {
            return;
        }

        float inset = dp(3f);
        float radius = dp(10f);

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
                                Color.parseColor("#1649E8"),
                                Color.parseColor("#087FE8"),
                                Color.parseColor("#123BC1")
                        },
                        null,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(
                rect,
                dp(12),
                dp(12),
                bgPaint
        );

        bgPaint.setShader(null);

        // Rounded rectangular path followed by the neon beam
        ringRect.set(
                dp(5),
                dp(5),
                width - dp(5),
                height - dp(5)
        );

        borderPath.reset();
        borderPath.addRoundRect(
                ringRect,
                radius,
                radius,
                Path.Direction.CW
        );

        // Static pale outline
        borderPaint.setColor(
                isActive
                        ? Color.parseColor("#FFFFB9B9")
                        : Color.parseColor("#E8FCFF")
        );

        canvas.drawPath(borderPath, borderPaint);

        // Subtle continuous neon edge
        neonBasePaint.setColor(
                isActive
                        ? Color.parseColor("#66FF7777")
                        : Color.parseColor("#6653F5FF")
        );

        canvas.drawPath(borderPath, neonBasePaint);

        // Measure the actual rounded border path
        PathMeasure pathMeasure = new PathMeasure(
                borderPath,
                true
        );

        float pathLength = pathMeasure.getLength();

        if (pathLength > 0) {
            float segmentLength = Math.min(
                    dp(42),
                    pathLength * 0.24f
            );

            float startDistance = rotation * pathLength;
            float endDistance = startDistance + segmentLength;

            segmentPath.reset();

            if (endDistance <= pathLength) {
                pathMeasure.getSegment(
                        startDistance,
                        endDistance,
                        segmentPath,
                        true
                );
            } else {
                // Continue seamlessly across the path endpoint
                pathMeasure.getSegment(
                        startDistance,
                        pathLength,
                        segmentPath,
                        true
                );

                Path extraSegment = new Path();

                pathMeasure.getSegment(
                        0,
                        endDistance - pathLength,
                        extraSegment,
                        true
                );

                segmentPath.addPath(extraSegment);
            }

            int beamColor = isActive
                    ? Color.parseColor("#FFFF7777")
                    : Color.parseColor("#42F5FF");

            // Outer glow
            neonGlowPaint.setColor(beamColor);
            neonGlowPaint.setShadowLayer(
                    dp(7),
                    0,
                    0,
                    beamColor
            );

            canvas.drawPath(segmentPath, neonGlowPaint);

            // Bright cyan core
            neonCorePaint.setColor(
                    isActive
                            ? Color.WHITE
                            : Color.parseColor("#C8FFFF")
            );

            neonCorePaint.setShadowLayer(
                    dp(3),
                    0,
                    0,
                    beamColor
            );

            canvas.drawPath(segmentPath, neonCorePaint);

            neonGlowPaint.clearShadowLayer();
            neonCorePaint.clearShadowLayer();
        }

        // Center icon and label
        float centerY = height / 2f;
        float iconSize = dp(11);
        float gap = dp(6);
        float textWidth = textPaint.measureText(text);

        float totalWidth = iconSize + gap + textWidth;
        float startX = (width - totalWidth) / 2f;

        if (!isActive) {
            // Play triangle
            iconPath.reset();

            iconPath.moveTo(
                    startX,
                    centerY - iconSize / 2f
            );

            iconPath.lineTo(
                    startX,
                    centerY + iconSize / 2f
            );

            iconPath.lineTo(
                    startX + iconSize,
                    centerY
            );

            iconPath.close();

            canvas.drawPath(iconPath, iconPaint);
        } else {
            // Stop bars
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
}
