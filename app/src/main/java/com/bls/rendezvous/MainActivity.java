package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.view.Gravity;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;

public class MainActivity extends Activity {
 @Override
 protected void onCreate(Bundle b) {
  super.onCreate(b);
  home();
 }
 
 void home(){
  int[] c = {Color.parseColor("#0F0C29"),Color.parseColor("#302B63"),Color.parseColor("#00B4DB")};
  GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TL_BR,c);
  ScrollView sv = new ScrollView(this);
  LinearLayout m = new LinearLayout(this);
  m.setOrientation(1);
  m.setGravity(17);
  m.setPadding(dp(28),dp(70),dp(28),dp(30));
  m.setBackground(bg);
  
  TextView logo = new TextView(this);
  logo.setText("BLS");
  logo.setTextSize(92);
  logo.setTextColor(-1);
  logo.setGravity(17);
  logo.setTypeface(null,1);
  logo.setLetterSpacing(0.1f);
  m.addView(logo);
  
  TextView t = new TextView(this);
  t.setText("INTERNATIONAL\nVisa & Passport Services");
  t.setTextSize(13);
  t.setTextColor(Color.parseColor("#B0BEC5"));
  t.setGravity(17);
  t.setLetterSpacing(0.15f);
  t.setPadding(0,dp(8),0,dp(40));
  m.addView(t);
  
  m.addView(proCard("PASSPORT SERVICES","Apply • Renew • Track Status","P",new String[]{"#FF416C","#FF4B2B"},() -> passport()));
  m.addView(proCard("VISA CENTERS","Official BLS Centers & Directions","V",new String[]{"#00C9FF","#92FE9D"},() -> {
   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/BLS+Spain+Visa+Algeria")));
  }));
  
  TextView btn = new TextView(this);
  btn.setText("GET STARTED →");
  btn.setTextSize(16);
  btn.setTextColor(Color.parseColor("#0F0C29"));
  btn.setGravity(17);
  btn.setTypeface(null,1);
  btn.setLetterSpacing(0.08f);
  GradientDrawable bbg = new GradientDrawable();
  bbg.setCornerRadius(dp(30));
  bbg.setColor(-1);
  btn.setBackground(bbg);
  btn.setElevation(dp(10));
  LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(-1,dp(62));
  pr.topMargin=dp(35);
  btn.setLayoutParams(pr);
  btn.setPadding(0,dp(18),0,dp(18));
  btn.setOnClickListener(v -> login());
  m.addView(btn);
  
  TextView foot = new TextView(this);
  foot.setText("Trusted by 2M+ travelers worldwide");
  foot.setTextSize(11);
  foot.setTextColor(Color.parseColor("#80FFFFFF"));
  foot.setGravity(17);
  foot.setPadding(0,dp(25),0,0);
  m.addView(foot);
  
  sv.addView(m);
  setContentView(sv);
 }
 
 void passport(){
  LinearLayout m = new LinearLayout(this);
  m.setOrientation(1);
  m.setPadding(dp(24),dp(50
