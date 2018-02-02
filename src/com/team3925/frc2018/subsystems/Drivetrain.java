package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Robot;
import com.team3925.frc2018.maps.PracticeMap;
import com.team3925.frc2018.maps.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem{
	
	private final TalonSRX leftMaster  = (Robot.PRACTICE_BOT) ? PracticeMap.DrivetrainMap.LEFT_MASTER :
																RobotMap.DrivetrainMap.LEFT_MASTER;
	
	private final TalonSRX rightMaster = (Robot.PRACTICE_BOT) ? PracticeMap.DrivetrainMap.RIGHT_MASTER :
																RobotMap.DrivetrainMap.RIGHT_MASTER;
	
	private final DoubleSolenoid shiftSolenoid = (Robot.PRACTICE_BOT) ? null:
										    	   RobotMap.DrivetrainMap.SHIFT_SOLENOID;
	
	public static Drivetrain instance;
	public static Drivetrain getInstance() {
		if (instance == null)
			instance = new Drivetrain();
		return instance;
	}
	
	private Drivetrain() {
		if (!Robot.PRACTICE_BOT) {
			//put init code here
		}else {
			//put init code here
		}
	}

	public void setRaw(double l, double r) {
		leftMaster .set(ControlMode.PercentOutput, l);
		rightMaster.set(ControlMode.PercentOutput, r);
	}
	
	public void setShifter(boolean isHigh) {
		if(!Robot.PRACTICE_BOT) {
			shiftSolenoid.set((isHigh) ? Value.kForward : Value.kReverse);
		}else {
			System.out.println("WRONG BOT! shiftSolenoid @ Drivetrain not available!");
		}
	}
	
	@Deprecated
	public TalonSRX getLeftMaster() { return leftMaster;  }
	@Deprecated
	public TalonSRX getRightMaster(){ return rightMaster; }
	
	
	@Override
	protected void initDefaultCommand() { }

}
