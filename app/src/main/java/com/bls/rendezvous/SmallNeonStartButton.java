// =========================================================
// APPOINTMENT MONITORING CARD — FINAL PREMIUM (كيما الصورة)
// =========================================================
LinearLayout mainCard = new LinearLayout(this);
mainCard.setOrientation(LinearLayout.VERTICAL);
GradientDrawable mainBg = new GradientDrawable();
mainBg.setColor(Color.WHITE);
mainBg.setCornerRadius(dp(20));
mainCard.setBackground(mainBg);
mainCard.setElevation(dp(10));

// ===== TOP PART =====
LinearLayout topPart = new LinearLayout(this);
topPart.setOrientation(LinearLayout.HORIZONTAL);
topPart.setGravity(Gravity.CENTER_VERTICAL);
topPart.setPadding(dp(0), dp(16), dp(16), dp(16));

// الشريط الأزرق اليسار
View blueStrip = new View(this);
GradientDrawable stripBg = new GradientDrawable();
stripBg.setColor(Color.parseColor("#0B57D0"));
stripBg.setCornerRadii(new float[]{dp(20),0,0,dp(20),0,0,0,0});
blueStrip.setLayoutParams(new LinearLayout.LayoutParams(dp(8), dp(110)));
blueStrip.setBackground(stripBg);

// أيقونة فيكتوري دائرية زرقاء
ImageView calIcon = new ImageView(this);
GradientDrawable circleBg = new GradientDrawable();
circleBg.setShape(GradientDrawable.OVAL);
circleBg.setColor(Color.parseColor("#1A5EDC"));
calIcon.setBackground(circleBg);
calIcon.setImageResource(android.R.drawable.ic_menu_today); // تقدر تبدلو بفيكتور خاص
calIcon.setColorFilter(Color.WHITE);
LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dp(58), dp(58));
iconLp.setMargins(dp(14),0,dp(14),0);
calIcon.setLayoutParams(iconLp);
calIcon.setPadding(dp(12),dp(12),dp(12),dp(12));

// الوسط
LinearLayout centerCol = new LinearLayout(this);
centerCol.setOrientation(LinearLayout.VERTICAL);
centerCol.setLayoutParams(new LinearLayout.LayoutParams(0,-2,1f));

TextView title = text("Appointment Monitoring", 18, Color.parseColor("#101840"));
title.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
TextView sub = text("Real-time scheduling & status tracking", 12, Color.parseColor("#6B7280"));
sub.setPadding(0,dp(2),0,dp(10));
centerCol.addView(title);
centerCol.addView(sub);

// سطر العلم + الحالة - ديناميكي
LinearLayout flagLine = new LinearLayout(this);
flagLine.setOrientation(LinearLayout.HORIZONTAL);
flagLine.setGravity(Gravity.CENTER_VERTICAL);

ImageView flagImg = new ImageView(this);
TextView countryLine = new TextView(this);
countryLine.setTextSize(13);
countryLine.setTextColor(Color.parseColor("#374151"));

LinearLayout activeLine = new LinearLayout(this);
activeLine.setOrientation(LinearLayout.HORIZONTAL);
activeLine.setGravity(Gravity.CENTER_VERTICAL);
activeLine.setPadding(0,dp(4),0,0);
View greenDot = new View(this);
GradientDrawable dotBg = new GradientDrawable();
dotBg.setShape(GradientDrawable.OVAL);
dotBg.setColor(Color.parseColor("#10B981"));
greenDot.setBackground(dotBg);
greenDot.setLayoutParams(new LinearLayout.LayoutParams(dp(10), dp(10)));
TextView activeTxt = text("Active", 13, Color.parseColor("#059669"));
activeTxt.setTypeface(Typeface.DEFAULT_BOLD);
activeTxt.setPadding(dp(6),0,0,0);

if (selectedCountry == null || selectedCountry.isEmpty()) {
    flagImg.setVisibility(View.GONE);
    greenDot.setVisibility(View.GONE);
    activeTxt.setVisibility(View.GONE);
    countryLine.setText("Select a country to start");
    countryLine.setTextColor(Color.parseColor("#9CA3AF"));
} else {
    flagImg.setVisibility(View.VISIBLE);
    // هنا حط العلم حسب الدولة المختارة
    // flagImg.setImageResource(getFlagDrawable(selectedCountry));
    flagImg.setLayoutParams(new LinearLayout.LayoutParams(dp(38), dp(26)));
    countryLine.setText(selectedCountry + " • Last check 2 min ago");
    if (isMonitoringActive) {
        greenDot.setVisibility(View.VISIBLE);
        activeTxt.setVisibility(View.VISIBLE);
    } else {
        greenDot.setVisibility(View.GONE);
        activeTxt.setVisibility(View.GONE);
    }
}

flagLine.addView(flagImg);
flagLine.addView(countryLine);
centerCol.addView(flagLine);

LinearLayout activeWrapper = new LinearLayout(this);
activeWrapper.setOrientation(LinearLayout.HORIZONTAL);
activeWrapper.addView(greenDot);
activeWrapper.addView(activeTxt);
activeWrapper.setPadding(dp(46),0,0,0); // تحت العلم
if(selectedCountry!=null && !selectedCountry.isEmpty() && isMonitoringActive){
    centerCol.addView(activeWrapper);
}

// زر START/STOP
SmallNeonStartButton startBtn = new SmallNeonStartButton(this);
startBtn.setActive(isMonitoringActive);
startBtn.setOnClickListener(v -> {
    if (selectedCountry == null || selectedCountry.isEmpty()) {
        Toast.makeText(this, "Please select a country first", Toast.LENGTH_SHORT).show();
        // openCountrySelector();
        return;
    }
    isMonitoringActive = !isMonitoringActive;
    startBtn.setActive(isMonitoringActive);
    // startMonitoring() / stopMonitoring()
});

topPart.addView(blueStrip);
topPart.addView(calIcon);
topPart.addView(centerCol);
topPart.addView(startBtn);

// ===== BOTTOM PART — Background settings =====
View divider = new View(this);
divider.setBackgroundColor(Color.parseColor("#E5E7EB"));
divider.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(1)));

LinearLayout bottomPart = new LinearLayout(this);
bottomPart.setOrientation(LinearLayout.HORIZONTAL);
bottomPart.setGravity(Gravity.CENTER_VERTICAL);
bottomPart.setPadding(dp(14), dp(10), dp(14), dp(10));
bottomPart.setBackgroundColor(Color.parseColor("#F8FAFC"));

TextView enhanced = text("🛡 Enhanced features: Real-time tracking • Automated alerts", 10, Color.parseColor("#4B5563"));
enhanced.setLayoutParams(new LinearLayout.LayoutParams(0,-2,1f));

LinearLayout toggles = new LinearLayout(this);
toggles.setOrientation(LinearLayout.HORIZONTAL);
toggles.setGravity(Gravity.CENTER_VERTICAL);
toggles.addView(createToggle("Sound On", true));
toggles.addView(createToggle("Push Alert", true));
toggles.addView(createToggle("Alert", true));

bottomPart.addView(enhanced);
bottomPart.addView(toggles);

mainCard.addView(topPart);
mainCard.addView(divider);
mainCard.addView(bottomPart);

page.addView(mainCard, margin(0,0,0,12));

// ميثود صغيرة للـ Toggle
private LinearLayout createToggle(String label, boolean checked) {
    LinearLayout l = new LinearLayout(this);
    l.setOrientation(LinearLayout.HORIZONTAL);
    l.setGravity(Gravity.CENTER_VERTICAL);
    l.setPadding(dp(8),0,0,0);
    TextView tv = text(label, 10, Color.parseColor("#374151"));
    Switch sw = new Switch(this);
    sw.setChecked(checked);
    sw.setScaleX(0.7f); sw.setScaleY(0.7f);
    l.addView(tv); l.addView(sw);
    return l;
}
