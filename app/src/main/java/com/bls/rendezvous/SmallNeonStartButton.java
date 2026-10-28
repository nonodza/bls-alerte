
package com.bls.rendezvous;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
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
    private boolean isNeonVisible = false;
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
    private final Matrix shaderMatrix = new Matrix();

    private float density;
    private float scaledDensity;
    private float rotation = 0f;

    private ValueAnimator animator;
    private SweepGradient neonGradient;

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
        neonBasePaint.setStrokeWidth(dp(1.5f));
        neonBasePaint.setStrokeCap(Paint.Cap.ROUND);
        neonBasePaint.setStrokeJoin(Paint.Join.ROUND);

        neonGlowPaint.setStyle(Paint.Style.STROKE);
        neonGlowPaint.setStrokeWidth(dp(5f));
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

        // Enable soft neon glow
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        updateAccessibility();

        // Two complete rotations: 1800 ms x 2 = 3.6 seconds
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1800L);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            rotation = (float) animation.getAnimatedValue();
            updateNeonShader();
            invalidate();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isNeonVisible = true;
                updateNeonShader();
                invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isNeonVisible = false;
                rotation = 0f;
                updateNeonShader();
                invalidate();
            }
        });
    }

    private void createNeonShader() {
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        if (centerX <= 0 || centerY <= 0) {
            return;
        }

        // Same cyan neon color in both START and STOP.
        // Brightness varies smoothly around the complete outline.
        int[] colors = new int[]{
                Color.parseColor("#FF00AEEF"),
                Color.parseColor("#FF00F5FF"),
                Color.parseColor("#FF70FFFF"),
                Color.parseColor("#FF00E5FF"),
                Color.parseColor("#FF007FAF"),
                Color.parseColor("#FF00AEEF")
        };

        float[] positions = new float[]{
                0f, 0.20f, 0.38f, 0.60f, 0.82f, 1f
        };

        neonGradient = new SweepGradient(
                centerX,
                centerY,
                colors,
                positions
        );

        updateNeonShader();
    }

    private void updateNeonShader() {
        if (neonGradient == null) {
            return;
        }

        // Rotate the light distribution around the button center.
        shaderMatrix.reset();
        shaderMatrix.setRotate(
                rotation * 360f,
                getWidth() / 2f,
                getHeight() / 2f
        );

        neonGradient.setLocalMatrix(shaderMatrix);

        neonBasePaint.setShader(neonGradient);
        neonGlowPaint.setShader(neonGradient);
        neonCorePaint.setShader(neonGradient);
    }

    public void setActive(boolean active) {
        if (isActive == active) {
            return;
        }

        isActive = active;
        text = active ? "STOP" : "START";

        updateAccessibility();
        requestLayout();

        if (animator != null) {
            animator.cancel();

            rotation = 0f;
            isNeonVisible = false;

            updateNeonShader();
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
        // Do not animate automatically when the page opens.
    }

    @Override
    protected void onDetachedFromWindow() {
        if (animator != null) {
            animator.cancel();
            isNeonVisible = false;
        }

        super.onDetachedFromWindow();
    }

    @Override
    protected void onSizeChanged(
            int width,
            int height,
            int oldWidth,
            int oldHeight
    ) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        createNeonShader();
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

        // Blue gradient background remains unchanged
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

        // Complete rounded outline
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

        // Static border remains visible when the animation is off
        borderPaint.setColor(
                Color.parseColor("#FFE8FCFF")
        );

        canvas.drawPath(borderPath, borderPaint);

        if (isNeonVisible && neonGradient != null) {

            // The entire outline glows, not a separate moving segment.
            neonGlowPaint.setStrokeWidth(dp(5f));
            neonGlowPaint.setMaskFilter(
                    new BlurMaskFilter(
                            dp(4f),
                            BlurMaskFilter.Blur.NORMAL
                    )
            );

            canvas.drawPath(borderPath, neonGlowPaint);
            neonGlowPaint.setMaskFilter(null);

            // Continuous colored neon line
            neonBasePaint.setStrokeWidth(dp(2f));
            canvas.drawPath(borderPath, neonBasePaint);

            // Fine luminous core, using the same rotating gradient
            neonCorePaint.setStrokeWidth(dp(1.3f));
            canvas.drawPath(borderPath, neonCorePaint);

        } else {
            // Subtle static cyan outline, no moving glow
            neonBasePaint.setShader(null);
            neonBasePaint.setColor(
                    Color.parseColor("#6653F5FF")
            );
            neonBasePaint.setStrokeWidth(dp(1.2f));

            canvas.drawPath(borderPath, neonBasePaint);

            // Restore the rotating shader for the next animation
            if (neonGradient != null) {
                neonBasePaint.setShader(neonGradient);
            }
        }

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
