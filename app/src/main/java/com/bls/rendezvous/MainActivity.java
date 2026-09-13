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
  showHome();
 }
 
 void showHome(){
  int[] c = {Color.parseColor("#2D1B69"),Color.parseColor("#2E86AB"),Color.parseColor("#00C9A7")};
  GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
  ScrollView sv = new ScrollView(this);
  LinearLayout m = new LinearLayout(this);
  m.setOrientation(1);
  m.setGravity(17);
  m.setPadding(dp(24),dp(60),dp(24),dp(40));
  m.setBackground(bg);
  
  TextView t1 = new TextView(this);
  t1.setText("BLS");
  t1.setTextSize(90);
  t1.setTextColor(-1);
  t1.setGravity(17);
  t1.setTypeface(null,1);
  m.addView(t1);
  
  TextView t2 = new TextView(this);
  t2.setText("Visa Application Services\nSecure • Fast • Official");
  t2.setTextSize(16);
  t2.setTextColor(-1);
  t2.setGravity(17);
  t2.setPadding(0,0,0,dp(30));
  m.addView(t2);
  
  LinearLayout c1 = card("Passport Services","Apply, Renew & Track","P","#FF6B6B","#9B59B6");
  c1.setOnClickListener(v -> showPassport());
  m.addView(c1);
  
  LinearLayout c2 = card("Visa Centers","Find Nearest BLS Center","V","#3498DB","#1ABC9C");
  c2.setOnClickListener(v -> {
   Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/BLS+Spain+Visa+Center+Algeria"));
   startActivity(i);
  });
  m.addView(c2);
  
  TextView btn = new TextView(this);
  btn.setText("Get Started");
  btn.setTextSize(20);
  btn.setTextColor(-1);
  btn.setGravity(17);
  btn.setTypeface(null,1);
  GradientDrawable bbg = new GradientDrawable();
  bbg.setCornerRadius(dp(30));
  bbg.setColor(Color.parseColor("#66FFFFFF"));
  btn.setBackground(bbg);
  LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1,dp(60));
  p.topMargin=dp(40);
  btn.setLayoutParams(p);
  btn.setPadding(0,dp(15),0,dp(15));
  btn.setOnClickListener(v -> showLogin());
  m.addView(btn);
  
  sv.addView(m);
  setContentView(sv);
 }
 
 void showPassport(){
  ScrollView sv = new ScrollView(this);
  LinearLayout m = new LinearLayout(this);
  m.setOrientation(1);
  m.setPadding(dp(24),dp(50),dp(24),dp(24));
  m.setBackgroundColor(Color.parseColor("#F5F7FF"));
  
  TextView back = new TextView(this);
  back.setText("← Back");
  back.setTextSize(16);
  back.setTextColor(Color.parseColor("#2D1B69"));
  back.setPadding(0,0,0,dp(20));
  back.setOnClickListener(v -> showHome());
  m.addView(back);
  
  TextView title = new TextView(this);
  title.setText("Passport Services");
  title.setTextSize(26);
  title.setTextColor(Color.parseColor("#2D1B69"));
  title.setTypeface(null,1);
  m.addView(title);
  
  m.addView(serviceCard("New Passport","Apply for new passport","🛂"));
  m.addView(serviceCard("Renew Passport","Renew expired passport","🔄"));
  m.addView(serviceCard("Track Status","Track your application","📍"));
  
  sv.addView(m);
  setContentView(sv);
 }
 
 void showLogin(){
  ScrollView sv = new ScrollView(this);
  LinearLayout m = new LinearLayout(this);
  m.setOrientation(1);
  m.setGravity(17);
  m.setPadding(dp(24),dp(80),dp(24),dp(24));
  int[] c = {Color.parseColor("#2D1B69"),Color.parseColor("#00C9A7")};
  GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
  m.setBackground(bg);
  
  TextView t = new TextView(this);
  t.setText("Welcome Back");
  t.setTextSize(32);
  t.setTextColor(-1);
  t.setTypeface(null,1);
  t.setGravity(17);
  m.addView(t);
  
  EditText email = new EditText(this);
  email.setHint("Email");
  email.setBackgroundColor(Color.parseColor("#FFFFFF"));
  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,dp(55));
  lp.topMargin=dp(30);
  email.setLayoutParams(lp);
  m.addView(email);
  
  EditText pass = new EditText(this);
  pass.setHint("Password");
  pass.setBackgroundColor(Color.parseColor("#FFFFFF"));
  LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-1,dp(55));
  lp2.topMargin=dp(15);
  pass.setLayoutParams(lp2);
  m.addView(pass);
  
  TextView btn = new TextView(this);
  btn.setText("Login");
  btn.setTextSize(18);
  btn.setTextColor(-1);
  btn.setGravity(17);
  btn.setTypeface(null,1);
