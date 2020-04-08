package com.jccsisc.controlbsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.jccsisc.controlbsc.activities.LoginActivity;

public class Splash_ScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        //abrimos el loginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_ScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                Animatoo.animateFade(Splash_ScreenActivity.this);//con esta linea ejecutamos las animaciones ya con la libreria agregada
                finish();
            }
        }, 1500);
    }
}
