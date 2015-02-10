package org.usfirst.frc.team5249.robot;

//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
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
	Joystick leftStick = new Joystick(1);
	// Joystick rightStick = new Joystick(0);
	InvertableTalon frontLeft;
	InvertableTalon rearLeft;
	InvertableTalon frontRight;
	InvertableTalon rearRight;
	RobotDrive myRobot;
	XboxController drive;
	// XboxController operator;
	Solenoid solenoidExtend = new Solenoid (1,0);
	Solenoid solenoidRetract = new Solenoid (1);
	Compressor compressor = new Compressor(0);
	
	private double leftMotorSpeed;
	private double rightMotorSpeed;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		compressor.start();
		frontLeft = new InvertableTalon(0, true);
		rearLeft = new InvertableTalon(1, true);
		frontRight = new InvertableTalon(2, true);
		rearRight = new InvertableTalon(3, true);
		myRobot = new RobotDrive(rearLeft, frontLeft, rearRight, frontRight);
		drive = new XboxController(0);
		// operator = new XboxController(1);

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
		if (autoLoopCounter < 100) // Check if we've completed 100 loops
									// (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); // drive forwards half speed
			autoLoopCounter++;
		} else {
			myRobot.drive(0.0, 0.0); // stop robot
		}
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

	/*
	 * rt = forward, lt = reverse leftJoystick = turningfactor.
	 */
	public void teleopPeriodic() {


		while (isOperatorControl() && isEnabled()) {
			// myRobot.arcadeDrive(leftStick);
			compressor.setClosedLoopControl(true);
			if(drive.getRightTrigger()>drive.getLeftTrigger()){
				solenoidExtend.set(true);
				compressor.start();
			}else{
				solenoidExtend.set(false);
				compressor.stop();
			}
			myRobot.setLeftRightMotorOutputs(-drive.getLeftY(),
					-drive.getRightY());
			
			//myRobot.setLeftRightMotorOutputs(drive.getLeftTrigger(),
					//drive.getRightTrigger());
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
