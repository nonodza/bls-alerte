
package com.bls.rendezvous;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
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
    private float rotation = 0f;

    private ValueAnimator animator;

    public SmallNeonStartButton(Context context) {
        super(context);
        init();
    }

    public SmallNeonStartButton(Context context, AttributeSet attrs) {
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
                Typeface.create("sans-serif", Typeface.BOLD)
        );
        textPaint.setTextAlign(Paint.Align.LEFT);

        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);

        setClickable(true);
        setFocusable(true);
        setElevation(dp(3));

        // Neon glow support
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        updateAccessibility();

        // Two complete laps, started only by setActive()
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1800L);
        animator.setRepeatCount(1);
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

        // Stop any previous animation and restart from the beginning.
        if (animator != null) {
            animator.cancel();
            rotation = 0f;
            invalidate();
            animator.start();
        }
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
                resolveSize(desiredWidth, widthMeasureSpec),
                resolveSize(desiredHeight, heightMeasureSpec)
        );
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Intentionally do not start the animation automatically.
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

        rect.set(
                inset,
                inset,
                width - inset,
                height - inset
        );

        // Blue gradient background
        bgPaint.setShader(
                new LinearGradient(
                        0, 0, width, height,
                        new int[]{
                                Color.parseColor("#1649E8"),
                                Color.parseColor("#087FE8"),
                                Color.parseColor("#123BC1")
                        },
                        null,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(rect, dp(12), dp(12), bgPaint);
        bgPaint.setShader(null);

        // Rounded path followed by the neon beam
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

        canvas.drawPath(borderPath, borderPaint);

        // Subtle static neon line
        neonBasePaint.setColor(
                isActive
                        ? Color.parseColor("#66FF7777")
                        : Color.parseColor("#6653F5FF")
        );

        canvas.drawPath(borderPath, neonBasePaint);

        // Animated neon segment
        PathMeasure pm = new PathMeasure(borderPath, true);
        float pathLength = pm.getLength();

        float segmentLength = pathLength * 0.25f;
        float startOffset = rotation * pathLength;
        float endOffset = startOffset + segmentLength;

        segmentPath.reset();
        extraSegmentPath.reset();

        if (endOffset <= pathLength) {
            pm.getSegment(
                    startOffset,
                    endOffset,
                    segmentPath,
                    true
            );
        } else {
            pm.getSegment(
                    startOffset,
                    pathLength,
                    segmentPath,
                    true
            );

            pm.getSegment(
                    0f,
                    endOffset - pathLength,
                    extraSegmentPath,
                    true
            );
        }

        int glowColor = isActive
                ? Color.parseColor("#FFFF5555")
                : Color.parseColor("#FF00E5FF");

        int coreColor = isActive
                ? Color.parseColor("#FFFFCCCC")
                : Color.parseColor("#FFE0FCFF");

        neonGlowPaint.setColor(glowColor);
        neonGlowPaint.setMaskFilter(
                new BlurMaskFilter(
                        dp(3f),
                        BlurMaskFilter.Blur.NORMAL
                )
        );

        neonCorePaint.setColor(coreColor);

        canvas.drawPath(segmentPath, neonGlowPaint);
        canvas.drawPath(extraSegmentPath, neonGlowPaint);

        canvas.drawPath(segmentPath, neonCorePaint);
        canvas.drawPath(extraSegmentPath, neonCorePaint);

        neonGlowPaint.setMaskFilter(null);

        // Centered icon and text
        float iconSize = dp(12f);
        float gap = dp(6f);

        float textWidth = textPaint.measureText(text);
        float totalContentWidth = iconSize + gap + textWidth;

        float startX = (width - totalContentWidth) / 2f;

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        float textY =
                (height - fontMetrics.bottom - fontMetrics.top) / 2f;

        float iconTop = (height - iconSize) / 2f;

        iconPath.reset();

        if (isActive) {
            // STOP square
            iconPath.addRect(
                    startX,
                    iconTop,
                    startX + iconSize,
                    iconTop + iconSize,
                    Path.Direction.CW
            );
        } else {
            // START triangle
            iconPath.moveTo(startX, iconTop);
            iconPath.lineTo(
                    startX + iconSize,
                    iconTop + iconSize / 2f
            );
            iconPath.lineTo(
                    startX,
                    iconTop + iconSize
            );
            iconPath.close();
        }

        canvas.drawPath(iconPath, iconPaint);

        canvas.drawText(
                text,
                startX + iconSize + gap,
                textY,
                textPaint
        );
    }
}
