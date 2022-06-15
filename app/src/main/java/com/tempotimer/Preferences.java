package com.tempotimer;

import java.io.Serializable;

public class Preferences implements Serializable {
    private int min_exc_time;
    private int min_conc_time;
    private int min_init_time;
    private int default_init_time;
    private int default_conc_time;
    private int default_pause_time;
    private int default_exc_time;
    private int default_rest_time;
    private int default_reps;


    public Preferences() {

        setMin_init_time(4);
        setMin_conc_time(250);
        setMin_exc_time(250);
        setDefault_conc_time(0);
        setDefault_rest_time(3);
        setDefault_exc_time(2);
        setDefault_pause_time(2);
        setDefault_reps(5);
    }


    public int getMin_exc_time() {
        return min_exc_time;
    }

    public void setMin_exc_time(int min_exc_time) {
        this.min_exc_time = min_exc_time;
    }

    public int getMin_conc_time() {
        return min_conc_time;
    }

    public void setMin_conc_time(int min_conc_time) {
        this.min_conc_time = min_conc_time;
    }

    public int getMin_init_time() {
        return min_init_time;
    }

    public void setMin_init_time(int min_init_time) {
        this.min_init_time = min_init_time;
    }

    public int getDefault_init_time() {
        return default_init_time;
    }

    public void setDefault_init_time(int default_init_time) {
        this.default_init_time = default_init_time;
    }

    public int getDefault_conc_time() {
        return default_conc_time;
    }

    public void setDefault_conc_time(int default_conc_time) {
        this.default_conc_time = default_conc_time;
    }

    public int getDefault_pause_time() {
        return default_pause_time;
    }

    public void setDefault_pause_time(int default_pause_time) {
        this.default_pause_time = default_pause_time;
    }

    public int getDefault_exc_time() {
        return default_exc_time;
    }

    public void setDefault_exc_time(int default_exc_time) {
        this.default_exc_time = default_exc_time;
    }

    public int getDefault_rest_time() {
        return default_rest_time;
    }

    public void setDefault_rest_time(int default_rest_time) {
        this.default_rest_time = default_rest_time;
    }

    public int getDefault_reps() {
        return default_reps;
    }

    public void setDefault_reps(int default_reps) {
        this.default_reps = default_reps;
    }

    public int[] getDefaultData() {
        int[] n =new int[6];
        n[0]=getDefault_reps();
        n[1]=getDefault_init_time();
        n[2]=getDefault_exc_time();
        n[3]=getDefault_pause_time();
        n[4]=getDefault_conc_time();
        n[5]=getDefault_pause_time();
        return n;
    }
}
