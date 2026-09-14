package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
public class MainActivity extends Activity {
@Override
protected void onCreate(Bundle b) {
super.onCreate(b);
TextView tv = new TextView(this);
tv.setText("BLS\n\nBUILD SUCCESS\n\nالوان قاتلة راهي جاية!");
tv.setTextSize(24);
tv.setTextColor(Color.WHITE);
tv.setBackgroundColor(Color.parseColor("#0F0C29"));
tv.setGravity(Gravity.CENTER);
tv.setPadding(50,200,50,200);
setContentView(tv);
}
}
