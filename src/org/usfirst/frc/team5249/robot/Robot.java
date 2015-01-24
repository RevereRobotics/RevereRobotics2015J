package org.usfirst.frc.team5249.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	/** 
     * I Didnt know how many motors we have. I coded for 4 motors.
     */
	
	//RobotDrive myRobot;
	//Joystick leftStick, rightStick;
	int autoLoopCounter;	
	
	Joystick leftStick = new Joystick(1);
	Joystick rightStick = new Joystick(0);
	Talon frontLeft;
	Talon rearLeft;
	Talon frontRight;
	Talon rearRight;
	RobotDrive myRobot;

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
			
	
    public void robotInit() {
    	 frontLeft = new Talon(0);
    	 rearLeft = new Talon(1);
    	 frontRight = new Talon(2);
    	 rearRight = new Talon(3);
    	 myRobot = new RobotDrive(rearLeft, frontLeft, rearRight, frontRight);
    }
    
    /**
     * 
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//Not sure if we're using tank drive or arcade, so I commented out arcade.
    	//myRobot.arcadeDrive(stick);
    	while (isOperatorControl() && isEnabled()) {
        myRobot.tankDrive(leftStick, rightStick);
        Timer.delay(0.01);
    	}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();

    }
    
}
