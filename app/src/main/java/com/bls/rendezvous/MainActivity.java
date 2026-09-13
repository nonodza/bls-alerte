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

public class MainActivity extends Activity{
@Override protected void onCreate(Bundle b){super.onCreate(b);home();}
void home(){
int[] c={Color.parseColor("#2D1B69"),Color.parseColor("#3B2A8A"),Color.parseColor("#2E86AB"),Color.parseColor("#00C9A7")};
GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
ScrollView sv=new ScrollView(this);
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);m.setGravity(17);m.setPadding(dp(24),dp(60),dp(24),dp(40));m.setBackground(bg);
TextView t1=new TextView(this);t1.setText("BLS");t1.setTextSize(90);t1.setTextColor(-1);t1.setGravity(17);t1.setTypeface(null,1);m.addView(t1);
TextView t2=new TextView(this);t2.setText("Visa Application Services\nSecure • Fast • Official");t2.setTextSize(16);t2.setTextColor(-1);t2.setGravity(17);t2.setPadding(0,0,0,dp(40));m.addView(t2);
LinearLayout c1=card("Passport Services","Apply, Renew & Track Passport","P","#FF6B6B","#9B59B6");
c1.setClickable(true);c1.setOnClickListener(v->{Toast.makeText(this,"Passport ✓ Click works!",0).show();passport();});
m.addView(c1);
LinearLayout c2=card("Visa Centers","Find Nearest BLS Center\n& Directions","V","#3498DB","#1ABC9C");
c2.setClickable(true);c2.setOnClickListener(v->{Toast.makeText(this,"Opening Maps...",0).show();startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.google.com/?q=BLS+Spain+Algeria")));});
m.addView(c2);
TextView btn=new TextView(this);btn.setText("Get Started");btn.setTextSize(20);btn.setTextColor(-1);btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(Color.parseColor("#66FFFFFF"));bbg.setStroke(1,Color.parseColor("#88FFFFFF"));btn.setBackground(bbg);
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(60));pr.topMargin=dp(40);pr.leftMargin=dp(20);pr.rightMargin=dp(20);btn.setLayoutParams(pr);btn.setPadding(0,dp(15),0,dp(15));
btn.setClickable(true);btn.setOnClickListener(v->{Toast.makeText(this,"Get Started ✓",0).show();login();});m.addView(btn);
sv.addView(m);setContentView(sv);
}
void passport(){
LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setPadding(dp(24),dp(60),dp(24),dp(24));m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView b=new TextView(this);b.setText("← Back");b.setTextSize(16);b.setTextColor(Color.parseColor("#2D1B69"));b.setOnClickListener(v->home());m.addView(b);
TextView h=new TextView(this);h.setText("\nPassport Services\nComing Soon Pro!");h.setTextSize(22);h.setTypeface(null,1);m.addView(h);
ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);
}
void login(){
int[] c={Color.parseColor("#2D1B69"),Color.parseColor("#00C9A7")};GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,c);
LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setGravity(17);m.setPadding(dp(28),dp(80),dp(28),dp(28));m.setBackground(bg);
TextView h=new TextView(this);h.setText("Welcome Back");h.setTextSize(30);h.setTypeface(null,1);h.setTextColor(-1);h.setGravity(17);m.addView(h);
TextView btn=new TextView(this);btn.setText("Login Success →");btn.setTextColor(Color.parseColor("#0F0C29"));btn.setGravity(17);btn.setTypeface(null,1);GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(40);btn.setLayoutParams(pr);btn.setPadding(0,dp(15),0,dp(15));btn.setOnClickListener(v->{Toast.makeText(this,"Login ✓",0).show();home();});m.addView(btn);
TextView bk=new TextView(this);bk.setText("← Back to Home");bk.setTextColor(-1);bk.setGravity(17);bk.setPadding(0,dp(20),0,0);bk.setOnClickListener(v->home());m.addView(bk);
ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);
}
LinearLayout card(String a,String b,String l,String c1,String c2){
LinearLayout ca=new LinearLayout(this);ca.setOrientation(0);ca.setGravity(16);ca.setPadding(dp(20),dp(20),dp(20),dp(20));
GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(20));g.setColor(Color.parseColor("#55FFFFFF"));g.setStroke(1,Color.parseColor("#77FFFFFF"));ca.setBackground(g);
LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(20);ca.setLayoutParams(lp);
TextView ic=new TextView(this);ic.setText(l);ic.setTextSize(22);ic.setTextColor(-1);ic.setGravity(17);ic.setTypeface(null,1);
GradientDrawable ig=new GradientDrawable();ig.setCornerRadius(dp(15));ig.setColors(new int[]{Color.parseColor(c1),Color.parseColor(c2)});ig.setOrientation(GradientDrawable.Orientation.TL_BR);ic.setBackground(ig);ic.setLayoutParams(new LinearLayout.LayoutParams(dp(60),dp(60)));ca.addView(ic);
LinearLayout tx=new LinearLayout(this);tx.setOrientation(1);tx.setPadding(dp(16),0,0,0);
TextView t=new TextView(this);t.setText(a);t.setTextSize(18);t.setTextColor(-1);t.setTypeface(null,1);tx.addView(t);
TextView d=new TextView(this);d.setText(b);d.setTextSize(13);d.setTextColor(Color.parseColor("#E0E0E0"));tx.addView(d);
ca.addView(tx);return ca;
}
int dp(int v){return(int)TypedValue.applyDimension(1,v,getResources().getDisplayMetrics());}
}
