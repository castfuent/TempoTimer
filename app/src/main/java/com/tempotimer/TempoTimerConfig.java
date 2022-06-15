package com.tempotimer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Integer.parseInt;

public class TempoTimerConfig extends AppCompatActivity  implements View.OnClickListener{


        private Spinner spConc, spExc;
        private Integer[] data;
        EditText textNumber;
        SharedPreferences sp;
       Preferences p;

        @SuppressLint("CutPasteId")
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_timer_config);
        spConc = findViewById(R.id.spinnerConc);
        spExc = findViewById(R.id.spinnerEx);
        data = new Integer[]{125, 250, 500, 750, 1000};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        spConc.setAdapter(adapter);
        spExc.setAdapter(adapter);

        findViewById(R.id.t_minusc).setOnClickListener(this);
        findViewById(R.id.tplusc).setOnClickListener(this);
        textNumber = findViewById(R.id.eTMinInut);


        sp = getApplicationContext().getSharedPreferences("PreferenceTimes", 0);
        readPreferences();


        Button bExit = findViewById(R.id.bconfirreturn);
        bExit.setOnClickListener(v -> {
            EditText et_min_time = findViewById(R.id.eTMinInut);
            String text = et_min_time.getText().toString();
            int min_time = p.getMin_init_time();
            try {
                min_time = Integer.parseInt(text);
            }catch (Exception ignored){

            }
            p.setMin_init_time(min_time);
           int spinner_pos_conc = spConc.getSelectedItemPosition();
           int spinner_pos_exc = spExc.getSelectedItemPosition();
            p.setMin_conc_time(data[spinner_pos_conc]);
            p.setMin_exc_time(data[spinner_pos_exc]);
            writePreferences();
            Intent i = new Intent(TempoTimerConfig.this, TempoTimerMain.class);

            startActivity(i);
            finish();
        });

    }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick (View v){
        switch (v.getId()) {
            case R.id.t_minusc:
                change(0);
                break;
            case R.id.tplusc:
                change(1);
                break;

        }

    }

        @SuppressLint("SetTextI18n")
        private void change (int column){

        int i = parseInt(textNumber.getText().toString());
        if (column == 0) {
            if (i > 1) i--;
        } else {
            i++;
        }
        textNumber.setText("" + i);
    }


    @SuppressLint("SetTextI18n")
    private void readPreferences(){
        p=new Preferences();
        p.setMin_init_time(sp.getInt("min_time_init",p.getMin_init_time()));
        textNumber.setText(""+p.getMin_init_time());
        p.setMin_exc_time(sp.getInt("min_time_exc",p.getMin_exc_time()));
        p.setMin_conc_time(sp.getInt("min_time_conc",p.getMin_conc_time()));
    }

    private void writePreferences(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("min_time_init", p.getMin_init_time()); // Storing integer
        editor.putInt ("min_time_exc",p.getMin_exc_time()); // Storing integer
        editor.putInt("min_time_conc", p.getMin_conc_time()); // Storing in
        editor.apply(); // commit changes

    }



}