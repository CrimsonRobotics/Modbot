
package org.usfirst.frc.team2526.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	CANTalon frontleft;
	CANTalon frontRight;
	CANTalon backLeft;
	CANTalon backRight;
	RobotDrive myDrive;
	Joystick left;
	Joystick right;
	
   
    public void robotInit() {
    	frontleft = new CANTalon(1);
    	frontRight = new CANTalon(2);
    	backLeft = new CANTalon(8);
    	backRight = new CANTalon(9);
    	left = new Joystick(0);
    	right = new Joystick(1);
    	//CAN id's might not match up with current code(may be incorrect)
    	
    	
    }
    
	
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {
    	
    	}
    

    
    public void teleopPeriodic() {
    	while (isOperatorControl() && isEnabled()) {
    		myDrive.tankDrive(-left.getX(),  -right.getX());;
    		Timer.delay(0.01);
    	}
    }
    
    
    public void testPeriodic() {
    
    }
    
}
