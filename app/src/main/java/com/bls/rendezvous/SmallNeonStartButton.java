
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
    private final Path extraSegmentPath = new Path();
    private final Path iconPath = new Path();

    private float density;
    private float scaledDensity;

    // Animation progress: 0.0 to 1.0
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

        density = getResources()
                .getDisplayMetrics().density;

        scaledDensity = getResources()
                .getDisplayMetrics().scaledDensity;

        bgPaint.setStyle(Paint.Style.FILL);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1f));

        neonBasePaint.setStyle(Paint.Style.STROKE);
        neonBasePaint.setStrokeWidth(dp(1.2f));
        neonBasePaint.setStrokeCap(Paint.Cap.ROUND);
        neonBasePaint.setStrokeJoin(Paint.Join.ROUND);

        neonGlowPaint.setStyle(Paint.Style.STROKE);
        neonGlowPaint.setStrokeWidth(dp(4f));
        neonGlowPaint.setStrokeCap(Paint.Cap.ROUND);
        neonGlowPaint.setStrokeJoin(Paint.Join.ROUND);

        neonCorePaint.setStyle(Paint.Style.STROKE);
        neonCorePaint.setStrokeWidth(dp(1.8f));
        neonCorePaint.setStrokeCap(Paint.Cap.ROUND);
        neonCorePaint.setStrokeJoin(Paint.Join.ROUND);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(sp(12));
        textPaint.setTypeface(
                Typeface.create(
                        "sans-serif",
                        Typeface.BOLD
                )
        );
        textPaint.setTextAlign(Paint.Align.LEFT);

        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);

        setClickable(true);
        setFocusable(true);
        setElevation(dp(3));

        // Required for the neon glow on Android
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        updateAccessibility();

        // One continuous animator, independent of START / STOP
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2400L);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
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

        // Deliberately do not start, stop, or reset the animator here.
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
        float gap = dp(6);
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
                resolveSize(
                        desiredWidth,
                        widthMeasureSpec
                ),
                resolveSize(
                        desiredHeight,
                        heightMeasureSpec
                )
        );
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Restart animation only when the View is attached.
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
        float radius = dp(11f);

        // Button bounds
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

        // Build the exact rounded path followed by the neon beam
        float pathInset = dp(5f);

        ringRect.set(
                pathInset,
                pathInset,
                width - pathInset,
                height - pathInset
        );

        borderPath.reset();

        borderPath.addRoundRect(
                ringRect,
                radius,
                radius,
                Path.Direction.CW
        );

        // Static pale border
        borderPaint.setColor(
                isActive
                        ? Color.parseColor("#FFFFB9B9")
                        : Color.parseColor("#E8FCFF")
        );

        canvas.drawPath(
                borderPath,
                borderPaint
        );

        // Subtle continuous neon line
        neonBasePaint.setColor(
                isActive
                        ? Color.parseColor("#66FF7777")
                        : Color.parseColor("#6653F5FF")
        );

        canvas.drawPath(
                borderPath,
                neonBasePaint
        );

        // Moving light segment along the rounded rectangle
        PathMeasure pathMeasure = new PathMeasure(
                borderPath,
                true
        );

        float pathLength = pathMeasure.getLength();

        if (pathLength > 0f) {

            float segmentLength = Math.min(
                    dp(42f),
                    pathLength * 0.24f
            );

            float startDistance = rotation * pathLength;
            float endDistance = startDistance + segmentLength;

            segmentPath.reset();
            extraSegmentPath.reset();

            if (endDistance <= pathLength) {

                pathMeasure.getSegment(
                        startDistance,
                        endDistance,
                        segmentPath,
                        true
                );

            } else {

                // Draw the part before the path endpoint
                pathMeasure.getSegment(
                        startDistance,
                        pathLength,
                        segmentPath,
                        true
                );

                // Continue from the beginning without a visible jump
                pathMeasure.getSegment(
                        0f,
                        endDistance - pathLength,
                        extraSegmentPath,
                        true
                );

                segmentPath.addPath(extraSegmentPath);
            }

            int beamColor = isActive
                    ? Color.parseColor("#FFFF7777")
                    : Color.parseColor("#42F5FF");

            // Outer neon glow
            neonGlowPaint.setColor(beamColor);
            neonGlowPaint.setShadowLayer(
                    dp(7f),
                    0f,
                    0f,
                    beamColor
            );

            canvas.drawPath(
                    segmentPath,
                    neonGlowPaint
            );

            // Bright center of the moving beam
            neonCorePaint.setColor(
                    isActive
                            ? Color.WHITE
                            : Color.parseColor("#C8FFFF")
            );

            neonCorePaint.setShadowLayer(
                    dp(3f),
                    0f,
                    0f,
                    beamColor
            );

            canvas.drawPath(
                    segmentPath,
                    neonCorePaint
            );

            neonGlowPaint.clearShadowLayer();
            neonCorePaint.clearShadowLayer();
        }

        // Center the icon and label together
        float centerY = height / 2f;
        float iconSize = dp(11f);
        float gap = dp(6f);

        float textWidth = textPaint.measureText(text);
        float totalWidth = iconSize + gap + textWidth;
        float startX = (width - totalWidth) / 2f;

        if (!isActive) {

            // START play icon
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

            canvas.drawPath(
                    iconPath,
                    iconPaint
            );

        } else {

            // STOP icon
            float barWidth = dp(3f);
            float barHeight = dp(10f);
            float barGap = dp(2.5f);

            canvas.drawRoundRect(
                    startX,
                    centerY - barHeight / 2f,
                    startX + barWidth,
                    centerY + barHeight / 2f,
                    dp(1f),
                    dp(1f),
                    iconPaint
            );

            canvas.drawRoundRect(
                    startX + barWidth + barGap,
                    centerY - barHeight / 2f,
                    startX + barWidth * 2f + barGap,
                    centerY + barHeight / 2f,
                    dp(1f),
                    dp(1f),
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
