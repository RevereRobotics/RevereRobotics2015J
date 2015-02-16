package org.usfirst.frc.team5249.robot;

public class autonomousRobot {

	//there is a maximum of 100 actions
	int[] autonomousTime = new int[100];
	boolean[] autonomousActionAvailable = new boolean[100];
	
	public autonomousRobot(){
		for(int i = 0; i<100;i++){
			autonomousActionAvailable[i] = true;
		}
	}
	
	//For the start boolean, if it is true, then it returns start time; if false then it returns end time.
	public int autonomousOrderedTime(int lengthOfAction, boolean start) {
		int startTime = 0;
		int endTime = 0;
		int actionNumber = 0;
		
		for(int i = 0; i<autonomousActionAvailable.length;i++){
			if(autonomousActionAvailable[i]){
				autonomousTime[i]=lengthOfAction;
				autonomousActionAvailable[i] = false;
				actionNumber = i;
				i = autonomousActionAvailable.length;
			}
		}
		
		for(int i = 0;i<actionNumber;i++){
			startTime+=autonomousTime[i];
		}
		
		endTime = startTime+lengthOfAction;
		
		if(start){
			return startTime;
		}else{
			return endTime;
		}
	}
	
	
}
