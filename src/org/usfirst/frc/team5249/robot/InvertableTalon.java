package org.usfirst.frc.team5249.robot;
 
import edu.wpi.first.wpilibj.Talon;

public class InvertableTalon extends Talon{

	private boolean invert;
	
	public InvertableTalon(int channel, boolean invert) {
		super(channel);
		this.invert = invert;
	}
	
	public void set(double speed){
		if(invert){
			speed = -speed;
		}
		super.set(speed);
	}

}
