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
		//close jaws
		if (autoLoopCounter<10) {
			alphaPiston.set(DoubleSolenoid.Value.kForward);
		}		
		
		//rotate
		if ((autoLoopCounter < 50)&&(autoLoopCounter>20)) {
			myRobot.setLeftRightMotorOutputs(-.5, .5);
		}

		if ((autoLoopCounter < 150)&&(autoLoopCounter>50)) 
			// Check if we've completed 100 loops
			// (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); // drive backwards half speed
		} 
		
		if (autoLoopCounter>150) {
			alphaPiston.set(DoubleSolenoid.Value.kReverse);
			myRobot.drive(0, 0);
		}
		
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
