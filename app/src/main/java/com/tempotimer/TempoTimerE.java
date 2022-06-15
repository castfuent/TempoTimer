package com.tempotimer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class TempoTimerE extends AppCompatActivity {

    Button botonStart,botonCancel,botonReset;
    TextView number,textphase;

    CountDownTimer start;
    Logica l;

    String[] texts;
    int[] colors;
    MediaPlayer[] mp;
    int orientation;
    AdView ads;
    private final static int  INTERVAL_TIME=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.orientation=getResources().getConfiguration().orientation;
        setContentView(R.layout.activity_tempo_timer_e);

        MobileAds.initialize(this, initializationStatus -> {

            //Showing a simple Toast Message to the user when The Google AdMob Sdk Initialization is Completed

        });

        ads=findViewById(R.id.bannerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ads.loadAd(adRequest);



        if (l==null)
            l=(Logica)getIntent().getExtras().get("logic");
        number= findViewById(R.id.textNumber);
        textphase= findViewById(R.id.textphase);
        //get The resources
        fill();
        //paint all the screen
        paint();


        botonReset= findViewById(R.id.botonReset);
        botonReset.setOnClickListener(v -> {


            if (start!=null) {
                start.cancel();
            }
            start=null;
            l.restart();
            paint();
        });



        botonStart = findViewById(R.id.BotonStart);
        botonStart.setOnClickListener(v -> {
            ads.setVisibility(View.VISIBLE);

            if (start==null)
                init_count();
          });

        botonCancel = findViewById(R.id.BotonExit);
        botonCancel.setOnClickListener(v -> {
                //if we have any CountDownTimer, we stop it
                if (start!=null) {
                    start.cancel();
                    start=null;
                }
                //go to Main Activity
                Intent i = new Intent(TempoTimerE.this, TempoTimerMain.class);
                i.putExtra("logic",l);
                startActivity(i);
                //bye bye TempoTimerE!!!
                finish();
        });
    }


    @SuppressLint("SetTextI18n")
    private void paint(){
        number.setBackgroundColor(colors[l.getState()]);
        textphase.setText(l.getRepsString() + " " + texts[l.getState()]);
        if (l.getState()==7)
            number.setText("-");
        else
            if (l.getState()==0)

                number.setText(""+l.getInitTime());
            else
            number.setText("" + l.getTime());
    }




    private void fill(){
        mp=new MediaPlayer[8];
        texts=new String[8];
        colors=new int[8];

        mp[0]=MediaPlayer.create(this, R.raw.tic);
        mp[1]=MediaPlayer.create(this, R.raw.tic);
        mp[2]=MediaPlayer.create(this, R.raw.r);
        mp[3]=MediaPlayer.create(this, R.raw.gong2);
        mp[4]= MediaPlayer.create(this, R.raw.metal);
        mp[5]= MediaPlayer.create(this, R.raw.pistol);
        mp[6]=MediaPlayer.create(this, R.raw.fin);
        mp[7]= MediaPlayer.create(this, R.raw.fintotal);

        texts[0]=getString(R.string.text0);
        //texts[0]= R.string.text0;
        texts[1]=getString(R.string.text1);
        texts[2]=getString(R.string.text2);
        texts[3]=getString(R.string.text3);
        texts[4]=getString(R.string.text4);
        texts[5]=getString(R.string.text5);
        texts[6]=getString(R.string.text6);
        texts[7]=getString(R.string.text7);

        colors[0]= Color.GRAY;
        colors[1]=Color.GRAY;
        colors[2]=Color.GRAY;
        colors[3]=Color.YELLOW;
        colors[4]=Color.RED;
        colors[5]=Color.GREEN;
        colors[6]=Color.CYAN;
        colors[7]=Color.GRAY;
    }





    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("logic",l);
        outState.putInt("orientation",orientation);
       if (start!=null) {
           start.cancel();
           start = null;
       }
}



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        this.l = (Logica) savedInstanceState.getSerializable("logic");
        if (l.isRunning()) {
             paint();
             init_count();
         }

    }


    /**
     * init_count
     *
     */
    private void init_count() {


        long aux = l.getTotal();
        if (l.isRunning()) {
            aux = l.getTotalCount();
        }
        l.start();


        start = new CountDownTimer(aux, INTERVAL_TIME) {

            public void onTick(long millisUntilFinished) {
                if (l.getState() != 7) {

                    int option = l.count(millisUntilFinished);
                    if (option == 8) {
                        mp[0].start();
                    } else if (option >= 0 && option < 7) {
                        mp[option].start();
                    }
                    paint();
                }
            }

            public void onFinish() {
                l.stop();
                paint();
                mp[7].start();
                l.restart();
            }
        }.start();
    }

}