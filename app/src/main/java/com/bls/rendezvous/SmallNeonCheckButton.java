package com.bls.rendezvous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.View;

public class SmallNeonStopCheckButton extends View {

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rect = new RectF();

    private int viewWidth;
    private int viewHeight;

    private final int red = Color.rgb(239, 68, 68);
    private final int lightRed = Color.rgb(255, 105, 105);
    private final int darkRed = Color.rgb(153, 27, 27);

    public SmallNeonStopCheckButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setClickable(true);
        setFocusable(true);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dp(1.5f));
        borderPaint.setColor(lightRed);

        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setStrokeWidth(dp(3f));
        glowPaint.setColor(lightRed);
        glowPaint.setShadowLayer(dp(5f), 0f, 0f, lightRed);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dp(9f));
        textPaint.setTypeface(
                Typeface.create("sans-serif", Typeface.BOLD)
        );
        textPaint.setTextAlign(Paint.Align.CENTER);

        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (viewWidth <= 0 || viewHeight <= 0) return;

        float radius = dp(13f);

        bgPaint.setShader(
                new LinearGradient(
                        0, 0, viewWidth, viewHeight,
                        new int[]{red, darkRed, red},
                        null,
                        Shader.TileMode.CLAMP
                )
        );

        canvas.drawRoundRect(rect, radius, radius, bgPaint);
        bgPaint.setShader(null);

        canvas.drawRoundRect(rect, radius, radius, glowPaint);
        canvas.drawRoundRect(rect, radius, radius, borderPaint);

        drawContent(canvas);
    }

    private void drawContent(Canvas canvas) {

        String label = "STOP CHECK";

        float cy = viewHeight / 2f;
        float iconSize = dp(11f);
        float gap = dp(4f);

        float textWidth = textPaint.measureText(label);
        float totalWidth = iconSize + gap + textWidth;
        float x = (viewWidth - totalWidth) / 2f;

        // Stop icon
        float iconLeft = x + dp(1f);
        float iconTop = cy - dp(4.5f);

        RectF stopRect = new RectF(
                iconLeft,
                iconTop,
                iconLeft + dp(9f),
                iconTop + dp(9f)
        );

        canvas.drawRoundRect(
                stopRect,
                dp(1.5f),
                dp(1.5f),
                iconPaint
        );

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float baseline = cy - (fm.ascent + fm.descent) / 2f;

        canvas.drawText(
                label,
                x + iconSize + gap + textWidth / 2f,
                baseline,
                textPaint
        );
    }

    private float dpValue(float value) {
        return dp(value);
    }
}
