
package org.usfirst.frc.team2526.robot;

import com.ctre.*;
//import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
//import edu.wpi.first.wpilibj.CANTalon.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.*;

public class Robot extends IterativeRobot {
	//Encoder sampleEncoder;
	CANTalon frontLeft;
	CANTalon frontRight;
	CANTalon backLeft;
	CANTalon backRight;
	RobotDrive myDrive;
	Joystick left;
	Joystick right;
	Button leftButton;
	double i = 2.0;
	double x = 1.0;
	Boolean hasCommandBeenIssued = false;
	
	double wheelRotations = (4 * Math.PI);
	double distanceInFeet = 5;
	double revolutions = distanceInFeet * 12 / wheelRotations;
	
	public double encoderDis(Encoder encoder){
		i += 1;
		double distance = encoder.getDistance();

		//double distance = encoder.pidGet();

		//int myDistance = encoder.get();
		double reading = encoder.getRaw();
		boolean direction = encoder.getDirection();

		SmartDashboard.putNumber("Encoder Distance: ", distance);
		SmartDashboard.putNumber("Encoder Raw: ", reading);
		SmartDashboard.putNumber("Encoder Counter: ", i);
		SmartDashboard.putBoolean("Direction: ", direction);

		return distance;
	}

	public void robotInit() {
		//sampleEncoder = new Encoder(8, 15, false, Encoder.EncodingType.k4X);
		frontLeft = new CANTalon(2);
		frontRight = new CANTalon(1);
		backLeft = new CANTalon(8);
		backRight = new CANTalon(9); //encoder attached
		left = new Joystick(0);
		right = new Joystick(1);
		leftButton = new JoystickButton(left,2);
		myDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

	}


	public void autonomousInit() {
		hasCommandBeenIssued = false;
		
		//setup left
		backLeft.reset();
		
		backLeft.setPosition(0);
		frontLeft.setPosition(0);

		backLeft.changeControlMode(CANTalon.TalonControlMode.Position);
		backLeft.setPID( 1.0, 0.0, 0.0); //Set the PID constants (p, i, d)


		backLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		frontLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontLeft.set(backLeft.getDeviceID());
		
		backLeft.reverseSensor(false);
		
		backLeft.configEncoderCodesPerRev(100);
		frontLeft.configEncoderCodesPerRev(100);

		backLeft.enableControl(); //Enable PID control on the talon
		frontLeft.enableControl();

		
		//setup right
		backRight.reset();
		
		backRight.setPosition(0);
		frontRight.setPosition(0);

		backRight.changeControlMode(CANTalon.TalonControlMode.Position);
		backRight.setPID( 1.0, 0.0, 0.0); //Set the PID constants (p, i, d)


		backRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		frontRight.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontRight.set(backRight.getDeviceID());
		
		backRight.reverseSensor(false);
		
		backRight.configEncoderCodesPerRev(100);
		frontRight.configEncoderCodesPerRev(100);

		backRight.enableControl(); //Enable PID control on the talon
		frontRight.enableControl();
		
		x += 1;
		SmartDashboard.putString("Test Label:", x + " autoInit" );

	}

	//public void autonomousPeriodic() {
	public void autonomousPeriodic() {
		String controlMode = backLeft.getControlMode().toString();
		String pid = backLeft.getP() + ":" + backLeft.getI() + ":" + backLeft.getD();

		SmartDashboard.putString("Auto Periodic: ", i + " pid: " + pid + " " + controlMode );

		i += 1;
		
		backLeft.enableControl();
		backRight.enableControl();
		
		if (!hasCommandBeenIssued) {

			//backLeft.set(5000);

			backLeft.setPosition(0);
			backRight.setPosition(0);
			backLeft.setSetpoint(revolutions * 100);
			backRight.setSetpoint(revolutions * 100);
			//backLeft.set(500);
			//frontLeft.set(5000);
			
			hasCommandBeenIssued = true;

		}

		/*
  	while(isAutonomous() && isEnabled() && backRight.getEncPosition() < 5000) {
  		myDrive.arcadeDrive(1, 0);
  	}
		 */

		SmartDashboard.putNumber("Backleft getClosedLoopError 1: ", backLeft.getClosedLoopError());
		SmartDashboard.putNumber("Backleft Velocity: " , backLeft.getCloseLoopRampRate());
		SmartDashboard.putNumber("Backleft getPosition: " , backLeft.getPosition());
		SmartDashboard.putNumber("FrontLeft getClosedLoopError 1: ", backRight.getClosedLoopError());
		SmartDashboard.putNumber("FrontLeft Velocity: " , frontLeft.getEncVelocity());
		SmartDashboard.putNumber("FrontLeft getPosition: " , frontLeft.getPosition());
		
		Timer.delay(0.01);

	}


	public void teleopInit() {
		backLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus); //Change control mode of talon, default is PercentVbus (-1.0 to 1.0)
		backLeft.setPID(0.0, 0.0, 0.0); //Set the PID constants (p, i, d)
		frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		frontLeft.setPID(0.0, 0.0, 0.0);
		backLeft.enableControl(); 
		frontLeft.enableControl();
	}
	public void teleopPeriodic() {
		while (isOperatorControl() && isEnabled()) {
			//myDrive.tankDrive(-left.getX(), -right.getX());
			myDrive.arcadeDrive(left.getY(), right.getX());

			Timer.delay(0.001);

			//if (leftButton.get()) {
			//	backLeft.set(5000);
			//}

			SmartDashboard.putNumber("Backleft Velocity: " , backLeft.getEncVelocity());
			SmartDashboard.putNumber("Backleft Position 1: ", backLeft.getEncPosition());
			SmartDashboard.putNumber("Backright Velocity: " , backRight.getEncVelocity());
			SmartDashboard.putNumber("Backright Position 1: ", backRight.getEncPosition());

		}
	}


	public void testPeriodic() {

	}

}

