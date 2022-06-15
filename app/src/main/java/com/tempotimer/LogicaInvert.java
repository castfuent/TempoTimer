package com.tempotimer;

public class LogicaInvert extends Logica {


    public LogicaInvert(Preferences p){
        super(p);
    }


   public int count(long time){

            timer += total_count - time;
            total_count = time;
            //no sound
            int sound = -1;
            //if tis a second!!
            if (timer >= 1000) {
                sound = 8;
                timer = 0;
            }


            //this var is for if we have any phase´s change
            //int aux=-1;
            //and the flag is the same but for he sound

            if (total_count <= count) {
                //CHANGE!!!!!!!
                //we restart the count for the next change
                timer = 0;
                switch (phase) {
                    case 1:
                        phase = 2;
                        sound = 2;
                        break;
                    case 2:
                        phase = 5;
                        sound = 5;
                        reps++;
                        break;
                    //excentric pahse
                    case 5:
                        if (phases[4] == 0) {
                            phase = 3;
                            sound = 3;
                        } else {
                            phase = 4;
                            sound = 4;
                        }
                        if (phases[5] == p.getMin_exc_time())
                            sound = -1;
                        break;
                    //
                    case 4:
                        phase = 3;
                        sound = 3;
                        break;
                    case 3://if the finish the phase and  we are int the last rep, we finsih!!!!

                        if (reps == data[0]) {
                            phase = 7;
                        } else {
                            if (phases[6] == 0) {
                                reps++;
                                phase = 4;
                                sound = 4;
                            } else {
                                phase = 6;
                                sound = 6;
                                if (phases[6] <= 0)
                                    sound = -1;
                                if (phases[3] == p.getDefault_conc_time())
                                    sound = -1;
                            }
                        }
                        break;
                    case 6:
                        reps++;
                        phase = 5;
                        sound = 5;
                        break;

                }//fin switch
                if (phase != 7)
                    count = total_count - phases[phase];
            }//fin del if

            //now the sound. and return. If we have to know the phase, go to getState()!!!!
            //	return eventTime(flag);
            return sound;
        }


}
