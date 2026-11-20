package com.bls.rendezvous;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class SmallNeonRefreshButton extends View {

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rect = new RectF();
    private final Path arrowPath = new Path();

    private int viewWidth;
    private int viewHeight;

    private final int blue = Color.rgb(36, 119, 255);
    private final int cyan = Color.rgb(0, 225, 255);
    private final int darkBlue = Color.rgb(20, 54, 135);

    public SmallNeonRefreshButton(Context context) {
        super(context);
        init();
    }

    public SmallNeonRefreshButton(
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
                android.graphics.Typeface.create(
                        "sans-serif",
                        android.graphics.Typeface.BOLD
                )
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

        rect.set(
                inset,
                inset,
                w - inset,
                h - inset
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (viewWidth <= 0 || viewHeight <= 0) return;

        float radius = dp(13f);

        bgPaint.setShader(
                new LinearGradient(
                        0, 0,
                        viewWidth, viewHeight,
                        new int[]{blue, darkBlue, blue},
                        null,
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

        glowPaint.setColor(cyan);
        canvas.drawRoundRect(
                rect,
                radius,
                radius,
                glowPaint
        );

        borderPaint.setColor(cyan);
        canvas.drawRoundRect(
                rect,
                radius,
                radius,
                borderPaint
        );

        drawContent(canvas);
    }

    private void drawContent(Canvas canvas) {

        String label = "REFRESH";

        float cy = viewHeight / 2f;
        float iconSize = dp(11f);
        float gap = dp(4f);

        float textWidth = textPaint.measureText(label);
        float totalWidth = iconSize + gap + textWidth;

        float x = (viewWidth - totalWidth) / 2f;

        // Circular refresh icon
        float cx = x + iconSize / 2f;
        float iconCy = cy;

        RectF iconRect = new RectF(
                cx - dp(4f),
                iconCy - dp(4f),
                cx + dp(4f),
                iconCy + dp(4f)
        );

        canvas.drawArc(
                iconRect,
                45f,
                285f,
                false,
                iconPaint
        );

        // Arrow head
        arrowPath.reset();

        arrowPath.moveTo(
                cx + dp(3.2f),
                iconCy - dp(4.8f)
        );

        arrowPath.lineTo(
                cx + dp(5.2f),
                iconCy - dp(4.5f)
        );

        arrowPath.lineTo(
                cx + dp(4.8f),
                iconCy - dp(2.5f)
        );

        canvas.drawPath(arrowPath, iconPaint);

        Paint.FontMetrics fm = textPaint.getFontMetrics();

        float baseline =
                cy - (fm.ascent + fm.descent) / 2f;

        canvas.drawText(
                label,
                x + iconSize + gap + textWidth / 2f,
                baseline,
                textPaint
        );
    }
}
