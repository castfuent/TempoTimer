package com.tempotimer;

import java.io.Serializable;

public class Logica implements Serializable {


	protected int[] data;
	protected int[] phases;


	//this is the state of the progam
	protected int phase;
	protected long count;
	protected long total_count;

	//reps total is the total of reps that we recevie of MainActitivy
	//reps is the reps felt to final
	protected int reps;

	//ok: timer is for count the secods. If we have a lift with minimun time, we broke the rule that
	//any sound is each second. So we launch a sound if timer is TIME DIVIDER or if we chane the
	//state. More information in eventTime
	protected int timer;

	Preferences p;

	public Logica(Preferences p) {
		int n_PHASES = 7;
		phases=new int[n_PHASES];
		//around the phase´s times
		//the array is for the time of tics,
		int n_DATA = 6;
		data=new int[n_DATA];
		this.p=p;
		total_count=0;
		setData(p.getDefaultData());
		restart();
	}



	public void setData(int[] data){
		this.data=data;
		setPhases();
	}




	public void setPhases() {
		//check if the length the array is correct
			phases[0]=0;
			phases[1]=1000;
			phases[2]=p.getMin_init_time()*1000;
			// if the initial time is two or lower. we always put 2 seconds (phase 2)
			// the rest to phase 1
			if (data[1]>p.getMin_init_time()) {
				phases[1]=(data[1]-p.getMin_init_time())*1000;
			}

			//only phase 3 and 5 have minimun time
			if (data[2]<=0) phases[3]=p.getMin_exc_time();
			else  phases[3]=data[2]*1000;

			if (data[3]<=0) phases[4]=0;
			else phases[4]=data[3]*1000;
			
			if (data[4]<=0) phases[5]=p.getMin_conc_time();
			else phases[5]=data[4]*1000;

			if (data[5]<=0) phases[6]=0;
			else phases[6]=data[5]*1000;

	}


	/**
	 *
	 * @return total of tics in the logic
	 */
	public long getTotal() {
		long total=phases[0];
		total+=phases[1];
		total+=phases[2];
		total+=phases[3]*data[0];
		total+=phases[4]*data[0];
		total+=phases[5]*data[0];
		total+=phases[6]*(data[0]-1);
		return total;
	}


	/**
	 * we start the logic. if the initial time is 2 or lower, we go to phase 2. else g oto phase 1
	 * and we start the count to zero (always in a phase change
	 */
	public void start() {
		if (phase==0){
			if (phases[1]==0)
				phase=2;
			else
				phase =1;
			total_count=getTotal();
			count=total_count-phases[phase];

		}

	}



	/**
	 *if we want to restart the logic
	 */
	public void restart() {
		total_count=getTotal();
		reps=0;
		phase=0;
		timer=0;
	}



	public String getRepsString(){
		return reps+"/"+data[0];
	}

	/**
	 * the logic autmated
	 * @return actual phase if we change or -1 if we dont change
	 */
	public int count(long time) {

 		timer+=total_count-time;
 		total_count=time;
 		//no sound
		int sound = -1;
		//if tis a second!!
		if (timer>=1000) {
			sound=8;
			timer=0;
		}


		//this var is for if we have any phase´s change
		//int aux=-1;
		//and the flag is the same but for he sound

		if (total_count<=count) {
			//CHANGE!!!!!!!
			//we restart the count for the next change


			timer=0;
			switch (phase) {
				case 1: phase=2;
						sound=2;
						break;
				case 2:phase=3;
						sound=3;
						reps++;
						break;
			//excentric pahse
				case 3: if (phases[4]==0) {
								phase = 5;
								sound = 5;
							}else {
								phase = 4;
								sound = 4;
							}
							if (phases[3] ==p.getMin_exc_time())
							sound = -1;

	    					break;
			//
				case 4: phase=5; sound=5;
						break;
				case 5://if the finish the phase and  we are int the last rep, we finsih!!!!

						if (reps==data[0]) {

							phase=7;
						}
						else {
							if (phases[6]==0) {
								reps++;
								phase=3;
								sound=3;
							}
							else {
								phase=6;
								sound=6;
								if (phases[6]<=0)
									sound=-1;
								if (phases[5]==p.getDefault_conc_time())
									sound=-1;
							}
						}
						break;
				case 6:reps++;
						phase=3;
						sound=3;
					break;
				
		}//fin switch
			if (phase!=7)
				count=total_count-phases[phase];
	}//fin del if

		//now the sound. and return. If we have to know the phase, go to getState()!!!!
	//	return eventTime(flag);
		return sound;
			
}//fin del metodi




	/**
	 * @return time to display int the interface. Seconds to end the actual phase
	 */
	public int getTime() {
	//	if (phase == 1) {
	//		return (phases[phase]+phases[2] - count - 1) / TIME_DIVIDER + 1;
	//	}
		int number=0;
		if (phase<phases.length) {
			//	return (phases[phase]  - 1) / TIME_DIVIDER + 1 ;
			number=(int) ((total_count - count - 1) / 1000) + 1;
			if (phase==1)
				number+= phases[2]/1000;
		}
		return number;

	}
	/**
	 * @return actual state or phase in the logic
	 */
	public int getState() {return phase;}



	public String getStringData(int i){
		if (i>=0 && i<data.length){
			return ""+data[i];
		}
		return "-";
	}

	public void stop() {
		this.phase=7;

	}


	public long getTotalCount() {
		return total_count;
	}

/*	public long getCount() {
		return count;
	}
*/
	public int getInitTime() {
		return (phases[1]+phases[2])/1000;
	}

	public boolean isRunning() {
		return phase!=0;
	}
}

