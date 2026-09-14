package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
public class MainActivity extends Activity{
@Override protected void onCreate(Bundle b){super.onCreate(b);home();}
void home(){
int c1=Color.parseColor("#0F0C29");
int c2=Color.parseColor("#302B63");
int[] cl={c1,c2,Color.parseColor("#24243E")};
GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,cl);
ScrollView sv=new ScrollView(this);
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);m.setPadding(dp(20),dp(35),dp(20),dp(20));m.setBackground(bg);
LinearLayout top=new LinearLayout(this);top.setOrientation(0);
TextView lo=new TextView(this);lo.setText("BLS");lo.setTextSize(20);lo.setTextColor(-1);lo.setTypeface(null,1);
LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(0,-2,1);lo.setLayoutParams(lp1);top.addView(lo);
TextView se=new TextView(this);se.setText("⚙ Settings");se.setTextColor(Color.parseColor("#9AA0B6"));se.setOnClickListener(v->settings());top.addView(se);m.addView(top);
TextView t1=new TextView(this);t1.setText("\nVisa Services");t1.setTextSize(30);t1.setTextColor(-1);t1.setTypeface(null,1);m.addView(t1);
TextView t2=new TextView(this);t2.setText("Book your appointment\n");t2.setTextColor(Color.parseColor("#9AA0B6"));t2.setPadding(0,dp(8),0,dp(20));m.addView(t2);
m.addView(card("Passport","New, Renew & Track",0));
m.addView(card("Visa Centers","Algiers Oran Constantine",1));
m.addView(card("My Appointments","View & Manage",2));
m.addView(card("Support & Help","Contact BLS",3));
TextView btn=new TextView(this);btn.setText("Get Started");btn.setTextSize(18);btn.setTextColor(c1);btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(25);btn.setLayoutParams(pr);btn.setPadding(0,dp(14),0,dp(14));btn.setOnClickListener(v->login());m.addView(btn);
sv.addView(m);setContentView(sv);
}
LinearLayout card(String a,String b,int t){
LinearLayout ca=new LinearLayout(this);ca.setOrientation(0);ca.setGravity(16);ca.setPadding(dp(18),dp(16),dp(18),dp(16));
GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(18));g.setColor(Color.parseColor("#22FFFFFF"));g.setStroke(1,Color.parseColor("#33FFFFFF"));ca.setBackground(g);
LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(12);ca.setLayoutParams(lp);
LinearLayout tx=new LinearLayout(this);tx.setOrientation(1);
TextView tt=new TextView(this);tt.setText(a);tt.setTextColor(-1);tt.setTextSize(16);tt.setTypeface(null,1);tx.addView(tt);
TextView dd=new TextView(this);dd.setText(b);dd.setTextColor(Color.parseColor("#9AA0B6"));dd.setTextSize(11);tx.addView(dd);ca.addView(tx);
if(t==0)ca.setOnClickListener(v->passport());
if(t==1)ca.setOnClickListener(v->centers());
if(t==2)ca.setOnClickListener(v->appoint());
if(t==3)ca.setOnClickListener(v->{
Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://algeria.blsspainvisa.com"));startActivity(i);});
return ca;
}
void passport(){LinearLayout m=base();m.addView(title("Passport Services"));
m.addView(sCard("New Passport","Apply for new"));m.addView(sCard("Renew Passport","Renew existing"));
m.addView(sCard("Track Status","Track application"));m.addView(sCard("Requirements","Documents needed"));
show(m);}
void centers(){LinearLayout m=base();m.addView(title("BLS Centers Algeria"));
m.addView(cCard("BLS Algiers","El Biar - 023 12 34 56"));m.addView(cCard("BLS Oran","Bir El Djir - 041"));
m.addView(cCard("BLS Constantine","Zouaghi - 031"));TextView mp=new TextView(this);mp.setText("Open in Google Maps");
mp.setGravity(17);mp.setTextColor(-1);GradientDrawable gg=new GradientDrawable();gg.setCornerRadius(dp(30));gg.setColor(Color.parseColor("#0F0C29"));mp.setBackground(gg);
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(50));pr.topMargin=dp(20);mp.setLayoutParams(pr);mp.setPadding(0,dp(14),0,dp(14));
mp.setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/search/BLS+Spain+Algeria"));startActivity(i);});m.addView(mp);show(m);}
void appoint(){LinearLayout m=base();m.addView(title("My Appointments"));m.addView(sCard("Upcoming","No appointments"));
m.addView(sCard("History","View past bookings"));m.addView(sCard("Book New","Book appointment"));show(m);}
void settings(){LinearLayout m=base();m.addView(title("Settings"));m.addView(sCard("Language","العربية / Français / English"));
m.addView(sCard("Notifications","Manage alerts"));m.addView(sCard("Dark Mode","On / Off"));m.addView(sCard("Privacy Policy","View policy"));
m.addView(sCard("About BLS","Version 1.0.0"));m.addView(sCard("Logout","Sign out"));show(m);}
LinearLayout base(){LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setPadding(dp(20),dp(35),dp(20),dp(20));m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView bk=new TextView(this);bk.setText("<- Back");bk.setTypeface(null,1);bk.setOnClickListener(v->home());m.addView(bk);return m;}
TextView title(String s){TextView h=new TextView(this);h.setText("\n"+s+"\n");h.setTextSize(22);h.setTypeface(null,1);return h;}
LinearLayout sCard(String a,String b){LinearLayout ca=new LinearLayout(this);ca.setOrientation(1);ca.setPadding(dp(14),dp(12),dp(14),dp(12));
GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(12));g.setColor(Color.WHITE);g.setStroke(1,Color.parseColor("#E5E5E5"));ca.setBackground(g);
LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(10);ca.setLayoutParams(lp);
TextView t1=new TextView(this);t1.setText(a);t1.setTypeface(null,1);ca.addView(t1);TextView t2=new TextView(this);t2.setText(b);t2.setTextSize(11);t2.setTextColor(Color.GRAY);ca.addView(t2);
ca.setOnClickListener(v->Toast.makeText(this,a,Toast.LENGTH_SHORT).show());return ca;}
LinearLayout cCard(String a,String b){LinearLayout ca=sCard(a,b);ca.setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/search/"+a));startActivity(i);});return ca;}
void show(LinearLayout m){ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);}
void login(){int c1=Color.parseColor("#0F0C29");int c2=Color.parseColor("#302B63");int[] cl={c1,c2};
GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,cl);LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setGravity(17);m.setPadding(dp(28),dp(80),dp(28),dp(28));m.setBackground(bg);
TextView h=new TextView(this);h.setText("Welcome Back");h.setTextSize(32);h.setTextColor(-1);h.setTypeface(null,1);h.setGravity(17);m.addView(h);
TextView btn=new TextView(this);btn.setText("Login -> Home");btn.setTextSize(16);btn.setTextColor(c1);btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(40);btn.setLayoutParams(pr);btn.setPadding(0,dp(14),0,dp(14));btn.setOnClickListener(v->home());m.addView(btn);
TextView b2=new TextView(this);b2.setText("<- Back");b2.setTextColor(Color.parseColor("#9AA0B6"));b2.setGravity(17);b2.setPadding(0,dp(20),0,0);b2.setOnClickListener(v->home());m.addView(b2);
ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);}
int dp(int v){return(int)TypedValue.applyDimension(1,v,getResources().getDisplayMetrics());}
}
