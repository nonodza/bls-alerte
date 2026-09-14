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
m.setPadding(dp(20),dp(40),dp(20),dp(20));
m.setBackground(bg);

// Top bar
LinearLayout top=new LinearLayout(this);
top.setOrientation(0);
TextView logo=new TextView(this);
logo.setText("BLS");
logo.setTextSize(22);
logo.setTextColor(-1);
logo.setTypeface(null,1);
LinearLayout.LayoutParams lp1=
new LinearLayout.LayoutParams(0,-2,1);
logo.setLayoutParams(lp1);
top.addView(logo);
TextView set=new TextView(this);
set.setText("⚙ Settings");
set.setTextColor(Color.parseColor("#9AA0B6"));
set.setTextSize(14);
set.setOnClickListener(v->settings());
top.addView(set);
m.addView(top);

TextView t1=new TextView(this);
t1.setText("\nVisa Services");
t1.setTextSize(32);
t1.setTextColor(-1);
t1.setTypeface(null,1);
m.addView(t1);

TextView t2=new TextView(this);
t2.setText("Book your appointment\n");
t2.setTextColor(Color.parseColor("#9AA0B6"));
t2.setTextSize(14);
t2.setPadding(0,dp(8),0,dp(20));
m.addView(t2);

m.addView(mainCard("Passport Services",
"New, Renew & Track status",0));
m.addView(mainCard("Visa Centers",
"Algiers, Oran, Constantine",1));
m.addView(mainCard("My Appointments",
"View & Manage bookings",2));
m.addView(mainCard("Support & Help",
"Contact BLS Support",3));

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
pr.topMargin=dp(30);
pr.leftMargin=dp(10);
pr.rightMargin=dp(10);
btn.setLayoutParams(pr);
btn.setPadding(0,dp(14),0,dp(14));
btn.setOnClickListener(v->login());
m.addView(btn);

sv.addView(m);
setContentView(sv);
}

LinearLayout mainCard(String a,String b,int type){
LinearLayout ca=new LinearLayout(this);
ca.setOrientation(0);
ca.setGravity(16);
ca.setPadding(dp(20),dp(18),dp(20),dp(18));
GradientDrawable g=new GradientDrawable();
g.setCornerRadius(dp(20));
g.setColor(Color.parseColor("#1EFFFFFF"));
g.setStroke(1,Color.parseColor("#33FFFFFF"));
ca.setBackground(g);
LinearLayout.LayoutParams lp=
new LinearLayout.LayoutParams(-1,-2);
lp.bottomMargin=dp(14);
ca.setLayoutParams(lp);

LinearLayout tx=new LinearLayout(this);
tx.setOrientation(1);
TextView tt=new TextView(this);
tt.setText(a);
tt.setTextColor(-1);
tt.setTextSize(17);
tt.setTypeface(null,1);
tx.addView(tt);
TextView dd=new TextView(this);
dd.setText(b);
dd.setTextColor(Color.parseColor("#9AA0B6"));
dd.setTextSize(12);
tx.addView(dd);
ca.addView(tx);

if(type==0) ca.setOnClickListener(v->passport());
if(type==1) ca.setOnClickListener(v->centers());
if(type==2) ca.setOnClickListener(v->appointments());
if(type==3) ca.setOnClickListener(v->support());

return ca;
}

void passport(){
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setPadding(dp(20),dp(40),dp(20),dp(20));
m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView bk=new TextView(this);
bk.setText("<- Back");
bk.setTypeface(null,1);
bk.setTextColor(Color.parseColor("#0F0C29"));
bk.setOnClickListener(v->home());
m.addView(bk);
TextView h=new TextView(this);
h.setText("\nPassport Services");
h.setTextSize(24);
h.setTypeface(null,1);
h.setTextColor(Color.parseColor("#0F0C29"));
m.addView(h);
m.addView(setCard("New Passport","Apply for new"));
m.addView(setCard("Renew Passport","Renew existing"));
m.addView(setCard("Track Status","Track application"));
m.addView(setCard("Requirements","Documents needed"));
ScrollView sv=new ScrollView(this);
sv.addView(m);
setContentView(sv);
}

void centers(){
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setPadding(dp(20),dp(40),dp(20),dp(20));
m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView bk=new TextView(this);
bk.setText("<- Back");
bk.setTypeface(null,1);
bk.setOnClickListener(v->home());
m.addView(bk);
TextView h=new TextView(this);
h.setText("\nBLS Centers Algeria");
h.setTextSize(24);
h.setTypeface(null,1);
m.addView(h);
m.addView(centerCard("BLS Algiers",
"El Biar - 023 12 34 56"));
m.addView(centerCard("BLS Oran",
"Bir El Djir - 041 12 34 56"));
m.addView(centerCard("BLS Constantine",
"Zouaghi - 031 12 34 56"));
TextView mapBtn=new TextView(this);
mapBtn.setText("Open in Google Maps");
mapBtn.setGravity(17);
mapBtn.setTextColor(-1);
GradientDrawable gg=new GradientDrawable();
gg.setCornerRadius(dp(30));
gg.setColor(Color.parseColor("#0F0C29"));
mapBtn.setBackground(gg);
LinearLayout.LayoutParams pr=
new LinearLayout.LayoutParams(-1,dp(50));
pr.topMargin=dp(20);
mapBtn.setLayoutParams(pr);
mapBtn.setPadding(0,dp(14),0,dp(14));
mapBtn.setOnClickListener(v->{
Intent i=new Intent(Intent.ACTION_VIEW,
Uri.parse("https://www.google.com/maps/search/BLS+Spain+Algeria"));
startActivity(i);
});
m.addView(mapBtn);
ScrollView sv=new ScrollView(this);
sv.addView(m);
setContentView(sv);
}

LinearLayout centerCard(String a,String b){
LinearLayout ca=new LinearLayout(this);
ca.setOrientation(1);
ca.setPadding(dp(16),dp(16),dp(16),dp(16));
GradientDrawable g=new GradientDrawable();
g.setCornerRadius(dp(16));
g.setColor(Color.WHITE);
g.setStroke(1,Color.parseColor("#E0E0E0"));
ca.setBackground(g);
LinearLayout.LayoutParams lp=
new LinearLayout.LayoutParams(-1,-2);
lp.bottomMargin=dp(12);
ca.setLayoutParams(lp);
TextView t1=new TextView(this);
t1.setText(a);
t1.setTypeface(null,1);
t1.setTextSize(16);
ca.addView(t1);
TextView t2=new TextView(this);
t2.setText(b);
t2.setTextColor(Color.GRAY);
t2.setTextSize(12);
ca.addView(t2);
ca.setOnClickListener(v->{
Intent i=new Intent(Intent.ACTION_VIEW,
Uri.parse("https://www.google.com/maps/search/"+a));
startActivity(i);
});
return ca;
}

void appointments(){
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setPadding(dp(20),dp(40),dp(20),dp(20));
m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView bk=new TextView(this);
bk.setText("<- Back");
bk.setTypeface(null,1);
bk.setOnClickListener(v->home());
m.addView(bk);
TextView h=new TextView(this);
h.setText("\nMy Appointments");
h.setTextSize(24);
h.setTypeface(null,1);
m.addView(h);
m.addView(setCard("Upcoming","No appointments"));
m.addView(setCard("History","View past bookings"));
m.addView(setCard("Book New","Book appointment"));
ScrollView sv=new ScrollView(this);
sv.addView(m);
setContentView(sv);
}

void support(){
Intent i=new Intent(Intent.ACTION_VIEW,
Uri.parse("https://algeria.blsspainvisa.com"));
startActivity(i);
}

void settings(){
LinearLayout m=new LinearLayout(this);
m.setOrientation(1);
m.setPadding(dp(20),dp(40),dp(20),dp(20));
m.setBackgroundColor(Color.parseColor("#F5F7FF"));
TextView bk=new TextView(this);
bk.setText("<- Back");
bk.setTypeface(null,1);
bk.setOnClickListener(v->home());
m.addView(bk);
TextView h=new TextView(this);
h.setText("\nSettings");
h.setTextSize(26);
h.setTypeface(null,1);
h.setTextColor(Color.parseColor("#0F0C29"));
m.addView(h);
m.addView(setCard("Language","العربية / Français / English"));
m.addView(setCard("Notifications","Manage alerts"));
m.addView(setCard("Dark Mode","On / Off"));
m.addView(setCard("Privacy Policy","View policy"));
m.addView(setCard("About BLS","Version 1.0.0"));
m.addView(setCard("Logout","Sign out"));
ScrollView sv=new ScrollView
