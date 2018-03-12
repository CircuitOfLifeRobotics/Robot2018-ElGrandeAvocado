package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	public enum ElevatorState {
		TOP, SCALE_MAX, SCALE_MED, SCALE_LOW, SWITCH, BOTTOM, UNKNOWN, OTHER;
	}

	private final TalonSRX elevatorMaster = RobotMap.ElevatorMap.MASTER;

	private static final double K_ELEVATOR = 1176.48;

	public static final double TELEOP_ELEVATOR_INCREMENT = 8;
	
	public static ElevatorState state = ElevatorState.UNKNOWN;

	private static final double kP = 0.2; // 0.015 | 0.2
	private static final double kI = 0;
	private static final double kD = 0;
	private static final double kF = 0.35; // 0.35

	private static final int MOTION_MAGIC_ACCELERATION = 9001;
	private static final int MOTION_MAGIC_CRUISE_VELOCITY = 5467;
	// private static final int MOTION_MAGIC_ACCELERATION = 9001;
	// private static final int MOTION_MAGIC_CRUISE_VELOCITY = 5467;
	// private static final int MOTION_MAGIC_ACCELERATION = 20594;
	// private static final int MOTION_MAGIC_CRUISE_VELOCITY = 5467;

	private static final double MAX_SCALE_HEIGHT = 77000;//78900
	public static double elevatorHeight = 0;

	private static Elevator instance;

	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
		elevatorMaster.setInverted(true);
		RobotMap.ElevatorMap.SLAVE.setInverted(true);
		elevatorMaster.setSensorPhase(true);
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID_X,
				Constants.TIMEOUT_MS);

		elevatorMaster.selectProfileSlot(0, Constants.PID_ID_X);
		elevatorMaster.config_kP(0, kP, Constants.TIMEOUT_MS);
		elevatorMaster.config_kI(0, kI, Constants.TIMEOUT_MS);
		elevatorMaster.config_kD(0, kD, Constants.TIMEOUT_MS);
		elevatorMaster.config_kF(0, kF, Constants.TIMEOUT_MS);
		
		elevatorMaster.configForwardSoftLimitThreshold((int)MAX_SCALE_HEIGHT, Constants.TIMEOUT_MS);
		elevatorMaster.configForwardSoftLimitEnable(true, Constants.TIMEOUT_MS);
		elevatorMaster.configReverseSoftLimitThreshold(0, Constants.TIMEOUT_MS);
		elevatorMaster.configReverseSoftLimitEnable(true, Constants.TIMEOUT_MS);

		elevatorMaster.configMotionAcceleration(MOTION_MAGIC_ACCELERATION, Constants.TIMEOUT_MS);
		elevatorMaster.configMotionCruiseVelocity(MOTION_MAGIC_CRUISE_VELOCITY, Constants.TIMEOUT_MS);

		elevatorMaster.overrideLimitSwitchesEnable(false);
		elevatorMaster.configContinuousCurrentLimit(200, Constants.TIMEOUT_MS);

		elevatorMaster.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		elevatorMaster.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
		RobotMap.ElevatorMap.SLAVE.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		RobotMap.ElevatorMap.SLAVE.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
	}

	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, speed);
	}

	public boolean isElevatorAtSetpoint() {
		return elevatorMaster.getClosedLoopError(0) < 100;
	}

	public void setPosition(double inches) {
		// elevatorMaster.set(ControlMode.Position, (inches * K_ELEVATOR));
//		elevatorMaster.set(ControlMode.MotionMagic, (Math.min(inches, MAX_SCALE_HEIGHT) * K_ELEVATOR));
		 elevatorMaster.set(ControlMode.MotionMagic, inches);
		elevatorHeight = inches;
	}

	public void setPosition(ElevatorState state) {
		this.state = state;
		// switch(state) {
		// case TOP:
		// setPosition((12 * 4.5) + 4.667);
		// break;
		// case SCALE_MAX:
		// setPosition(12 * 4.5);
		// break;
		// case SCALE_MED:
		// setPosition(12 * 4);
		// break;
		// case SCALE_LOW:
		// setPosition(12 * 3.5);
		// break;
		// case BOTTOM:
		// setPosition(0);
		// break;
		// case SWITCH:
		// setPosition(12 * 2);
		// break;
		// }
		switch (state) {
		case TOP:
			setPosition(MAX_SCALE_HEIGHT);
			break;
		case SCALE_MAX:
			setPosition(77428);
			break;
		case SCALE_MED:
			setPosition(70000); //66301
			break;
		case SCALE_LOW:
			setPosition(54297);
			break;
		case BOTTOM:
			setPosition(0);
			break;
		case SWITCH:
			setPosition(40000);
			break;
		}
	}

	public double getElevatorHeightPercentage() {
		return elevatorHeight / MAX_SCALE_HEIGHT;
	}

	public void zero() {
		elevatorMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
		// setPosition(ElevatorState.BOTTOM);
	}

	public boolean getLimitSwitch() {
		return false;
	}

	public void log() {
		SmartDashboard.putNumber("Elevator Current 1", elevatorMaster.getOutputCurrent());
		SmartDashboard.putNumber("Elevator Current 2", RobotMap.ElevatorMap.SLAVE.getOutputCurrent());
	}

	@Override
	protected void initDefaultCommand() {
	}

}
