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
@Override protected void onCreate(Bundle b){super.onCreate(b);
SharedPreferences sp=getSharedPreferences("bls",0);
curLang=sp.getInt("lang",0);home();}
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
String tVisa="Visa Services";String tBook="Book your appointment";
String p1="Passport";String v1="Visa Centers";String a1="My Appointments";String btnT="Get Started";
if(curLang==1){tVisa="Services Visa";tBook="Reservez votre RDV";p1="Passeport";v1="Centres Visa";a1="Mes RDV";btnT="Commencer";}
if(curLang==2){tVisa="خدمات التأشيرة";tBook="احجز موعدك";p1="جواز السفر";v1="مراكز التأشيرة";a1="مواعيدي";btnT="ابدأ الآن";}
TextView t1=new TextView(this);t1.setText("\n"+tVisa);t1.setTextSize(30);t1.setTextColor(-1);t1.setTypeface(null,1);m.addView(t1);
TextView t2=new TextView(this);t2.setText(tBook+"\n");t2.setTextColor(Color.parseColor("#9AA0B6"));t2.setPadding(0,dp(8),0,dp(20));m.addView(t2);
m.addView(card(p1,"New, Renew & Track",0));m.addView(card(v1,"Algiers Oran Constantine",1));m.addView(card(a1,"View & Manage",2));m.addView(card("Support & Help","Contact BLS",3));
TextView btn=new TextView(this);btn.setText(btnT);btn.setTextSize(18);btn.setTextColor(c1);btn.setGravity(17);btn.setTypeface(null,1);
GradientDrawable bbg=new GradientDrawable();bbg.setCornerRadius(dp(30));bbg.setColor(-1);btn.setBackground(bbg);
LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(56));pr.topMargin=dp(25);btn.setLayoutParams(pr);btn.setPadding(0,dp(14),0,dp(14));btn.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(btn);
sv.addView(m);setContentView(sv);
}
LinearLayout card(String a,String b,int t){
LinearLayout ca=new LinearLayout(this);ca.setOrientation(0);ca.setGravity(16);ca.setPadding(dp(18),dp(16),dp(18),dp(16));
GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(18));g.setColor(Color.parseColor("#22FFFFFF"));g.setStroke(1,Color.parseColor("#33FFFFFF"));ca.setBackground(g);
LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(12);ca.setLayoutParams(lp);
LinearLayout tx=new LinearLayout(this);tx.setOrientation(1);
TextView tt=new TextView(this);tt.setText(a);tt.setTextColor(-1);tt.setTextSize(16);tt.setTypeface(null,1);tx.addView(tt);
TextView dd=new TextView(this);dd.setText(b);dd.setTextColor(Color.parseColor("#9AA0B6"));dd.setTextSize(11);tx.addView(dd);ca.addView(tx);
if(t==0)ca.setOnClickListener(v->passport());if(t==1)ca.setOnClickListener(v->centers());
if(t==2)ca.setOnClickListener(v->appoint());if(t==3)ca.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));return ca;
}
void passport(){
LinearLayout m=base();m.addView(title("Passport Services"));
LinearLayout c1=sCard("New Passport","Apply for new");c1.setOnClickListener(v->open("https://algeria.blsspainvisa.com/book_appointment.php"));m.addView(c1);
LinearLayout c2=sCard("Renew Passport","Renew existing");c2.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(c2);
LinearLayout c3=sCard("Track Status","Track application");c3.setOnClickListener(v->trackPage());m.addView(c3);
LinearLayout c4=sCard("Requirements","Documents needed");c4.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(c4);
show(m);
}
void trackPage(){
LinearLayout m=base();m.addView(title("Track Status"));
EditText e1=input("Enter Tracking Number");m.addView(e1);
TextView btn=btnDark("Track on BLS Website");btn.setOnClickListener(v->open("https://algeria.blsspainvisa.com/track_application.php"));m.addView(btn);
TextView btn2=btnDark("Check Status Here");m.addView(btn2);
TextView res=new TextView(this);res.setPadding(0,dp(20),0,0);m.addView(res);
btn2.setOnClickListener(v->{
String tr=e1.getText().toString();
if(tr.isEmpty()){res.setText("Please enter number");return;}
res.setText("Tracking "+tr+":\n\n✓ Received\n✓ Under Processing\n○ Ready for Collection");
});show(m);
}
void centers(){LinearLayout m=base();m.addView(title("BLS Centers Algeria"));
m.addView(cCard("BLS Algiers","El Biar"));m.addView(cCard("BLS Oran","Bir El Djir"));m.addView(cCard("BLS Constantine","Zouaghi"));
TextView mp=btnDark("Open in Google Maps");mp.setOnClickListener(v->open("https://www.google.com/maps/search/BLS+Spain+Algeria"));m.addView(mp);
TextView mp2=btnDark("View on BLS Website");mp2.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(mp2);show(m);}
void appoint(){LinearLayout m=base();m.addView(title("My Appointments"));
LinearLayout c1=sCard("Upcoming","View on website");c1.setOnClickListener(v->open("https://algeria.blsspainvisa.com/track_application.php"));m.addView(c1);
LinearLayout c2=sCard("History","View past bookings");c2.setOnClickListener(v->open("https://algeria.blsspainvisa.com/track_application.php"));m.addView(c2);
LinearLayout c3=sCard("Book New","Book appointment");c3.setOnClickListener(v->open("https://algeria.blsspainvisa.com/book_appointment.php"));m.addView(c3);
show(m);}
void langPage(){LinearLayout m=base();m.addView(title("Choose Language / Langue"));
LinearLayout c0=sCard("English","Default");c0.setOnClickListener(v->{setLang(0);});m.addView(c0);
LinearLayout c1=sCard("Français","French");c1.setOnClickListener(v->{setLang(1);});m.addView(c1);
LinearLayout c2=sCard("العربية","Arabic");c2.setOnClickListener(v->{setLang(2);});m.addView(c2);show(m);}
void setLang(int l){SharedPreferences sp=getSharedPreferences("bls",0);sp.edit().putInt("lang",l).apply();curLang=l;Toast.makeText(this,"Language Changed ✓",Toast.LENGTH_SHORT).show();home();}
void settings(){LinearLayout m=base();m.addView(title("Settings"));
LinearLayout lc=sCard("Language",curLang==0?"English":curLang==1?"Français":"العربية");lc.setOnClickListener(v->langPage());m.addView(lc);
LinearLayout c1=sCard("Notifications","View updates");c1.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(c1);
LinearLayout c2=sCard("Privacy Policy","View policy");c2.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(c2);
LinearLayout c3=sCard("About BLS","Official website");c3.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(c3);
LinearLayout c4=sCard("Contact Support","Contact BLS");c4.setOnClickListener(v->open("https://algeria.blsspainvisa.com/"));m.addView(c4);
show(m);}
LinearLayout base(){LinearLayout m=new LinearLayout(this);m.setOrientation(1);m.setPadding(dp(20),dp(35),dp(20),dp(20));m.setBackgroundColor(Color.parseColor("#F5F7FF"));TextView bk=new TextView(this);bk.setText("<- Back");bk.setTypeface(null,1);bk.setOnClickListener(v->home());m.addView(bk);return m;}
TextView title(String s){TextView h=new TextView(this);h.setText("\n"+s+"\n");h.setTextSize(22);h.setTypeface(null,1);return h;}
EditText input(String hint){EditText e=new EditText(this);e.setHint(hint);e.setPadding(dp(16),dp(14),dp(16),dp(14));GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(12));g.setColor(Color.WHITE);g.setStroke(1,Color.parseColor("#E5E5E5"));e.setBackground(g);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(10);e.setLayoutParams(lp);return e;}
TextView btnDark(String t){TextView b=new TextView(this);b.setText(t);b.setTextColor(-1);b.setGravity(17);b.setTypeface(null,1);GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(30));g.setColor(Color.parseColor("#0F0C29"));b.setBackground(g);LinearLayout.LayoutParams pr=new LinearLayout.LayoutParams(-1,dp(52));pr.topMargin=dp(12);b.setLayoutParams(pr);b.setPadding(0,dp(14),0,dp(14));return b;}
LinearLayout sCard(String a,String b){LinearLayout ca=new LinearLayout(this);ca.setOrientation(1);ca.setPadding(dp(14),dp(12),dp(14),dp(12));GradientDrawable g=new GradientDrawable();g.setCornerRadius(dp(12));g.setColor(Color.WHITE);g.setStroke(1,Color.parseColor("#E5E5E5"));ca.setBackground(g);LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(10);ca.setLayoutParams(lp);TextView t1=new TextView(this);t1.setText(a);t1.setTypeface(null,1);ca.addView(t1);TextView t2=new TextView(this);t2.setText(b);t2.setTextSize(11);t2.setTextColor(Color.GRAY);ca.addView(t2);return ca;}
LinearLayout cCard(String a,String b){LinearLayout ca=sCard(a,b);ca.setOnClickListener(v->open("https://www.google.com/maps/search/"+a+" Algeria"));return ca;}
void open(String url){Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(url));startActivity(i);}
void show(LinearLayout m){ScrollView sv=new ScrollView(this);sv.addView(m);setContentView(sv);}
int dp(int v){return(int)TypedValue.applyDimension(1,v,getResources().getDisplayMetrics());}
}
