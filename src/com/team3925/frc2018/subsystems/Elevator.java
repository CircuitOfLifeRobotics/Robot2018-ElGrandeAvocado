package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	private final TalonSRX elevatorMaster = RobotMap.ElevatorMap.MASTER;

	private static Elevator instance;
	private static final int ENC_TICKS_PER_REV = 4096;

	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.pidIDx, Constants.timeoutMs);
		
		elevatorMaster.selectProfileSlot(0, Constants.pidIDx);
		elevatorMaster.config_kP(0, 0, Constants.timeoutMs);
		elevatorMaster.config_kI(0, 0, Constants.timeoutMs);
		elevatorMaster.config_kD(0, 0, Constants.timeoutMs);
		elevatorMaster.config_kF(0, 0, Constants.timeoutMs);
		
		elevatorMaster.configMotionAcceleration(0, Constants.timeoutMs);
		elevatorMaster.configMotionCruiseVelocity(0, Constants.timeoutMs);
	}

	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, speed);
	}
	private void setPosition(double revolutions) {
		elevatorMaster.set(ControlMode.MotionMagic, (revolutions * ENC_TICKS_PER_REV));
	}
	
	public void zero() {
		elevatorMaster.setSelectedSensorPosition(0, Constants.pidIDx, Constants.timeoutMs);
	}
	

	@Override
	protected void initDefaultCommand() {
	}

}
