package org.usfirst.frc.team5249.robot;

//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CameraServer;
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
	public static CameraServer cam0;
	
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
	// in ticks
	int turn90Degrees;
	
	int a = 20;//close
	int b = a+38;//right
	int c = b+30;//up
	int d = c+75;//forward
	int e = d+25;//open
	int f = e+0;//left
	int g = f+0;//forward
	int h = g+0;//right
	int i = h+0;//backward
	int j = i+0;//lower
	int k = j+0;//stop
	int l = k+0;
	

	autonomousRobot aDrive;
	final static boolean start = true;
	final static boolean end = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		cam0 = CameraServer.getInstance();
		cam0.setQuality(10);
		cam0.setSize(100);
		cam0.startAutomaticCapture();
		compressor.start();
		winchSystem = new Jaguar(4);
		frontLeft = new InvertableTalon(0, true);
		rearLeft = new InvertableTalon(1, true);
		frontRight = new InvertableTalon(2, true);
		rearRight = new InvertableTalon(3, true);
		myRobot = new RobotDrive(rearLeft, frontLeft, rearRight, frontRight);
		drive = new XboxController(0);

	}

	// Ordered actions ---------------------------------------------------------
	public void orderedRight(int lengthOfAction) {
		autonomousRight(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedLeft(int lengthOfAction) {
		autonomousLeft(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedForward(int lengthOfAction) {
		autonomousForward(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedBackward(int lengthOfAction) {
		autonomousBackward(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedOpen(int lengthOfAction) {
		autonomousOpen(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedClose(int lengthOfAction) {
		autonomousClose(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedRaise(int lengthOfAction) {
		autonomousRaise(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedLower(int lengthOfAction) {
		autonomousLower(aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	public void orderedWinchStop(int lengthOfAction) {
		autonomousWinchStop(
				aDrive.autonomousOrderedTime(lengthOfAction, start),
				aDrive.autonomousOrderedTime(lengthOfAction, end));
	}

	// More raw actions---------------------------------------------------------
	public void autonomousRight(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			myRobot.setLeftRightMotorOutputs(.5 * leftAdjust, -.5 * rightAdjust);
		}
	}

	public void autonomousLeft(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			myRobot.setLeftRightMotorOutputs(-.5 * leftAdjust, .5 * rightAdjust);
		}
	}

	public void autonomousForward(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			myRobot.setLeftRightMotorOutputs(.5 * leftAdjust, .5
					* rightAdjust);
		}
	}

	public void autonomousBackward(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			myRobot.setLeftRightMotorOutputs(-.5 * leftAdjust, -.5 * rightAdjust);
		}
	}

	public void autonomousStop(int endTime) {
		if (autoLoopCounter > 150) {
			alphaPiston.set(DoubleSolenoid.Value.kReverse);
			myRobot.drive(0, 0);
			winchSystem.set(0);
		}
	}

	public void autonomousOpen(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			alphaPiston.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void autonomousClose(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			alphaPiston.set(DoubleSolenoid.Value.kForward);
		}
	}

	public void autonomousRaise(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			winchSystem.set(.5);
		}
	}

	public void autonomousLower(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			winchSystem.set(-.5);
		}
	}

	public void autonomousWinchStop(int startTime, int endTime) {
		if ((autoLoopCounter < endTime) && (autoLoopCounter > startTime)) {
			winchSystem.set(0);
		}
	}

	// 50 ticks a second (measured in ticks)
	public void autonomousNoStacking() {
		autonomousClose(0, 10);
		autonomousRight(10, 30);
		autonomousForward(30, 80);
		autonomousOpen(80, 90);
		autonomousBackward(90, 120);
		autonomousLeft(120, 140);
		autonomousForward(140, 170);
		autonomousRight(170, 190);
		autonomousBackward(190, 240);
		autonomousStop(240);
	}

	// preset of no stacking please adjust values when testing
	public void orderedNoStack() {
		orderedClose(10);
		orderedRight(turn90Degrees);
		orderedForward(50);
		orderedOpen(10);
		orderedBackward(50);
		orderedRight(turn90Degrees);
		orderedForward(50);
		orderedLeft(turn90Degrees);
		orderedForward(70);
	}

	// preset of stacking one please adjust values when testing
	public void orderedStackOne() {
		orderedClose(10);
		orderedRight(turn90Degrees);
		orderedRaise(40);
		orderedForward(50);
		orderedOpen(10);
		orderedLower(40);
		orderedBackward(50);
		orderedRight(turn90Degrees);
		orderedForward(50);
		orderedLeft(turn90Degrees);
		orderedForward(70);
	}

	// must test on field what value to lift to
	public void autonomousStackOne() {
		autonomousClose(0, a);

		// Adjust to make this a 90 degree turn
		autonomousRight(a, b);

		// I don't know how high to raise
		autonomousRaise(b, c);

		// Adjust times
		autonomousForward(c, d);
		autonomousOpen(d, e);
		autonomousBackward(e, f);
		autonomousLeft(f, g);
		autonomousForward(g, h);
		autonomousRight(h, i);
		autonomousBackward(i, j);

		// Lowers all the way to original position
		autonomousLower(j, k);

		autonomousStop(k);
	}

	/**
	 *
	 * This function is run once each time the robot enters autonomous mode
	 */
	public void autonomousInit() {
		// ******************Adjust these values so that they actually work*****
		turn90Degrees = 30;
		leftAdjust = .9;
		rightAdjust = 1;

		// Do not adjust these values
		autoLoopCounter = 0;
		aDrive = new autonomousRobot();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	// 100 loops ~ 2 seconds
	public void autonomousPeriodic() {
		//autonomousStackOne();
		autonomousBackward(0,55);
		autonomousStop(60);
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
		/*
		 * leftMotorSpd = XboxController.clamp(-1.0,1.0, drive.getRightTrigger()
		 * - drive.getLeftTrigger() + drive.getLeftX()); rightMotorSpd =
		 * XboxController.clamp(-1.0,1.0, drive.getRightTrigger() -
		 * drive.getLeftTrigger() - drive.getLeftX()); Double leftX =
		 * drive.getLeftX(); leftX = drive.deadBand(drive.getLeftX(), .15);
		 */

		while (isOperatorControl() && isEnabled()) {

			if (drive.getButton(5)) {
				alphaPiston.set(DoubleSolenoid.Value.kForward);
			} else if (drive.getButton(6)) {
				alphaPiston.set(DoubleSolenoid.Value.kReverse);
			} else {
				alphaPiston.set(DoubleSolenoid.Value.kOff);

			}
			// myRobot.setLeftRightMotorOutputs(drive.getRightTrigger() +
			// drive.getLeftTrigger() + drive.getLeftX(), //leftX -.039,
			// drive.getRightTrigger()-drive.getLeftTrigger() -
			// drive.getLeftX());
			myRobot.setLeftRightMotorOutputs(-drive.getLeftY(),
					-drive.getRightY());

			// do not know which causes which yet
			// if (drive.getRightTrigger() > drive.getLeftTrigger()){
			winchSystem.set(drive.getLeftTrigger());
			if (drive.getLeftTrigger() < drive.getRightTrigger()) {
				winchSystem.set(-drive.getRightTrigger());
			}
			// winchSystem.set(drive.getRightY());

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
