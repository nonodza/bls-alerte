
package com.bls.rendezvous;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class SmallNeonCheckButton extends View {

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rect = new RectF();
    private final Matrix matrix = new Matrix();

    private SweepGradient gradient;
    private ValueAnimator animator;

    private boolean checking = false;
    private float rotation = 0f;

    private int viewWidth;
    private int viewHeight;

    private final int blue = Color.rgb(36, 119, 255);
    private final int cyan = Color.rgb(0, 225, 255);
    private final int darkBlue = Color.rgb(20, 54, 135);

    public SmallNeonCheckButton(Context context) {
        super(context);
        init();
    }

    public SmallNeonCheckButton(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setClickable(true);
        setFocusable(true);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1.5f));
        borderPaint.setColor(cyan);

        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(dp(3f));
        glowPaint.setColor(cyan);
        glowPaint.setShadowLayer(dp(5f), 0f, 0f, cyan);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dp(9f));
        textPaint.setTypeface(
                Typeface.create("sans-serif", Typeface.BOLD)
        );
        textPaint.setTextAlign(Paint.Align.CENTER);

        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.STROKE);
        iconPaint.setStrokeWidth(dp(1.8f));
        iconPaint.setStrokeCap(Paint.Cap.ROUND);
        iconPaint.setStrokeJoin(Paint.Join.ROUND);
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
        setMeasuredDimension(
                resolveSize((int) dp(92), widthMeasureSpec),
                resolveSize((int) dp(42), heightMeasureSpec)
        );
    }

    @Override
    protected void onSizeChanged(
            int w,
            int h,
            int oldw,
            int oldh
    ) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewWidth = w;
        viewHeight = h;

        float inset = dp(4f);
        rect.set(inset, inset, w - inset, h - inset);

        gradient = new SweepGradient(
                w / 2f,
                h / 2f,
                new int[]{
                        cyan,
                        blue,
                        cyan,
                        Color.rgb(70, 170, 255),
                        cyan
                },
                null
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (viewWidth <= 0 || viewHeight <= 0) return;

        float radius = dp(13f);

        bgPaint.setShader(
                new LinearGradient(
                        0, 0, viewWidth, viewHeight,
                        new int[]{blue, darkBlue, blue},
                        null,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(rect, radius, radius, bgPaint);
        bgPaint.setShader(null);

        if (checking && gradient != null) {
            matrix.setRotate(
                    rotation,
                    viewWidth / 2f,
                    viewHeight / 2f
            );
            gradient.setLocalMatrix(matrix);

            glowPaint.setShader(gradient);
            canvas.drawRoundRect(rect, radius, radius, glowPaint);
            glowPaint.setShader(null);

            borderPaint.setShader(gradient);
            canvas.drawRoundRect(rect, radius, radius, borderPaint);
            borderPaint.setShader(null);
        } else {
            borderPaint.setColor(cyan);
            canvas.drawRoundRect(rect, radius, radius, borderPaint);
        }

        drawContent(canvas);
    }

    private void drawContent(Canvas canvas) {
        String label = checking ? "CHECKING..." : "CHECK NOW";

        float cy = viewHeight / 2f;
        float iconSize = dp(11f);
        float gap = dp(4f);

        float textWidth = textPaint.measureText(label);
        float totalWidth = iconSize + gap + textWidth;
        float x = (viewWidth - totalWidth) / 2f;

        // Search icon
        float cx = x + iconSize * 0.38f;
        float circleY = cy - dp(1.5f);
        float radius = dp(3.2f);

        canvas.drawCircle(cx, circleY, radius, iconPaint);
        canvas.drawLine(
                cx + radius * 0.7f,
                circleY + radius * 0.7f,
                cx + radius * 1.6f,
                circleY + radius * 1.6f,
                iconPaint
        );

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float baseline = cy - (fm.ascent + fm.descent) / 2f;

        textPaint.setColor(Color.WHITE);
        canvas.drawText(
                label,
                x + iconSize + gap + textWidth / 2f,
                baseline,
                textPaint
        );
    }

    public void setChecking(boolean value) {
        if (checking == value) return;

        checking = value;

        if (animator != null) {
            animator.cancel();
            animator = null;
        }

        if (checking) {
            rotation = 0f;

            animator = ValueAnimator.ofFloat(0f, 360f);
            animator.setDuration(1400L);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());

            animator.addUpdateListener(a -> {
                rotation = (float) a.getAnimatedValue();
                invalidate();
            });

            animator.start();
        }

        invalidate();
    }

    public boolean isChecking() {
        return checking;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        super.onDetachedFromWindow();
    }
}
