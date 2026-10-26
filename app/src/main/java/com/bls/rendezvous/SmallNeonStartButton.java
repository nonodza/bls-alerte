package com.bls.rendezvous;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class SmallNeonStartButton extends View {
    private Paint bgPaint, textPaint, shadowPaint;
    private RectF rect;
    private boolean active = false;
    private LinearGradient gradient;
    
    public SmallNeonStartButton(Context context) { super(context); init(); }
    public SmallNeonStartButton(Context context, AttributeSet attrs) { super(context, attrs); init(); }
    
    private void init() {
        setClickable(true); setFocusable(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dp(15));
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new RectF();
    }
    
    public boolean isActive(){ return active; }
    public void setActive(boolean a){
        active = a;
        invalidate();
        animate().scaleX(0.92f).scaleY(0.92f).setDuration(80)
        .withEndAction(() -> animate().scaleX(1f).scaleY(1f).setDuration(120).start()).start();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        float w = getWidth(), h = getHeight();
        rect.set(dp(6), dp(6), w-dp(6), h-dp(6));
        float r = dp(16);
        
        // shadow glow
        shadowPaint.setColor(Color.parseColor("#3B82FF"));
        shadowPaint.setShadowLayer(dp(14), 0, dp(6), Color.parseColor("#3B82FF"));
        canvas.drawRoundRect(rect, r, r, shadowPaint);
        shadowPaint.clearShadowLayer();
        
        // gradient blue -> cyan كيما التصميم
        if(!active){
            gradient = new LinearGradient(rect.left, rect.top, rect.right, rect.bottom,
                new int[]{Color.parseColor("#0A5CFF"), Color.parseColor("#00B5D6")},
                null, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient(rect.left, rect.top, rect.right, rect.bottom,
                new int[]{Color.parseColor("#FF3B3B"), Color.parseColor("#D6002A")},
                null, Shader.TileMode.CLAMP);
        }
        bgPaint.setShader(gradient);
        canvas.drawRoundRect(rect, r, r, bgPaint);
        
        // text with play icon
        String txt = active ? "■ STOP" : "▶ START";
        float cy = h/2f - (textPaint.descent()+textPaint.ascent())/2f;
        canvas.drawText(txt, w/2f, cy, textPaint);
    }
    
    private float dp(float v){ return v * getResources().getDisplayMetrics().density; }
    @Override protected void onMeasure(int w,int h){ setMeasuredDimension((int)dp(124),(int)dp(48)); }
}
