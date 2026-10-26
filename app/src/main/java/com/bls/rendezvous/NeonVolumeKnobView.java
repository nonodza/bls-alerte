package com.bls.rendezvous;

import android.content.Context;
import android.graphics.*;
import android.view.View;

public class NeonVolumeKnobView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean active = false;
    private float progress = 0.75f; // 75% من الدائرة منورة

    public NeonVolumeKnobView(Context context) {
        super(context);
        setClickable(true);
        setFocusable(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public boolean isActive(){ return active; }
    public void setActive(boolean a){
        active = a;
        progress = a ? 1f : 0.75f;
        invalidate();
        animate().scaleX(0.92f).scaleY(0.92f).setDuration(80)
            .withEndAction(() -> animate().scaleX(1f).scaleY(1f).setDuration(120).start()).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cx = getWidth()/2f, cy = getHeight()/2f;
        float r = Math.min(getWidth(), getHeight())/2f - dp(22);
        if(r <= 0) return;

        // 1- النقاط الخارجية
        int dots = 48;
        int neon = Color.parseColor("#A6FF00");
        for(int i=0; i<dots; i++){
            float angle = (float)(i * 360f / dots - 90);
            double rad = Math.toRadians(angle);
            float rr = r + dp(32);
            float x = cx + (float)(rr * Math.cos(rad));
            float y = cy + (float)(rr * Math.sin(rad));
            boolean lit = i < dots * progress;
            dotPaint.setColor(lit ? neon : Color.parseColor("#1E293B"));
            dotPaint.setAlpha(lit ? 255 : 100);
            if(lit) dotPaint.setShadowLayer(dp(6),0,0,neon);
            else dotPaint.clearShadowLayer();
            canvas.drawCircle(x, y, dp(2.5f), dotPaint);
        }
        dotPaint.clearShadowLayer();

        // 2- القوس الأخضر النيوني
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(7));
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setColor(neon);
        paint.setShadowLayer(dp(12),0,0,neon);
        RectF oval = new RectF(cx-r, cy-r, cx+r, cy+r);
        canvas.drawArc(oval, -90, 270*progress, false, paint);
        paint.clearShadowLayer();

        // 3- المقبض 3D غامق
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(new RadialGradient(cx, cy, r,
            new int[]{Color.parseColor("#3A445C"), Color.parseColor("#1F2937"), Color.parseColor("#0F172A")},
            null, Shader.TileMode.CLAMP));
        canvas.drawCircle(cx, cy, r, paint);
        paint.setShader(null);

        // حفرة في الوسط
        paint.setColor(Color.parseColor("#111827"));
        paint.setAlpha(180);
        canvas.drawCircle(cx, cy, r - dp(18), paint);
        paint.setAlpha(255);

        // نقطة خضراء صغيرة فوق
        paint.setColor(neon);
        paint.setShadowLayer(dp(6),0,0,neon);
        canvas.drawCircle(cx, cy - r + dp(18), dp(3), paint);
        paint.clearShadowLayer();
    }
    private float dp(float v){ return v * getResources().getDisplayMetrics().density; }
}
