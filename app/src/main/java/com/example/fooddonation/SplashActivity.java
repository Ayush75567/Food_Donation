package com.example.fooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sp;
    ImageView image;
    TextView app_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        image=findViewById(R.id.img_icon);
        app_name=findViewById(R.id.app_name);
        sp=getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        AlphaAnimation animation=new AlphaAnimation(0,1);
        animation.setDuration(3000);
        image.startAnimation(animation);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               if (sp.getString(ConstantSp.ID, "").equalsIgnoreCase("")) {
                   new commomMethod(SplashActivity.this, MainActivity.class);
               } else {
                   new commomMethod(SplashActivity.this, HomeActivity.class);
               }
           }
       },3000);
    }
}