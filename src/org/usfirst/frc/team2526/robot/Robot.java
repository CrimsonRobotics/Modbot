
package org.usfirst.frc.team2526.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	Encoder sampleEncoder;
	CANTalon frontLeft;
	CANTalon frontRight;
	CANTalon backLeft;
	CANTalon backRight;
	RobotDrive myDrive;
	Joystick left;
	Joystick right;
	
	public double encoderDis(Encoder encoder){
		double distance = encoder.getDistance();
		SmartDashboard.putNumber("Encoder Distance", distance);
		return distance;
	}
	
    public void robotInit() {
    	sampleEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    	frontLeft = new CANTalon(2);
    	frontRight = new CANTalon(1);
    	backLeft = new CANTalon(8);
    	backRight = new CANTalon(9);
    	left = new Joystick(0);
    	right = new Joystick(1);
    	myDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
    	
    	
    	
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {
    	
    	}
    
    
    public void teleopPeriodic() {
    	while (isOperatorControl() && isEnabled()) {
    		//myDrive.tankDrive(-left.getX(), -right.getX());
    		myDrive.arcadeDrive(left.getY(), right.getX());
    		encoderDis(sampleEncoder);
    		Timer.delay(0.01);
    		
    	}
    }
    
    
    public void testPeriodic() {
    
    }
    
}
