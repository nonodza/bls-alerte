package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.content.SharedPreferences;
public class MainActivity extends Activity{
int curLang=0;
@Override protected void onCreate(Bundle b){
super.onCreate(b);
SharedPreferences sp=getSharedPreferences("bls",0);
curLang=sp.getInt("lang",0);home();}
void home(){
int c1=Color.parseColor("#0F0C29");
int c2=Color.parseColor("#302B63");
int[] cl={c1,c2,Color.parseColor("#24243E")};
GradientDrawable bg=new GradientDrawable(
GradientDrawable.Orientation.TL_BR,cl);
ScrollView sv=new ScrollView(this);
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setPadding(dp(20),dp(35),dp(20),dp(20));
m.setBackground(bg);
LinearLayout top=new LinearLayout(this);
top.setOrientation(0);
TextView lo=new TextView(this);
lo.setText("BLS");lo.setTextSize(20);
lo.setTextColor(-1);lo.setTypeface(null,1);
LinearLayout.LayoutParams lp1=
new LinearLayout.LayoutParams(0,-2,1);
lo.setLayoutParams(lp1);top.addView(lo);
TextView se=new TextView(this);
se.setText("⚙ Settings");
se.setTextColor(Color.parseColor("#9AA0B6"));
se.setOnClickListener(v->settings());
top.addView(se);m.addView(top);
String tV="Visa Services";String tB="Book appointment";
String p1="Passport";String v1="Visa Centers";
String a1="My Appointments";String btnT="Get Started";
if(curLang==1){tV="Services Visa";tB="Reservez RDV";
p1="Passeport";v1="Centres Visa";a1="Mes RDV";btnT="Commencer";}
if(curLang==2){tV="خدمات التأشيرة";tB="احجز موعدك";
p1="جواز السفر";v1="مراكز التأشيرة";a1="مواعيدي";btnT="ابدأ";}
TextView t1=new TextView(this);
t1.setText("\n"+tV);t1.setTextSize(30);
t1.setTextColor(-1);t1.setTypeface(null,1);m.addView(t1);
TextView t2=new TextView(this);
t2.setText(tB+"\n");t2.setTextColor(Color.parseColor("#9AA0B6"));
t2.setPadding(0,dp(8),0,dp(20));m.addView(t2);
m.addView(card(p1,"New, Renew & Track",0));
m.addView(card(v1,"Algiers Oran Constantine",1));
m.addView(card(a1,"View & Manage",2));
m.addView(card("Support & Help","Contact BLS",3));
TextView btn=new TextView(this);btn.setText(btnT);
btn.setTextSize(18);btn.setTextColor(c1);
btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();
bbg.setCornerRadius(dp(30));bbg.setColor(-1);
btn.setBackground(bbg);
LinearLayout.LayoutParams pr=
new LinearLayout.LayoutParams(-1,dp(56));
pr.topMargin=dp(25);btn.setLayoutParams(pr);
btn.setPadding(0,dp(14),0,dp(14));
btn.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));
m.addView(btn);sv.addView(m);setContentView(sv);
}
LinearLayout card(String a,String b,int t){
LinearLayout ca=new LinearLayout(this);
ca.setOrientation(0);ca.setGravity(16);
ca.setPadding(dp(18),dp(16),dp(18),dp(16));
GradientDrawable g=new GradientDrawable();
g.setCornerRadius(dp(18));
g.setColor(Color.parseColor("#22FFFFFF"));
g.setStroke(1,Color.parseColor("#33FFFFFF"));
ca.setBackground(g);
LinearLayout.LayoutParams lp=
new LinearLayout.LayoutParams(-1,-2);
lp.bottomMargin=dp(12);ca.setLayoutParams(lp);
LinearLayout tx=new LinearLayout(this);
tx.setOrientation(1);
TextView tt=new TextView(this);tt.setText(a);
tt.setTextColor(-1);tt.setTextSize(16);
tt.setTypeface(null,1);tx.addView(tt);
TextView dd=new TextView(this);dd.setText(b);
dd.setTextColor(Color.parseColor("#9AA0B6"));
dd.setTextSize(11);tx.addView(dd);ca.addView(tx);
if(t==0)ca.setOnClickListener(v->passport());
if(t==1)ca.setOnClickListener(v->centers());
if(t==2)ca.setOnClickListener(v->appoint());
if(t==3)ca.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));
return ca;
}
void passport(){
LinearLayout m=base();m.addView(title("Passport Services"));
LinearLayout c1=sCardNoClick("New Passport","Apply for new");
c1.setOnClickListener(v->open("https://algeria.blsspainvisa.com/book_appointment.php"));
m.addView(c1);
LinearLayout c2=sCardNoClick("Renew Passport","Renew existing");
c2.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));
m.addView(c2);
LinearLayout c3=sCardNoClick("Track Status","Track application");
c3.setOnClickListener(v->trackPage());m.addView(c3);
LinearLayout c4=sCardNoClick("Requirements","Documents needed");
c4.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));
m.addView(c4);show(m);
}
void trackPage(){
LinearLayout m=base();m.addView(title("Track Status"));
EditText e1=input("Tracking Number (BLS123)");m.addView(e1);
TextView b1=btnDark("Track on BLS Website");
b1.setOnClickListener(v->open("https://algeria.blsspainvisa.com/track_application.php"));
m.addView(b1);
TextView b2=btnDark("Check Here");m.addView(b2);
TextView res=new TextView(this
