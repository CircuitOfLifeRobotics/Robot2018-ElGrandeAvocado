package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Robot;
import com.team3925.frc2018.maps.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem{
	
	private final TalonSRX liftMaster = (Robot.PRACTICE_BOT) ? null:
										RobotMap.LiftMap.MASTER;
	
	private static Lift instance;
	public static Lift getInstance() {
		if (instance == null)
			instance = new Lift();
		return instance;
	}
	
	public Lift() {
		if (!Robot.PRACTICE_BOT) {
			//init code here
		}else {
			//init code here
		}
	}
	
	public void setRaw(double speed) {
		if(!Robot.PRACTICE_BOT) {
			liftMaster.set(ControlMode.PercentOutput, speed);
		}else {
			System.out.println("WRONG BOT! liftMaster @ Lift not available!");
		}
	}

	@Override
	protected void initDefaultCommand() {}
	

}
