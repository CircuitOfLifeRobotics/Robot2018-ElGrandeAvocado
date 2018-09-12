package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {

	private final TalonSRX liftMaster = RobotMap.LiftMap.MASTER;

	private static Lift instance;

	public static Lift getInstance() {
		if (instance == null)
			instance = new Lift();
		return instance;
	}

	@Override
	protected void initDefaultCommand() {
	}

}
