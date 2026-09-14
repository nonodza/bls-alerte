package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.view.Gravity;
import android.util.TypedValue;

public class MainActivity extends Activity {
@Override
protected void onCreate(Bundle b){
super.onCreate(b);
home();
}

void home(){
int c1=Color.parseColor("#0F0C29");
int c2=Color.parseColor("#302B63");
int c3=Color.parseColor("#24243E");
int[] colors={c1,c2,c3};
GradientDrawable bg=new GradientDrawable(
GradientDrawable.Orientation.TL_BR,colors);

ScrollView sv=new ScrollView(this);
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setGravity(17);
m.setPadding(dp(24),dp(60),dp(24),dp(30));
m.setBackground(bg);

TextView t1=new TextView(this);
t1.setText("BLS");
t1.setTextSize(90);
t1.setTextColor(-1);
t1.setGravity(17);
t1.setTypeface(null,1);
m.addView(t1);

TextView t2=new TextView(this);
t2.setText("VISA SERVICES");
t2.setTextSize(11);
t2.setTextColor(Color.parseColor("#9AA0B6"));
t2.setGravity(17);
t2.setPadding(0,dp(10),0,dp(40));
m.addView(t2);

m.addView(makeCard("Passport","Apply & Track"));
m.addView(makeCard("Visa Centers","Find BLS Near You"));

TextView btn=new TextView(this);
btn.setText("Get Started");
btn.setTextSize(18);
btn.setTextColor(c1);
btn.setGravity(17);
btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();
bbg.setCornerRadius(dp(30));
bbg.setColor(-1);
btn.setBackground(bbg);
LinearLayout.LayoutParams pr=
new LinearLayout.LayoutParams(-1,dp(56));
pr.topMargin=dp(40);
pr.leftMargin=dp(20);
pr.rightMargin=dp(20);
btn.setLayoutParams(pr);
btn.setPadding(0,dp(14),0,dp(14));
btn.setOnClickListener(v->login());
m.addView(btn);

sv.addView(m);
setContentView(sv);
}

LinearLayout makeCard(String a,String b){
LinearLayout ca=new LinearLayout(this);
ca.setOrientation(0);
ca.setGravity(16);
ca.setPadding(dp(20),dp(20),dp(20),dp(20));
GradientDrawable g=new GradientDrawable();
g.setCornerRadius(dp(20));
g.setColor(Color.parseColor("#1AFFFFFF"));
g.setStroke(1,Color.parseColor("#33FFFFFF"));
ca.setBackground(g);
LinearLayout.LayoutParams lp=
new LinearLayout.LayoutParams(-1,-2);
lp.bottomMargin=dp(16);
ca.setLayoutParams(lp);

TextView tx=new TextView(this);
tx.setText(a+"\n"+b);
tx.setTextColor(-1);
tx.setTextSize(16);
ca.addView(tx);
ca.setOnClickListener(v->passport());
return ca;
}

void passport(){
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setPadding(dp(24),dp(50),dp(24),dp(24));
m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView bk=new TextView(this);
bk.setText("<- Back");
bk.setTextSize(16);
bk.setTextColor(Color.parseColor("#0F0C29"));
bk.setTypeface(null,1);
bk.setOnClickListener(v->home());
m.addView(bk);
TextView h=new TextView(this);
h.setText("\nPassport Services");
h.setTextSize(26);
h.setTextColor(Color.parseColor("#0F0C29"));
h.setTypeface(null,1);
m.addView(h);
m.addView(makeCard("New Passport","Apply now"));
m.addView(makeCard("Track Status","Check status"));
ScrollView sv=new ScrollView(this);
sv.addView(m);
setContentView(sv);
}

void login(){
int c1=Color.parseColor("#0F0C29");
int c2=Color.parseColor("#302B63");
int[] colors={c1,c2};
GradientDrawable bg=new GradientDrawable(
GradientDrawable.Orientation.TL_BR,colors);
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setGravity(17);
m.setPadding(dp(28),dp(80),dp(28),dp(28));
m.setBackground(bg);
TextView h=new TextView(this);
h.setText("Welcome Back");
h.setTextSize(32);
h.setTextColor(-1);
h.setTypeface(null,1);
h.setGravity(17);
m.addView(h);
TextView btn=new TextView(this);
btn.setText("Login -> Home");
btn.setTextSize(16);
btn.setTextColor(c1);
btn.setGravity(17);
btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();
bbg.setCornerRadius(dp(30));
bbg.setColor(-1);
btn.setBackground(bbg);
LinearLayout.LayoutParams pr=
new LinearLayout.LayoutParams(-1,dp(56));
pr.topMargin=dp(40);
btn.setLayoutParams(pr);
btn.setPadding(0,dp(14),0,dp(14));
btn.setOnClickListener(v->home());
m.addView(btn);
ScrollView sv=new ScrollView(this);
sv.addView(m);
setContentView(sv);
}

int dp(int v){
return(int)TypedValue.applyDimension(
1,v,getResources().getDisplayMetrics());
}
}
