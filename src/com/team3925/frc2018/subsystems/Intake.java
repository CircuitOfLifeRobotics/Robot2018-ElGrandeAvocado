package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Robot;
import com.team3925.frc2018.maps.PracticeMap;
import com.team3925.frc2018.maps.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem{
	
	private final TalonSRX leftIntake  = (Robot.PRACTICE_BOT) ? PracticeMap.IntakeMap.LEFT_INTAKE
															  : RobotMap.IntakeMap.LEFT_INTAKE;
	
	private final TalonSRX rightIntake = (Robot.PRACTICE_BOT) ? PracticeMap.IntakeMap.RIGHT_INTAKE
															  : RobotMap.IntakeMap.RIGHT_INTAKE;
	
	private final DoubleSolenoid grabSolenoid = (Robot.PRACTICE_BOT) ? PracticeMap.IntakeMap.GRAB_SOLENOID
																	 : RobotMap.IntakeMap.GRAB_SOLENOID;
	
	private final DoubleSolenoid holdSolenoid = (Robot.PRACTICE_BOT) ? null
																	 : RobotMap.IntakeMap.HOLD_SOLENOID;
	
	private final DoubleSolenoid liftSolenoid = (Robot.PRACTICE_BOT) ? null
																	 : RobotMap.IntakeMap.LIFT_SOLENOID;
	
	private static Intake instance;
	public static Intake getInstance() {
		if (instance == null)
			instance = new Intake();
		return instance;
	}
	
	private Intake() {
		if (!Robot.PRACTICE_BOT) {
			//init code here
		} else {
			//init code here
		}
	}
	
	public void setIntakeRollers(double speed) {
		leftIntake.set(ControlMode.PercentOutput, speed);
		rightIntake.set(ControlMode.PercentOutput, speed);
	}
	
	public void setGrabber(boolean grab) {
		grabSolenoid.set((grab) ? Value.kForward : Value.kReverse);
	}
	
	public void setHold(boolean hold) {
		if(!Robot.PRACTICE_BOT) {
			holdSolenoid.set((hold) ? Value.kForward : Value.kReverse);
		}else {
			System.out.println("WRONG BOT! holdSolenoid @ Intake not available!");
		}
	}
	
	public void setLift(boolean down) {
		if(!Robot.PRACTICE_BOT) {
			liftSolenoid.set((down) ? Value.kForward : Value.kReverse);
		}else {
			System.out.println("WRONG BOT! liftSolenoid @ Intake not available!");
		}
	}

	@Override
	protected void initDefaultCommand() { }

}
