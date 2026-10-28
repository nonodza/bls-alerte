package com.bls.rendezvous;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class NeonIconView extends View {

    private final Paint iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint neonGlowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint neonCorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF iconBounds = new RectF();
    private final Path iconPath = new Path();
    private final Path segmentPath = new Path();
    private final Path extraSegmentPath = new Path();

    private float density;
    private float rotation = 0f;
    private ValueAnimator animator;

    public NeonIconView(Context context) {
        super(context);
        init();
    }

    public NeonIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private float dp(float value) {
        return value * density;
    }

    private void init() {
        density = getResources().getDisplayMetrics().density;

        // إعدادات صباغة الأيقونة الأساسية
        iconPaint.setColor(Color.WHITE);
        iconPaint.setStyle(Paint.Style.FILL);

        // إعدادات خط التوهج الخارجي للنيون (Glow)
        neonGlowPaint.setStyle(Paint.Style.STROKE);
        neonGlowPaint.setStrokeWidth(dp(4f));
        neonGlowPaint.setStrokeCap(Paint.Cap.ROUND);
        neonGlowPaint.setStrokeJoin(Paint.Join.ROUND);

        // إعدادات قلب خط النيون الداخلي (Core)
        neonCorePaint.setStyle(Paint.Style.STROKE);
        neonCorePaint.setStrokeWidth(dp(1.8f));
        neonCorePaint.setStrokeCap(Paint.Cap.ROUND);
        neonCorePaint.setStrokeJoin(Paint.Join.ROUND);

        setClickable(true);
        setFocusable(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // مهم جداً لتفعيل التوهج والـ Blur

        // الانيميشن: دورتين كاملتين فقط عند النقر
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(800L); // سرعة اللفة الواحدة
        animator.setRepeatCount(1);  // المجموع دورتين (الأساسية + تكرار واحد)
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            rotation = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotation = 0f;
                invalidate(); // إخفاء النيون عند الانتهاء
            }
        });

        // تشغيل الحركة عند النقر على الأيقونة
        setOnClickListener(v -> {
            if (animator != null) {
                animator.cancel();
                rotation = 0f;
                animator.start();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        if (width <= 0 || height <= 0) return;

        // 1. تحديد حجم وموقع الأيقونة في المنتصف (مثلاً 24dp)
        float iconSize = dp(24f);
        float left = (width - iconSize) / 2f;
        float top = (height - iconSize) / 2f;
        iconBounds.set(left, top, left + iconSize, top + iconSize);

        // 2. بناء مسار الأيقونة (هنا كمثال سهم أو مثلث Play)
        iconPath.reset();
        iconPath.moveTo(iconBounds.left, iconBounds.top);
        iconPath.lineTo(iconBounds.right, iconBounds.top + (iconSize / 2f));
        iconPath.lineTo(iconBounds.left, iconBounds.bottom);
        iconPath.close();

        // رسم الأيقونة البيضاء الثابتة أولاً
        canvas.drawPath(iconPath, iconPaint);

        // 3. كود النيون الذي يدور حول حدود الأيقونة (يعمل فقط أثناء الحركة)
        if (animator != null && animator.isRunning()) {
            PathMeasure pm = new PathMeasure(iconPath, true);
            float pathLength = pm.getLength();

            // طول خط النيون المشع (ربع محيط الأيقونة)
            float segmentLength = pathLength * 0.25f; 
            float startOffset = rotation * pathLength;
            float endOffset = startOffset + segmentLength;

            segmentPath.reset();
            extraSegmentPath.reset();

            // قص المسار لتحريك شعاع النيون بسلاسة
            if (endOffset <= pathLength) {
                pm.getSegment(startOffset, endOffset, segmentPath, true);
            } else {
                pm.getSegment(startOffset, pathLength, segmentPath, true);
                pm.getSegment(0f, endOffset - pathLength, extraSegmentPath, true);
            }

            // ألوان النيون (سماوي مشع)
            int glowColor = Color.parseColor("#FF00E5FF");
            int coreColor = Color.parseColor("#E0F7FA");

            neonGlowPaint.setColor(glowColor);
            neonGlowPaint.setMaskFilter(new BlurMaskFilter(dp(3f), BlurMaskFilter.Blur.NORMAL));
            neonCorePaint.setColor(coreColor);

            // رسم خط النيون المشع الملتف حول الأيقونة
            canvas.drawPath(segmentPath, neonGlowPaint);
            canvas.drawPath(extraSegmentPath, neonGlowPaint);
            canvas.drawPath(segmentPath, neonCorePaint);
            canvas.drawPath(extraSegmentPath, neonCorePaint);

            neonGlowPaint.setMaskFilter(null);
        }
    }
}
