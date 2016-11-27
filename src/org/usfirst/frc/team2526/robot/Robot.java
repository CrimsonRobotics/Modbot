
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
	
	public void encoderDis(Encoder encoder){
		double distance = encoder.getDistance();
		SmartDashboard.putNumber("Encoder Distance", distance);
	}
	
    public void robotInit() {
    	//sampleEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    	frontLeft = new CANTalon(100);//Needs to obtain correct ID
    	frontRight = new CANTalon(100);//Need to obtain correct ID
    	backLeft = new CANTalon(2);
    	backRight = new CANTalon(100);//Need to obtain correct ID
    	left = new Joystick(0);
    	right = new Joystick(1);
    	myDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
    	//CAN id's might not match up with current code(may be incorrect)
    	
    	
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {
    	
    	}
    
    
    public void teleopPeriodic() {
    	while (isOperatorControl() && isEnabled()) {
    		//myDrive.tankDrive(-left.getX(), -right.getX());
    		myDrive.arcadeDrive(left.getX(), right.getY());
    		//encoderDis(sampleEncoder);
    		Timer.delay(0.01);
    		
    	}
    }
    
    
    public void testPeriodic() {
    
    }
    
}
