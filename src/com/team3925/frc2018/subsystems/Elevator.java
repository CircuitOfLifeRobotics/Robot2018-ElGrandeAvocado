package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	private final TalonSRX elevatorMaster = RobotMap.ElevatorMap.MASTER;

	private static final int ENC_TICKS_PER_REV = 4096;
	
	private static final double K_ELEVATOR= 0;

	private static Elevator instance;
	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID_X,
				Constants.TIMEOUT_MS);

		elevatorMaster.selectProfileSlot(0, Constants.PID_ID_X);
		elevatorMaster.config_kP(0, 0, Constants.TIMEOUT_MS);
		elevatorMaster.config_kI(0, 0, Constants.TIMEOUT_MS);
		elevatorMaster.config_kD(0, 0, Constants.TIMEOUT_MS);
		elevatorMaster.config_kF(0, 0, Constants.TIMEOUT_MS);

		elevatorMaster.configMotionAcceleration(0, Constants.TIMEOUT_MS);
		elevatorMaster.configMotionCruiseVelocity(0, Constants.TIMEOUT_MS);
		
		elevatorMaster.overrideLimitSwitchesEnable(true);
	}

	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, speed);
	}

	private void setPosition(double revolutions) {
		elevatorMaster.set(ControlMode.MotionMagic, (revolutions * ENC_TICKS_PER_REV));
	}
	
	public void setHeight(double height) {
		setPosition(height * K_ELEVATOR);
	}

	public void zero() {
		elevatorMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
	}
	
	public boolean getLimitSwitch() {
		return elevatorMaster.getSensorCollection().isRevLimitSwitchClosed();
	}

	@Override
	protected void initDefaultCommand() {
	}

}
