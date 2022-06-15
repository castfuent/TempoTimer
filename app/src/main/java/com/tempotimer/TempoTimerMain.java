package com.tempotimer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.parseInt;

public class TempoTimerMain extends AppCompatActivity  implements View.OnClickListener{



    EditText []texts;
    Button bStart, bReset,bExit;
    Logica l;
    SharedPreferences sp;
    Preferences p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_timer_main);
        p=new Preferences();

        texts = new EditText[6];
        texts[0] = findViewById(R.id.sets);
        texts[1] = findViewById(R.id.tempoStart);
        texts[2] = findViewById(R.id.tempo1);
        texts[3] = findViewById(R.id.tempo2);
        texts[4] = findViewById(R.id.tempo3);
        texts[5] = findViewById(R.id.tempo4);

       sp = getApplicationContext().getSharedPreferences("PreferenceTimes", 0);
        readPreferences();
        //  writePreferences();

        l=new Logica(p);
        try {
            l = (Logica) getIntent().getExtras().get("logic");
        }
        catch (Exception ignored){

        }
        draw();

        bStart = findViewById(R.id.botonStart);
        bStart.setOnClickListener(v -> {
            Intent i = new Intent(TempoTimerMain.this, TempoTimerE.class);
            int[] data =new int [6];
            for (int j=0;j<6;j++){
                data[j]=parseInt(texts[j].getText().toString());
            }
            Switch sw1=findViewById(R.id.switchInverse);
            boolean activate=sw1.isChecked();
           if (activate)
               l=new LogicaInvert(p);
           else
               l=new Logica(p);
            l.setData(data);
            i.putExtra("logic",l);
            startActivity(i);
            finish();
        });



        bReset= findViewById(R.id.botonReset);
        bReset.setOnClickListener(v -> reset());

        bExit= findViewById(R.id.botonExit);
        bExit.setOnClickListener(v -> finish());

        ImageButton bConfg= findViewById(R.id.configButton);
        bConfg.setOnClickListener(v -> {
            Intent i = new Intent(TempoTimerMain.this, TempoTimerConfig.class);
         //   i.putExtra("pref",p);
            startActivity(i);
        });



        findViewById(R.id.tvr_minus).setOnClickListener(this);
        findViewById(R.id.tvr_plus).setOnClickListener(this);
        findViewById(R.id.tvi_minus).setOnClickListener(this);
        findViewById(R.id.tvi_plus).setOnClickListener(this);
        findViewById(R.id.tv1_minus).setOnClickListener(this);
        findViewById(R.id.tv1_plus).setOnClickListener(this);
        findViewById(R.id.tv2_minus).setOnClickListener(this);
        findViewById(R.id.tv2_plus).setOnClickListener(this);
        findViewById(R.id.tv3_minus).setOnClickListener(this);
        findViewById(R.id.tv3_plus).setOnClickListener(this);
        findViewById(R.id.tv4_minus).setOnClickListener(this);
        findViewById(R.id.tv4_plus).setOnClickListener(this);
    }




    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
       switch (v.getId()){
           case R.id.tvr_minus: change(0,0);break;
           case R.id.tvr_plus: change(0,1);break;
           case R.id.tvi_minus: change(1,0);break;
           case R.id.tvi_plus: change(1,1);break;
           case R.id.tv1_minus: change(2,0);break;
           case R.id.tv1_plus: change(2,1);break;
           case R.id.tv2_minus: change(3,0);break;
           case R.id.tv2_plus: change(3,1);break;
           case R.id.tv3_minus: change(4,0);break;
           case R.id.tv3_plus: change(4,1);break;
           case R.id.tv4_minus: change(5,0);break;
           case R.id.tv4_plus: change(5,1);break;

           default:
               throw new IllegalStateException("Unexpected value: " + v.getId());
       }

   }

   @SuppressLint("SetTextI18n")
   private void change(int row, int column){

        if (row==0) {
            int i = parseInt(texts[row].getText().toString());
            boolean c = false;
            if (column == 0 && i > 1) {
                i--;
                c = true;
            }
            if (column == 1) {
                i++;
                c = true;
            }
            if (c)
                texts[row].setText("" + i);

        }
        else
        if (row==1){
            int i = parseInt(texts[row].getText().toString());
            boolean c = false;
            if (column == 0 && i > p.getMin_init_time()) {
                i--;
                c = true;
            }
            if (column == 1) {
                i++;
                c = true;
            }
            if (c)
                texts[row].setText("" + i);
            else
                    Toast.makeText(TempoTimerMain.this,R.string.minTimeText ,Toast.LENGTH_SHORT).show();

        }
        else
        if (row>1 && row<texts.length) {
            int i = parseInt(texts[row].getText().toString());
            boolean c = false;
            if (column == 0 && i > 0) {
                i--;
                c = true;
            }
            if (column == 1) {
                i++;
                c = true;
            }
            if (c)
                texts[row].setText("" + i);
        }
   }

    private void reset (){
        l=new Logica(p);
        draw();
    }

    private void draw() {
        for (int i=0;i<texts.length;i++) {
            texts[i].setText(l.getStringData(i));
        }
        Switch sw1= findViewById(R.id.switchInverse);
        sw1.setChecked(l instanceof  LogicaInvert);

    }

/*
    public void writePreferences(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("inverse", p.isInverse()); // Storing boolean - true/false
        editor.putInt("min_time_init", p.getMin_init_time()); // Storing integer
        editor.putInt ("min_time_exc",p.getMin_exc_time()); // Storing integer
        editor.putInt("min_time_conc", p.getMin_conc_time()); // Storing in
        editor.putInt("default_init_time",p.getDefault_init_time());
        editor.putInt("default_exc_time",p.getDefault_exc_time());
        editor.putInt("default_pause_time",p.getDefault_pause_time());
        editor.putInt("default_conc_time",p.getDefault_conc_time());
        editor.putInt("default_rest_time",p.getDefault_rest_time());
        editor.putInt("default_reps",p.getDefault_reps());
        editor.commit(); // commit changes

    }
*/



    private void readPreferences(){
      //  boolean inverse=sp.getBoolean("inverse", p.isInverse());
        int aux=sp.getInt("min_time_init",p.getMin_init_time());
        p.setMin_init_time(aux); // Storing intege
        p.setMin_exc_time(sp.getInt("min_time_exc",p.getMin_exc_time() )); // Storing integer
        p.setMin_conc_time(sp.getInt("min_time_conc", p.getMin_conc_time())); // Storing in
        p.setDefault_init_time(sp.getInt("default_init_time",p.getDefault_init_time()));
        if (p.getDefault_init_time()<p.getMin_init_time())
            p.setDefault_init_time(p.getMin_init_time());
        p.setDefault_exc_time(sp.getInt("default_exc_time",p.getDefault_exc_time()));
        p.setDefault_pause_time(sp.getInt("default_pause_time",p.getDefault_pause_time()));
        p.setDefault_conc_time(sp.getInt("default_conc_time",p.getDefault_conc_time()));
        p.setDefault_rest_time(sp.getInt("default_rest_time",p.getDefault_rest_time()));
        p.setDefault_reps(sp.getInt("default_reps",p.getDefault_reps()));

    }

}