package com.bls.rendezvous;
import android.app.Activity;import android.os.Bundle;import android.graphics.Color;import android.graphics.drawable.GradientDrawable;import android.widget.*;import android.content.Intent;import android.net.Uri;import android.util.TypedValue;
public class MainActivity extends Activity{
@Override protected void onCreate(Bundle b){super.onCreate(b);home();}
void home(){
int[] c={Color.parseColor("#0F0C29"),Color.parseColor("#302B63"),Color.parseColor("#24243E")};GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,c);
ScrollView sv=new ScrollView(this);LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setGravity(17);m.setPadding(dp(24),dp(70),dp(24),dp(40));m.setBackground(bg);
TextView t1=new TextView(this);t1.setText("BLS");t1.setTextSize(95);t1.setTextColor(-1);t1.setGravity(17);t1.setTypeface(null,1);m.addView(t1);
TextView t2=new TextView(this);t2.setText("VISA APPLICATION");t2.setTextSize(12);t2.setTextColor(Color.parseColor("#9AA0B6"));t2.setGravity(17);t2.setPadding(0,dp(10),0,dp(50));m.addView(t2);
m.addView(card("Passport Services","Apply Renew Track","#FF512F","#DD2476",1));
m.addView(card("Visa Centers","Find Nearest BLS Center","#11998E","#38EF7D",2));
TextView btn=new TextView(this);btn.setText("Get Started");btn.setTextSize(18);btn.setTextColor(Color.parseColor("#0F0C29"));btn.setGravity(17);btn.setTypeface(null,1);GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(58));pr.topMargin=dp(50);pr.leftMargin=dp(20);pr.rightMargin=dp(20);btn.setLayoutParams(pr);btn.setPadding(0,dp(14),0,dp(14));btn.setOnClickListener(v->login());m.addView(btn);
sv.addView(m);setContentView(sv);
}
LinearLayout card(String a,String b,String c1,String c2,int t){
LinearLayout ca=new LinearLayout(this);ca.setOrientation(0);ca.setGravity(16);ca.setPadding(dp(20),dp(20),dp(20),dp(20));GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(24));g.setColor(Color.parseColor("#1AFFFFFF"));g.setStroke(1,Color.parseColor("#33FFFFFF"));ca.setBackground(g);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(18);ca.setLayoutParams(lp);ca.setClickable(true);
TextView ic=new TextView(this);ic.setText(a.substring(0,1));ic.setTextSize(20);ic.setTextColor(-1);ic.setGravity(17);ic.setTypeface(null,1);GradientDrawable ig=new GradientDrawable();ig.setCornerRadius(dp(16));ig.setColors(new int[]{Color.parseColor(c1),Color.parseColor(c2)});ig.setOrientation(GradientDrawable.Orientation.TL_BR);ic.setBackground(ig);ic.setLayoutParams(new LinearLayout.LayoutParams(dp(56),dp(56)));ca.addView(ic);
LinearLayout tx=new LinearLayout(this);tx.setOrientation(1);tx.setPadding(dp(16),0,0,0);TextView tt=new TextView(this);tt.setText(a);tt.setTextSize(17);tt.setTextColor(-1);tt.setTypeface(null,1);tx.addView(tt);TextView d=new TextView(this);d.setText(b);d.setTextSize(12);d.setTextColor(Color.parseColor("#9AA0B6"));tx.addView(d);ca.addView(tx);
if(t==1)ca.setOnClickListener(v->passport());else ca.setOnClickListener(v->startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.google.com/?q=BLS+Spain+Algeria"))));
return ca;
}
void passport(){LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setPadding(dp(24),dp(50),dp(24),dp(24));m.setBackgroundColor(Color.parseColor("#0F0C29"));TextView b=new TextView(this);b.setText("<- Back");b.setTextColor(-1);b.setOnClickListener(v->home());m.addView(b);TextView h=new TextView(this);h.setText("\nPassport Services");h.setTextSize(26);h.setTextColor(-1);h.setTypeface(null,1);m.addView(h);ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);}
void login(){int[] c={Color.parseColor("#0F0C29"),Color.parseColor("#302B63")};GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,c);LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setGravity(17);m.setPadding(dp(28),dp(100),dp(28),dp(28));m.setBackground(bg);TextView h=new TextView(this);h.setText("Welcome Back");h.setTextSize(32);h.setTextColor(-1);h.setTypeface(null,1);h.setGravity(17);m.addView(h);TextView btn=new TextView(this);btn.setText("Login -> Home");btn.setTextSize(16);btn.setTextColor(Color.parseColor("#0F0C29"));btn.setGravity(17);btn.setTypeface(null,1);GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(40);btn.setLayoutParams(pr);btn.setPadding(0,dp(14),0,dp(14));btn.setOnClickListener(v->home());m.addView(btn);ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);}
int dp(int v){return(int)TypedValue.applyDimension(1,v,getResources().getDisplayMetrics());}
}
