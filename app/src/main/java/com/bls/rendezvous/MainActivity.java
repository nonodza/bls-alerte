package com.bls.rendezvous;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);

        // زر القائمة يفتح الـ Drawer
        findViewById(R.id.btn_menu).setOnClickListener(v -> {
            drawer.openDrawer(Gravity.START);
        });

        // الكارتات
        findViewById(R.id.btn_alerte).setOnClickListener(v -> {
            Toast.makeText(this, "خدمة تنبيه المواعيد قريبا", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_rdv).setOnClickListener(v -> {
            Toast.makeText(this, "فتح فورم حجز موعد BLS", Toast.LENGTH_SHORT).show();
            // بعدها نزيدو صفحة الحجز
        });

        // روابط السوشيال - بدل الروابط بروابطك
        findViewById(R.id.link_youtube).setOnClickListener(v -> openLink("https://www.youtube.com/@YOUR_CHANNEL"));
        findViewById(R.id.link_facebook).setOnClickListener(v -> openLink("https://www.facebook.com/YOUR_PAGE"));
        findViewById(R.id.link_instagram).setOnClickListener(v -> openLink("https://www.instagram.com/YOUR_PAGE"));
    }

    void openLink(String url){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }catch(Exception e){
            Toast.makeText(this,
