package com.namth.lab8_ph31259;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Main_chaoMung extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chao_mung);
        ImageView img_logo=findViewById(R.id.img_logo);
        Animation animation=AnimationUtils.loadAnimation(Main_chaoMung.this,R.anim.alpha);
        img_logo.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Main_chaoMung.this,Dangnhap.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}