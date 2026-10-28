package com.bls.rendezvous;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class SmallNeonStartButton extends View {

    private final Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Correctly declared icon path
    private final Path iconPath = new Path();

    private final RectF buttonRect = new RectF();
    private final RectF iconRect = new RectF();
    private final Matrix shaderMatrix = new Matrix();

    private SweepGradient neonGradient;
    private ValueAnimator neonAnimator;

    private boolean isActive = false;
    private boolean isNeonVisible = false;

    private float animationRotation = 0f;

    private int buttonWidth;
    private int buttonHeight;

    private final int blue = Color.rgb(36, 119, 255);
    private final int cyan = Color.rgb(0, 225, 255);
    private final int darkBlue = Color.rgb(20, 54, 135);

    public SmallNeonStartButton(Context context) {
        super(context);
        init();
    }

    public SmallNeonStartButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmallNeonStartButton(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        backgroundPaint.setStyle(Paint.Style.FILL);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1.5f));
        borderPaint.setColor(cyan);

        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(dp(3f));
        glowPaint.setColor(cyan);
        glowPaint.setShadowLayer(dp(5f), 0f, 0f, cyan);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dp(11f));
        textPaint.setTypeface(
                Typeface.create("sans-serif", Typeface.BOLD)
        );
        textPaint.setTextAlign(Paint.Align.CENTER);

        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);
        iconPaint.setStrokeWidth(dp(1.8f));
        iconPaint.setStrokeCap(Paint.Cap.ROUND);
        iconPaint.setStrokeJoin(Paint.Join.ROUND);

        setClickable(true);
        setFocusable(true);
    }

    private float dp(float value) {
        return value * getResources()
                .getDisplayMetrics().density;
    }

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        int desiredWidth = (int) dp(92);
        int desiredHeight = (int) dp(42);

        int measuredWidth = resolveSize(
                desiredWidth,
                widthMeasureSpec
        );

        int measuredHeight = resolveSize(
                desiredHeight,
                heightMeasureSpec
        );

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onSizeChanged(
            int w,
            int h,
            int oldw,
            int oldh
    ) {
        super.onSizeChanged(w, h, oldw, oldh);

        buttonWidth = w;
        buttonHeight = h;

        float inset = dp(4f);

        buttonRect.set(
                inset,
                inset,
                w - inset,
                h - inset
        );

        createNeonGradient();
    }

    private void createNeonGradient() {
        float centerX = buttonWidth / 2f;
        float centerY = buttonHeight / 2f;

        neonGradient = new SweepGradient(
                centerX,
                centerY,
                new int[]{
                        cyan,
                        blue,
                        cyan,
                        Color.rgb(70, 170, 255),
                        cyan
                },
                new float[]{
                        0f,
                        0.25f,
                        0.50f,
                        0.75f,
                        1f
                }
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (buttonWidth <= 0 || buttonHeight <= 0) {
            return;
        }

        float radius = dp(13f);

        // Premium blue gradient background
        backgroundPaint.setShader(
                new LinearGradient(
                        0f,
                        0f,
                        buttonWidth,
                        buttonHeight,
                        new int[]{
                                blue,
                                darkBlue,
                                blue
                        },
                        null,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(
                buttonRect,
                radius,
                radius,
                backgroundPaint
        );

        backgroundPaint.setShader(null);

        if (isNeonVisible && neonGradient != null) {
            shaderMatrix.setRotate(
                    animationRotation,
                    buttonWidth / 2f,
                    buttonHeight / 2f
            );

            neonGradient.setLocalMatrix(shaderMatrix);

            // Soft cyan glow around the entire outline
            glowPaint.setShader(neonGradient);
            canvas.drawRoundRect(
                    buttonRect,
                    radius,
                    radius,
                    glowPaint
            );

            glowPaint.setShader(null);

            // Continuous neon border
            borderPaint.setShader(neonGradient);
            canvas.drawRoundRect(
                    buttonRect,
                    radius,
                    radius,
                    borderPaint
            );

            borderPaint.setShader(null);
        } else {
            // Static cyan outline when idle
            borderPaint.setColor(cyan);
            borderPaint.setStrokeWidth(dp(1.5f));

            canvas.drawRoundRect(
                    buttonRect,
                    radius,
                    radius,
                    borderPaint
            );
        }

        drawButtonContent(canvas);
    }

    private void drawButtonContent(Canvas canvas) {
        float centerY = buttonHeight / 2f;

        float iconSize = dp(11f);
        float gap = dp(5f);

        String label = isActive ? "STOP" : "START";

        float textWidth = textPaint.measureText(label);
        float totalWidth = iconSize + gap + textWidth;

        float startX = (buttonWidth - totalWidth) / 2f;

        iconPath.reset();

        if (isActive) {
            // STOP icon: rounded square
            float iconTop = centerY - iconSize / 2f;

            iconRect.set(
                    startX,
                    iconTop,
                    startX + iconSize,
                    iconTop + iconSize
            );

            float stopInset = dp(3f);

            iconPath.addRect(
                    iconRect.left + stopInset,
                    iconRect.top + stopInset,
                    iconRect.right - stopInset,
                    iconRect.bottom - stopInset,
                    Path.Direction.CW
            );
        } else {
            // START icon: play triangle
            float iconTop = centerY - iconSize / 2f;

            iconPath.moveTo(startX, iconTop);
            iconPath.lineTo(
                    startX + iconSize,
                    centerY
            );
            iconPath.lineTo(
                    startX,
                    iconTop + iconSize
            );
            iconPath.close();
        }

        canvas.drawPath(iconPath, iconPaint);

        textPaint.setColor(Color.WHITE);

        Paint.FontMetrics metrics = textPaint.getFontMetrics();

        float textBaseline = centerY
                - (metrics.ascent + metrics.descent) / 2f;

        canvas.drawText(
                label,
                startX + iconSize + gap + textWidth / 2f,
                textBaseline,
                textPaint
        );
    }

    public void setActive(boolean active) {
        if (isActive == active) {
            return;
        }

        isActive = active;

        startNeonAnimation();

        invalidate();
    }

    public boolean isActive() {
        return isActive;
    }

    private void startNeonAnimation() {
        if (neonAnimator != null) {
            neonAnimator.cancel();
            neonAnimator = null;
        }

        isNeonVisible = true;
        animationRotation = 0f;

        neonAnimator = ValueAnimator.ofFloat(0f, 720f);
        neonAnimator.setDuration(3600L);
        neonAnimator.setInterpolator(
                new LinearInterpolator()
        );

        // Two complete rotations
        neonAnimator.setRepeatCount(0);

        neonAnimator.addUpdateListener(animation -> {
            animationRotation = (float) animation.getAnimatedValue();
            invalidate();
        });

        neonAnimator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isNeonVisible = false;
                        animationRotation = 0f;
                        invalidate();
                    }
                }
        );

        neonAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (neonAnimator != null) {
            neonAnimator.cancel();
            neonAnimator = null;
        }

        super.onDetachedFromWindow();
    }
}
