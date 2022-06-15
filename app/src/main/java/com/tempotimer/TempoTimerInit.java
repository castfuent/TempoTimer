package com.tempotimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class TempoTimerInit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_timer_init);


        int TIME_SPLASH = 1500;
        new Handler().postDelayed(() -> {
            // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
            Intent i = new Intent(TempoTimerInit.this, TempoTimerMain.class);
            startActivity(i);
            finish();
        }, TIME_SPLASH);

    }
}