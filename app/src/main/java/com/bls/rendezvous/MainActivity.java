package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;

public class MainActivity extends Activity {
 @Override protected void onCreate(Bundle b){super.onCreate(b);home();}
 
 void home(){
  int[] c={Color.parseColor("#2D1B69"),Color.parseColor("#3B4CCA"),Color.parseColor("#00C9A7")};
  GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
  ScrollView sv=new ScrollView(this);
  LinearLayout m=new LinearLayout(this);
  m.setOrientation(1);m.setGravity(17);
  m.setPadding(dp(24),dp(60),dp(24),dp(40));m.setBackground(bg);
  
  TextView t1=new TextView(this);t1.setText("BLS");t1.setTextSize(90);
  t1.setTextColor(-1);t1.setGravity(17);t1.setTypeface(null,1);m.addView(t1);
  
  TextView t2=new TextView(this);
  t2.setText("Visa Application Services\nSecure Fast Official");
  t2.setTextSize(14);t2.setTextColor(-1);t2.setGravity(17);
  t2.setPadding(0,0,0,dp(40));m.addView(t2);

  // FIX CLICK - Passport
  LinearLayout c1=makeCard("Passport Services","Apply, Renew & Track Passport");
  c1.setOnClickListener(v->{Toast.makeText(this,"Passport click OK",0).show();passport();});
  m.addView(c1);
  
  // FIX CLICK - Visa Maps
  LinearLayout c2=makeCard("Visa Centers","Find Nearest BLS Center & Directions");
  c2.setOnClickListener(v->{Toast.makeText(this,"Opening Maps",0).show();startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.google.com/?q=BLS+Spain+Algeria")));});
  m.addView(c2);
  
  // FIX CLICK - Get Started
  TextView btn=new TextView(this);btn.setText("Get Started");btn.setTextSize(20);
  btn.setTextColor(-1);btn.setGravity(17);btn.setTypeface(null,1);
  GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));
  bbg.setColor(Color.parseColor("#66FFFFFF"));btn.setBackground(bbg);
  LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(60));
  pr.topMargin=dp(40);pr.leftMargin=dp(10);pr.rightMargin=dp(10);
  btn.setLayoutParams(pr);btn.setPadding(0,dp(15),0,dp(15));
  btn.setClickable(true);btn.setFocusable(true);
  btn.setOnClickListener(v->{Toast.makeText(this,"Get Started OK",0).show();login();});
  m.addView(btn);
  
  sv.addView(m);setContentView(sv);
 }

 LinearLayout makeCard(String title,String desc){
  LinearLayout ca=new LinearLayout(this);ca.setOrientation(0);ca.setGravity(16);
  ca.setPadding(dp(20),dp(20),dp(20),dp(20));
  GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(20));
  g.setColor(Color.parseColor("#55FFFFFF"));ca.setBackground(g);
  LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(20);
  ca.setLayoutParams(lp);ca.setClickable(true);ca.setFocusable(true);
  
  TextView ic=new TextView(this);ic.setText(title.substring(0,1));ic.setTextSize(22);
  ic.setTextColor(-1);ic.setGravity(17);ic.setTypeface(null,1);
  GradientDrawable ig=new GradientDrawable();ig.setCornerRadius(dp(15));
  ig.setColors(new int[]{Color.parseColor("#8E2DE2"),Color.parseColor("#4A00E0")});
  ig.setOrientation(GradientDrawable.Orientation.TL_BR);ic.setBackground(ig);
  ic.setLayoutParams(new LinearLayout.LayoutParams(dp(60),dp(60)));
  ic.setClickable(false);ic.setFocusable(false); // مهم باش ما يبلوكيش الكليك
  ca.addView(ic);
  
  LinearLayout tx=new LinearLayout(this);tx.setOrientation(1);tx.setPadding(dp(16),0,0,0);
  tx.setClickable(false);tx.setFocusable(false);
  TextView t=new TextView(this);t.setText(title);t.setTextSize(18);t.setTextColor(-1);
  t.setTypeface(null,1);t.setClickable(false);tx.addView(t);
  TextView d=new TextView(this);d.setText(desc);d.setTextSize(13);
  d.setTextColor(Color.parseColor("#E0E0E0"));d.setClickable(false);tx.addView(d);
  ca.addView(tx);
  return ca;
 }

 void passport(){
  LinearLayout m=new LinearLayout(this);m.setOrientation(1);
  m.setPadding(dp(24),dp(50),dp(24),dp(24));m.setBackgroundColor(Color.parseColor("#F5F7FF"));
  TextView back=new TextView(this);back.setText("<- Back");back.setTextSize(16);
  back.setTextColor(Color.parseColor("#2D1B69"));back.setTypeface(null,1);
  back.setOnClickListener(v->home());m.addView(back);
  TextView h=new TextView(this);h.setText("\nPassport Services");h.setTextSize(26);
  h.setTextColor(Color.parseColor("#2D1B69"));h.setTypeface(null,1);m.addView(h);
  ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);
 }

 void login(){
  int[] c={Color.parseColor("#2D1B69"),Color.parseColor("#00C9A7")};
  GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
  LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setGravity(17);
  m.setPadding(dp(28),dp(80),dp(28),dp(28));m.setBackground(bg);
  TextView h=new TextView(this);h.setText("Welcome Back");h.setTextSize(32);
  h.setTextColor(-1);h.setTypeface(null,1);h.setGravity(17);m.addView(h);
  TextView btn=new TextView(this);btn.setText("Login Success -> Home");btn.setTextSize(18);
  btn.setTextColor(-1);btn.setGravity(17);btn.setTypeface(null,1);
  GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));
  bbg.setColor(Color.parseColor("#2D1B69"));btn.setBackground(bbg);
  LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(30);
  btn.setLayoutParams(pr);btn.setPadding(0,dp(15),0,dp(15));
  btn.setOnClickListener(v->home());m.addView(btn);
  TextView bk=new TextView(this);bk.setText("<- Back to Home");bk.setTextColor(-1);
  bk.setGravity(17);bk.setPadding(0,dp(20),0,0);bk.setOnClickListener(v->home());m.addView(bk);
  ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);
 }
 int dp(int v){return(int)TypedValue.applyDimension(1,v,getResources().getDisplayMetrics());}
}
