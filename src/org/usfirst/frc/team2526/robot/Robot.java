
package org.usfirst.frc.team2526.robot;

import com.ctre.*;
//import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
//import edu.wpi.first.wpilibj.CANTalon.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DriverStation;
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
	double distanceInFeet = 20;
	double revolutions = distanceInFeet * 12 / wheelRotations;
	int ticksPerRevolution = 360;
	
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
		backLeft = new CANTalon(8); //encoder attached
		backRight = new CANTalon(9); //encoder attached
		left = new Joystick(0);
		right = new Joystick(1);
		leftButton = new JoystickButton(left,2);
		//myDrive = new RobotDrive(backLeft, frontLeft, backRight, frontRight);
		myDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

	}

	public void autonomousInit() {
		//setup right
		//backRight.reset();

		backRight.setPosition(0);

		backRight.changeControlMode(CANTalon.TalonControlMode.Speed);
		//backRight.setAllowableClosedLoopErr(100);
		//backRight.setPID( 5.0, 0.02, 2.0); //Set the PID constants (p, i, d)
	
		//backRight.setPID(.0, 0.00, 0.0, 0.5, 50, 100, 0);
		
		SmartDashboard.putString("SensorType backLeft: ", getSensorType(backLeft));
		SmartDashboard.putString("SensorType backRight: ", getSensorType(backRight));
		SmartDashboard.putString("SensorType frontLeft: ", getSensorType(frontLeft));
		SmartDashboard.putString("SensorType frontRight: ", getSensorType(frontRight));
		
		SmartDashboard.putString("AnalogEncoder: " , backRight.isSensorPresent(CANTalon.FeedbackDevice.AnalogEncoder).toString());
		SmartDashboard.putString("AnalogPot: " , backRight.isSensorPresent(CANTalon.FeedbackDevice.AnalogPot).toString());
		SmartDashboard.putString("AnalogEncoder: " , backRight.isSensorPresent(CANTalon.FeedbackDevice.AnalogEncoder).toString());
		SmartDashboard.putString("AnalogEncoder: " , backRight.isSensorPresent(CANTalon.FeedbackDevice.AnalogEncoder).toString());

		
		backRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		backRight.setSafetyEnabled(false);
		//backRight.reverseSensor(false);
		//backLeft.reverseSensor(true);

		backRight.configEncoderCodesPerRev(ticksPerRevolution);
		
		x += 1;
		SmartDashboard.putString("Test Label:", x + " autoInit" );
	}

	public void autonomousInit2() {
		hasCommandBeenIssued = false;
		
		//clear sticky faults
		backLeft.clearStickyFaults();
		frontLeft.clearStickyFaults();
		backRight.clearStickyFaults();
		frontRight.clearStickyFaults();
		
		//  Clear accumlated values
		backLeft.clearIAccum();
		frontLeft.clearIAccum();
		backRight.clearIAccum();
		frontRight.clearIAccum();
		
		//setup left
		backLeft.reset();
		
		backLeft.setPosition(0);
		frontLeft.setPosition(0);

		backLeft.changeControlMode(CANTalon.TalonControlMode.Position);
		backLeft.setPID( 5.0, 0.02, 2.0); //Set the PID constants (p, i, d)


		backLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		frontLeft.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontLeft.set(backLeft.getDeviceID());
		frontLeft.reverseOutput(true);
		
		//backLeft.reverseSensor(false);
		
		backLeft.configEncoderCodesPerRev(ticksPerRevolution);
		frontLeft.configEncoderCodesPerRev(ticksPerRevolution);

		//backLeft.enableControl(); //Enable PID control on the talon
		//frontLeft.enableControl();

		
		//setup right
		backRight.reset();
		
		backRight.setPosition(0);
		frontRight.setPosition(0);

		backRight.changeControlMode(CANTalon.TalonControlMode.Position);
		//backRight.setAllowableClosedLoopErr(100);
		backRight.setPID( 5.0, 0.02, 2.0); //Set the PID constants (p, i, d)


		backRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);

		frontRight.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontRight.set(backRight.getDeviceID());
		
		backRight.reverseSensor(false);
		backLeft.reverseSensor(true);
		
		backRight.configEncoderCodesPerRev(ticksPerRevolution);
		frontRight.configEncoderCodesPerRev(ticksPerRevolution);

		//backRight.enableControl(); //Enable PID control on the talon
		//frontRight.enableControl();
		
		x += 1;
		SmartDashboard.putString("Test Label:", x + " autoInit" );

	}

	public void autonomousPeriodic() {
		String controlMode = backLeft.getControlMode().toString();
		String pid = backLeft.getP() + ":" + backLeft.getI() + ":" + backLeft.getD();

		SmartDashboard.putString("Auto Periodic: ", i + " pid: " + pid + " " + controlMode );

		i += 1;

		//backRight.setProfile(0);
		//backLeft.enable();
		backRight.enableControl();

		backRight.setSafetyEnabled(false);

		backRight.set(200);

		SmartDashboard.putNumber("Ticks: ", revolutions * ticksPerRevolution);

		//hasCommandBeenIssued = true;

		//SmartDashboard.putNumber("backRight getClosedLoopError 1: ", backRight.getClosedLoopError());
		//SmartDashboard.putNumber("backRight getCloseLoopRampRate 1: ", backRight.getCloseLoopRampRate());
		SmartDashboard.putString("feedbackDeviceStatus: " , backRight.isSensorPresent(CANTalon.FeedbackDevice.AnalogEncoder).toString());
		
		//System.out.print("StickyFaultOverTemp Device ID: " + backLeft.getEncVelocity());
		
		//Timer.delay(0.001);
	}
	
	
	public void autonomousPeriodic2() {
		String controlMode = backLeft.getControlMode().toString();
		String pid = backLeft.getP() + ":" + backLeft.getI() + ":" + backLeft.getD();

		SmartDashboard.putString("Auto Periodic: ", i + " pid: " + pid + " " + controlMode );

		i += 1;
		
		backLeft.enableControl();
		backRight.enableControl();
		
		//backLeft.enable();
		//backRight.enable();
		
		if (!hasCommandBeenIssued) {
			
			
			
			//backLeft.set(5000);
			//backLeft.setPosition(0);
			//backRight.setPosition(0);
			backRight.changeControlMode(CANTalon.TalonControlMode.Position);
			backRight.setSetpoint(revolutions * ticksPerRevolution);
			backLeft.changeControlMode(CANTalon.TalonControlMode.Position);
			backLeft.setSetpoint(revolutions * ticksPerRevolution);
			//backLeft.set(revolutions * 360);
			//frontLeft.set(revolutions * 360);
			
			SmartDashboard.putNumber("Ticks: ", revolutions * ticksPerRevolution);
			
			hasCommandBeenIssued = true;

		}

		/*
  	while(isAutonomous() && isEnabled() && backRight.getEncPosition() < 5000) {
  		myDrive.arcadeDrive(1, 0);
  	}
		 */

		outputMotorEncoderData(backLeft,"backLeft");
		
		//check for any faults
		checkCANTalonFaults(backLeft);
		checkCANTalonFaults(backRight);
		checkCANTalonFaults(frontLeft);
		checkCANTalonFaults(frontRight);
		
		Timer.delay(0.01);
		
		//System.out.println("Foo");

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
			x += 1;
			//if (leftButton.get()) {
			//	backLeft.set(5000);
			//}
			
			SmartDashboard.putNumber("Teleop Counter 1: ", x);
			//SmartDashboard.putString("feedbackDeviceStatus: " , backRight.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder).toString());
		
		}
	}
	
	public void outputMotorEncoderData(CANTalon motor, String motorName) {
		
		SmartDashboard.putString("Motor: ", motorName);
		SmartDashboard.putNumber(motorName + " getEncVelocity: " , motor.getEncVelocity());
		SmartDashboard.putNumber(motorName + " getEncPosition: ", motor.getEncPosition());
		SmartDashboard.putNumber(motorName + " getPosition: ", motor.getPosition());
		SmartDashboard.putNumber(motorName + " getClosedLoopError: ", motor.getClosedLoopError());
		SmartDashboard.putNumber(motorName + " getError: ", motor.getError());
		SmartDashboard.putNumber(motorName + " getSetpoint: ", motor.getSetpoint());
		SmartDashboard.putNumber(motorName + " getSpeed: ", motor.getSpeed());
		SmartDashboard.putBoolean(motorName + " isSafetyEnabled: " , motor.isSafetyEnabled());
		SmartDashboard.putString(motorName + " P:I:D: ", motor.getP() + ":" + motor.getI() +  ":" + motor.getD());
		SmartDashboard.putNumber(motorName + " getF: ", motor.getF());
		SmartDashboard.putNumber(motorName + " getIZone: ", motor.getIZone());
		SmartDashboard.putNumber(motorName + " getCloseLoopRampRate: ", motor.getCloseLoopRampRate());
			
	}

	public void testPeriodic() {
		
	}

	/*
	public void configureTalons(CANTalon master, CANTalon follower) {
        // The turret has one Talon.
        //talon_ = new CANTalon(Constants.kTurretTalonId);
		master.enableBrakeMode(true);
		master.enableLimitSwitch(true, true);
		master.ConfigFwdLimitSwitchNormallyOpen(true);
		master.ConfigRevLimitSwitchNormallyOpen(true);
		master.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		master.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        if (talon_.isSensorPresent(
                CANTalon.FeedbackDevice.CtreMagEncoder_Relative) != CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            DriverStation.reportError("Could not detect turret encoder!", false);
        }

        talon_.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

        talon_.setPID(Constants.kTurretKp, Constants.kTurretKi, Constants.kTurretKd, Constants.kTurretKf,
                Constants.kTurretIZone, Constants.kTurretRampRate, 0);
        talon_.setProfile(0);
        talon_.reverseSensor(true);
        talon_.reverseOutput(false);

        // We use soft limits to make sure the turret doesn't try to spin too
        // far.
        talon_.enableForwardSoftLimit(true);
        talon_.enableReverseSoftLimit(true);
        talon_.setForwardSoftLimit(Constants.kSoftMaxTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
        talon_.setReverseSoftLimit(Constants.kSoftMinTurretAngle / (360.0 * Constants.kTurretRotationsPerTick));
    }
    */
	
	
	public void checkCANTalonFaults(CANTalon myMotor) {
		
		if(myMotor.getFaultOverTemp() != 0) {
			System.out.println("FaultOverTemp Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getFaultUnderVoltage() != 0) {
			System.out.println("FaultUnderVoltage Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getFaultForLim() != 0) {
			System.out.println("FaultForLim Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getFaultRevLim() != 0) {
			System.out.println("FaultRevLim Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getFaultForSoftLim() != 0) {
			System.out.println("FaultForSoftLim Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getFaultRevSoftLim() != 0) {
			System.out.println("FaultRevSoftLim Device ID: " + myMotor.getDeviceID());
		}
		
		// Sticky Faults

		if(myMotor.getStickyFaultOverTemp() != 0) {
			System.out.println("StickyFaultOverTemp Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getStickyFaultUnderVoltage() != 0) {
			System.out.println("StickyFaultUnderVoltage Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getStickyFaultForLim() != 0) {
			System.out.println("StickyFaultForLim Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getStickyFaultRevLim() != 0) {
			System.out.println("StickyFaultRevLim Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getStickyFaultForSoftLim() != 0) {
			System.out.println("StickyFaultForSoftLim Device ID: " + myMotor.getDeviceID());
		}
		if(myMotor.getStickyFaultRevSoftLim() != 0) {
			System.out.println("StickyFaultRevSoftLim Device ID: " + myMotor.getDeviceID());
		}		
	}
	
	public static String getSensorType(CANTalon motor) {
		
		String sensorType = "Unknown";
		
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.AnalogEncoder) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "AnalogEncoder";
        }
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.AnalogPot) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "AnalogPot";
        }
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "CtreMagEncoder_Absolute";
        }
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Relative) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "CtreMagEncoder_Relative";
        }
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.EncFalling) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "EncFalling";
        }
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.PulseWidth) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "PulseWidth";
        }
		if (motor.isSensorPresent(CANTalon.FeedbackDevice.QuadEncoder) == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent) {
            sensorType = "QuadEncoder";
        }
		
		return sensorType;
	}
}

