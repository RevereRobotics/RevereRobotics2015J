package org.usfirst.frc.team5249.robot;

//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team5249.robot.XboxController;
import org.usfirst.frc.team5249.robot.InvertableTalon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	int autoLoopCounter;
	InvertableTalon frontLeft;
	InvertableTalon rearLeft;
	InvertableTalon frontRight;
	InvertableTalon rearRight;
	Jaguar winchSystem;
	RobotDrive myRobot;
	XboxController drive;
	DoubleSolenoid alphaPiston = new DoubleSolenoid(1, 0);
	Compressor compressor = new Compressor(0);
	double leftMotorSpd;
	double rightMotorSpd;
	
	double leftAdjust;
	double rightAdjust;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		compressor.start();
		winchSystem = new Jaguar(4);
		frontLeft = new InvertableTalon(0, true);
		rearLeft = new InvertableTalon(1, true);
		frontRight = new InvertableTalon(2, true);
		rearRight = new InvertableTalon(3, true);
		myRobot = new RobotDrive(rearLeft, frontLeft, rearRight, frontRight);
		drive = new XboxController(0);

	}

	public void autonomousRight(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			myRobot.setLeftRightMotorOutputs(.5*leftAdjust, -.5*rightAdjust);
		}
	}
	public void autonomousLeft(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			myRobot.setLeftRightMotorOutputs(-.5*leftAdjust, .5*rightAdjust);
		}
	}
	public void autonomousForward(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			myRobot.setLeftRightMotorOutputs(-.5*leftAdjust, -.5*rightAdjust);
		}
	}
	public void autonomousBackward(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			myRobot.setLeftRightMotorOutputs(.5*leftAdjust, .5*rightAdjust);
		}
	}
	public void autonomousStop(int endTime){
		if (autoLoopCounter>150) {
			alphaPiston.set(DoubleSolenoid.Value.kReverse);
			myRobot.drive(0, 0);
		}
	}
	public void autonomousOpen(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			alphaPiston.set(DoubleSolenoid.Value.kReverse);
		}
	}
	public void autonomousClose(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			alphaPiston.set(DoubleSolenoid.Value.kForward);
		}
	}
	public void autonomousLift(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			winchSystem.set(.5);
		}
	}
	public void autonomousLower(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			winchSystem.set(-.5);
		}
	}
	public void autonomousWinchStop(int startTime, int endTime){
		if ((autoLoopCounter < endTime)&&(autoLoopCounter>startTime)) {
			winchSystem.set(0);
		}
	}
	
	/**
	 *
	 * This function is run once each time the robot enters autonomous mode
	 */
	public void autonomousInit() {
		
		leftAdjust = .9;
		rightAdjust = 1;
		autoLoopCounter = 0;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	// 100 loops ~ 2 seconds
	public void autonomousPeriodic() {
		autonomousClose(0,10);
		autonomousRight(10,30);
		autonomousForward(30,80);
		autonomousOpen(80,90);
		autonomousBackward(90,120);
		autonomousLeft(120,140);
		autonomousForward(140,170);
		autonomousRight(170,190);
		autonomousBackward(190,240);
		
		autoLoopCounter++;
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {
		compressor.setClosedLoopControl(true);
		//	leftMotorSpd = XboxController.clamp(-1.0,1.0, drive.getRightTrigger() - drive.getLeftTrigger() + drive.getLeftX());
		//rightMotorSpd = XboxController.clamp(-1.0,1.0, drive.getRightTrigger() - drive.getLeftTrigger() - drive.getLeftX());
		// Double leftX = drive.getLeftX();
		 //leftX = drive.deadBand(drive.getLeftX(), .15);


		while (isOperatorControl() && isEnabled()) {


			if (drive.getButton(6)) {
				alphaPiston.set(DoubleSolenoid.Value.kForward);
			} else if (drive.getButton(5)) {
				alphaPiston.set(DoubleSolenoid.Value.kReverse);
			} else {
				alphaPiston.set(DoubleSolenoid.Value.kOff);

			}
		//myRobot.setLeftRightMotorOutputs(drive.getRightTrigger() + drive.getLeftTrigger() + drive.getLeftX(), //leftX -.039,
			//drive.getRightTrigger()-drive.getLeftTrigger() - drive.getLeftX());
			myRobot.setLeftRightMotorOutputs(-drive.getLeftY(), -drive.getRightY());


			//do not know which causes which yet
		//	if (drive.getRightTrigger() > drive.getLeftTrigger()){ 
				winchSystem.set(drive.getLeftTrigger());
			 if (drive.getLeftTrigger() < drive.getRightTrigger()) {
				winchSystem.set(-drive.getRightTrigger());
			}
			//winchSystem.set(drive.getRightY());


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
