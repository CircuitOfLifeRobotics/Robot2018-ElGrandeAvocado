package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	private final TalonSRX elevatorMaster = RobotMap.ElevatorMap.MASTER;

	private static Elevator instance;

	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
	}

	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, speed);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
