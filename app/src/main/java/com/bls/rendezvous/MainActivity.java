package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.view.Gravity;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
public class MainActivity extends Activity{
@Override protected void onCreate(Bundle b){super.onCreate(b);home();}
void home(){
int[] c={Color.parseColor("#0F0C29"),Color.parseColor("#302B63"),Color.parseColor("#00B4DB")};
GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,c);
ScrollView sv=new ScrollView(this);
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);m.setGravity(17);m.setPadding(dp(28),dp(70),dp(28),dp(30));m.setBackground(bg);
TextView logo=new TextView(this);logo.setText("BLS");logo.setTextSize(92);logo.setTextColor(-1);logo.setGravity(17);logo.setTypeface(null,1);m.addView(logo);
TextView t=new TextView(this);t.setText("INTERNATIONAL\nVisa & Passport Services");t.setTextSize(13);t.setTextColor(Color.parseColor("#B0BEC5"));t.setGravity(17);t.setPadding(0,dp(8),0,dp(40));m.addView(t);
m.addView(card("PASSPORT SERVICES","Apply • Renew • Track","P","#FF416C","#FF4B2B",()->passport()));
m.addView(card("VISA CENTERS","Official BLS Centers","V","#00C9FF","#92FE9D",()->{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.google.com/?q=BLS+Spain+Algeria")));}));
TextView btn=new TextView(this);btn.setText("GET STARTED →");btn.setTextSize(16);btn.setTextColor(Color.parseColor("#0F0C29"));btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);btn.setElevation(dp(8));
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(62));pr.topMargin=dp(35);btn.setLayoutParams(pr);btn.setPadding(0,dp(18),0,dp(18));
btn.setOnClickListener(v->login());m.addView(btn);
TextView foot=new TextView(this);foot.setText("Trusted by 2M+ travelers");foot.setTextSize(11);foot.setTextColor(Color.parseColor("#80FFFFFF"));foot.setGravity(17);foot.setPadding(0,dp(25),0,0);m.addView(foot);
sv.addView(m);setContentView(sv);
}
void passport(){
LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setPadding(dp(24),dp(50),dp(24),dp(24));m.setBackgroundColor(Color.parseColor("#F8F9FF"));
TextView back=new TextView(this);back.setText("← BACK");back.setTypeface(null,1);back.setOnClickListener(v->home());m.addView(back);
TextView h=new TextView(this);h.setText("\nPassport Services");h.setTextSize(28);h.setTypeface(null,1);m.addView(h);
m.addView(sCard("New Passport","Start fresh application"));m.addView(sCard("Renew","Quick renewal 72h"));m.addView(sCard("Track","Real-time status"));
ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);
}
void login(){
int[] c={Color.parseColor("#0F0C29"),Color.parseColor("#00B4DB")};GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setGravity(17);m.setPadding(dp(28),dp(90),dp(28),dp(28));m.setBackground(bg);
TextView h=new TextView(this);h.setText("Welcome Back");h.setTextSize(34);h.setTypeface(null,1);h.setTextColor(-1);m.addView(h);
EditText e=inp("Email");m.addView(e);EditText p=inp("Password");m.addView(p);
TextView btn=new TextView(this);btn.setText("CONTINUE");btn.setTextSize(15);btn.setTextColor(Color.parseColor("#0F0C29"));btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(30);btn.setLayoutParams(pr);btn.setPadding(0,dp(16),0,dp(16));
btn.setOnClickListener(v->{Toast.makeText(this,"Authenticated ✓",0).show();home();});m.addView(btn);
TextView bk=new TextView(this);bk.setText("← Back");bk.setTextColor(Color.parseColor("#80FFFFFF"));bk.setGravity(17);bk.setPadding(0,dp(20),0,0);bk.setOnClickListener(v->home());m.addView(bk);
ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);
}
LinearLayout card(String a,String b,String l,String c1,String c2,Runnable r){
LinearLayout ca=new LinearLayout(this);ca.setOrientation(0);ca.setGravity(16);ca.setPadding(dp(20),dp(22),dp(20),dp(22));
GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(22));g.setColor(Color.parseColor("#1AFFFFFF"));g.setStroke(1,Color.parseColor("#33FFFFFF"));ca.setBackground(g);
LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(18);ca.setLayoutParams(lp);
TextView ic=new TextView(this);ic.setText(l);ic.setTextSize(20);ic.setTextColor(-1);ic.setGravity(17);ic.setTypeface(null,1);
GradientDrawable ig=new GradientDrawable();ig.setCornerRadius(dp(16));ig.setColors(new int[]{Color.parseColor(c1),Color.parseColor(c2)});ig.setOrientation(GradientDrawable.Orientation.TL_BR);ic.setBackground(ig);ic.setLayoutParams(new LinearLayout.LayoutParams(dp(62),dp(62)));ca.addView(ic);
LinearLayout tx=new LinearLayout(this);tx.setOrientation(1);tx.setPadding(dp(18),0,0,0);
TextView t1=new TextView(this);t1.setText(a);t1.setTextSize(13);t1.setTextColor(-1);t1.setTypeface(null,1);tx.addView(t1);
TextView t2=new TextView(this);t2.setText(b);t2.setTextSize(11);t2.setTextColor(Color.parseColor("#B0BEC5"));tx.addView(t2);
ca.addView(tx);ca.setOnClickListener(v->r.run());return ca;
}
LinearLayout sCard(String a,String b){LinearLayout c=new LinearLayout(this);c.setOrientation(1);c.setPadding(dp(20),dp(18),dp(20),dp(18));GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(16));g.setColor(-1);c.setBackground(g);c.setElevation(dp(4));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=dp(14);c.setLayoutParams(p);TextView t1=new TextView(this);t1.setText(a);t1.setTypeface(null,1);c.addView(t1);TextView t2=new TextView(this);t2.setText(b);t2.setTextSize(12);t2.setTextColor(Color.GRAY);c.addView(t2);c.setOnClickListener(v->Toast.makeText(this,a+" Coming Soon!",0).show());return c;}
EditText inp(String h){EditText e=new EditText(this);e.setHint(h);e.setTextColor(-1);e.setHintTextColor(Color.parseColor("#80FFFFFF"));e.setBackgroundColor(Color.parseColor("#22FFFFFF"));e.setPadding(dp(20),dp(14),dp(20),dp(14));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,dp(58));p.topMargin=dp(16);e.setLayoutParams(p);return e;}
int dp(int v){return(int)TypedValue.applyDimension(1,v,getResources().getDisplayMetrics());}
}
