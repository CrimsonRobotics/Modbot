
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
	Encoder sampleEncoder;
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
  	backRight = new CANTalon(9);
  	left = new Joystick(0);
  	right = new Joystick(1);
  	leftButton = new JoystickButton(left,2);
  	myDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
  	
  	//set the PID settings
  	//backLeft.setPID(1, 0, 0);
  	//backLeft.setPosition(0);
  	//backRight.setPID(1, 0, 0);
  	//backRight.setPosition(0);
  	
  	
  	//backRight.setVoltageRampRate(2);
  	//backLeft.setVoltageRampRate(2);

  	
  	
  }
  
	
  public void autonomousInit() {
  	
  	backLeft.setPosition(0);
  	frontLeft.setPosition(0);
  	
  	//backLeft.reset(); //resets position
  	frontLeft.changeControlMode(CANTalon.TalonControlMode.Position);
  	//backLeft.changeControlMode(TalonControlMode.Position);
  	//talon.changeControlMode(ControlMode.Position); //Change control mode of talon, default is PercentVbus (-1.0 to 1.0)
  	frontLeft.setPID(1, 0, 0.0); //Set the PID constants (p, i, d)
  	//backLeft.setPID(1.0, 0.0, 0.0, 0, 360, 0.025, 0);
  	
  	frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
  	//talon.setFeedbackDevice(FeedbackDevice.QuadEncoder); //Set the feedback device that is hooked up to the talon
  	
  	//frontLeft.changeControlMode(TalonControlMode.Position);
  	//frontLeft.setPID(1, 0, 0.0); //Set the PID constants (p, i, d)
  	//frontLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
  	
  	backLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
  	frontLeft.reverseSensor(true);
  	//backLeft.reverseOutput(false);
  	backLeft.set(frontLeft.getDeviceID());
  	
  	frontLeft.enableControl(); //Enable PID control on the talon
  	backLeft.enableControl();

		//backLeft.setPosition(1000);
		x += 1;
		SmartDashboard.putString("Test Label:", x + " autoInit" );
		
  	//backLeft.setSetpoint(1000); //position
  	
  	//backLeft.set(1000); 
  	//frontLeft.set(5000);
  	
  }
  
  //public void autonomousPeriodic() {
  public void autonomousPeriodic() {
  	String controlMode = frontLeft.getControlMode().toString();
  	String pid = frontLeft.getP() + ":" + frontLeft.getI() + ":" + frontLeft.getD();
  	
  	SmartDashboard.putString("Auto Periodic: ", i + " pid: " + pid + " " + controlMode );
  	
  	i += 1;
  	
  	//SmartDashboard.putNumber("Position: ", backLeft.getEncPosition());
  	if (!hasCommandBeenIssued) {
  		
  		//backLeft.set(1);
  		
  		//frontLeft.set(5000);
  		frontLeft.set(5000);
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
		SmartDashboard.putNumber("FrontLeft getClosedLoopError 1: ", frontLeft.getClosedLoopError());
		SmartDashboard.putNumber("FrontLeft Velocity: " , frontLeft.getEncVelocity());
  	SmartDashboard.putNumber("FrontLeft getPosition: " , frontLeft.getPosition());
		
		//frontLeft.set(5000);
  	
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

