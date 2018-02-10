package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.maps.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

	private final TalonSRX liftMaster = RobotMap.LiftMap.MASTER;

	private static Lift instance;

	public static Lift getInstance() {
		if (instance == null)
			instance = new Lift();
		return instance;
	}

	public Lift() {
	}

	public void setRaw(double speed) {
		liftMaster.set(ControlMode.PercentOutput, speed);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
