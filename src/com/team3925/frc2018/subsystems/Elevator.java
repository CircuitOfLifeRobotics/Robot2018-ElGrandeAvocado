package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	
	private final TalonSRX elevatorMaster = RobotMap.ElevatorMap.MASTER;

	private static final int ENC_TICKS_PER_REV = 4096;
	private static final double K_ELEVATOR = 3.769;

	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final double kF = 1;

	private static final int MOTION_MAGIC_ACCELERATION = 4096;
	private static final int MOTION_MAGIC_CRUISE_VELOCITY = 4096;
	
	private static final int MAX_AMP_DRAW = 30;

	private static Elevator instance;

	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
		elevatorMaster.setInverted(false);
		elevatorMaster.setSensorPhase(false);
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID_X,
				Constants.TIMEOUT_MS);

		elevatorMaster.selectProfileSlot(0, Constants.PID_ID_X);
		elevatorMaster.config_kP(0, kP, Constants.TIMEOUT_MS);
		elevatorMaster.config_kI(0, kI, Constants.TIMEOUT_MS);
		elevatorMaster.config_kD(0, kD, Constants.TIMEOUT_MS);
		elevatorMaster.config_kF(0, kF, Constants.TIMEOUT_MS);

		elevatorMaster.configMotionAcceleration(MOTION_MAGIC_ACCELERATION, Constants.TIMEOUT_MS);
		elevatorMaster.configMotionCruiseVelocity(MOTION_MAGIC_CRUISE_VELOCITY, Constants.TIMEOUT_MS);

		elevatorMaster.overrideLimitSwitchesEnable(true);
		elevatorMaster.configContinuousCurrentLimit(MAX_AMP_DRAW, Constants.TIMEOUT_MS);
		elevatorMaster.configPeakCurrentLimit(0, Constants.TIMEOUT_MS);
	}

	
	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, 0.8 * speed);
	}

	private void setPosition(double revolutions) {
		elevatorMaster.set(ControlMode.MotionMagic, (revolutions * ENC_TICKS_PER_REV));
		System.out.println(revolutions * ENC_TICKS_PER_REV);
	}

	public void setHeight(double height) {
		setPosition(height / K_ELEVATOR);
	}

	public void zero() {
		elevatorMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
	}

	public boolean getLimitSwitch() {
		return elevatorMaster.getSensorCollection().isRevLimitSwitchClosed();
	}

	public void log() {
		SmartDashboard.putNumber("Elevator Current 1", elevatorMaster.getOutputCurrent());
		SmartDashboard.putNumber("Elevator Current 2", RobotMap.ElevatorMap.SLAVE.getOutputCurrent());
	}

	@Override
	protected void initDefaultCommand() {
	}

}
