package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

	public static enum ElevatorState {
		TOP, SCALE_MAX, SCALE_MED, SCALE_LOW, SWITCH, BOTTOM, UNKNOWN;
	}

	private final TalonSRX elevatorMaster = RobotMap.ElevatorMap.MASTER;

	public static ElevatorState state = ElevatorState.UNKNOWN;

	//*****TUNE ME*****
	private static final double kP = 0.2;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final double kF = 0.35;

	private static final int MOTION_MAGIC_ACCELERATION = 7000;
	private static final int MOTION_MAGIC_CRUISE_VELOCITY = 9000;

	private static final double MAX_SCALE_HEIGHT = 71805;
	
	private static final double TELEOP_ELEVATOR_INCREMENT = 8;
	
	private static final double SETPOINT_DEADZONE = 4096;
	//*****************

	public static double elevatorHeight = 0;

	private static Elevator instance;

	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
		elevatorMaster.setInverted(false);
		RobotMap.ElevatorMap.SLAVE_A.setInverted(false);
		RobotMap.ElevatorMap.SLAVE_B.setInverted(true);
		RobotMap.ElevatorMap.SLAVE_C.setInverted(true);
		elevatorMaster.setSensorPhase(false);
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID_X,
				Constants.TIMEOUT_MS);

		elevatorMaster.selectProfileSlot(0, Constants.PID_ID_X);
		elevatorMaster.config_kP(0, kP, Constants.TIMEOUT_MS);
		elevatorMaster.config_kI(0, kI, Constants.TIMEOUT_MS);
		elevatorMaster.config_kD(0, kD, Constants.TIMEOUT_MS);
		elevatorMaster.config_kF(0, kF, Constants.TIMEOUT_MS);

		elevatorMaster.configForwardSoftLimitThreshold((int) MAX_SCALE_HEIGHT, Constants.TIMEOUT_MS);
		elevatorMaster.configForwardSoftLimitEnable(true, Constants.TIMEOUT_MS);
		elevatorMaster.configReverseSoftLimitThreshold(0, Constants.TIMEOUT_MS);
		elevatorMaster.configReverseSoftLimitEnable(true, Constants.TIMEOUT_MS);

		elevatorMaster.configMotionAcceleration(MOTION_MAGIC_ACCELERATION, Constants.TIMEOUT_MS);
		elevatorMaster.configMotionCruiseVelocity(MOTION_MAGIC_CRUISE_VELOCITY, Constants.TIMEOUT_MS);

		elevatorMaster.overrideLimitSwitchesEnable(false);
		elevatorMaster.configContinuousCurrentLimit(200, Constants.TIMEOUT_MS);

		elevatorMaster.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		elevatorMaster.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
		RobotMap.ElevatorMap.SLAVE_A.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		RobotMap.ElevatorMap.SLAVE_A.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
	}

	@Deprecated
	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, speed);
	}
	
	public TalonSRX getMaster() {
		return elevatorMaster;
	}

	private void setPosition(double inches) {
		elevatorMaster.set(ControlMode.MotionMagic, inches);
		elevatorHeight = inches;
	}

	public void setPosition(ElevatorState state) {
		Elevator.state = state;
		switch (state) {
		case TOP:
			setPosition(MAX_SCALE_HEIGHT);
		case SCALE_MAX:
			setPosition(Constants.ElevatorSetpoints.SCALE_TOP);
			break;
		case SCALE_MED:
			setPosition(Constants.ElevatorSetpoints.SCALE_MED); // 66301
			break;
		case SCALE_LOW:
			setPosition(Constants.ElevatorSetpoints.SCALE_LOW);
			break;
		case BOTTOM:
			setPosition(Constants.ElevatorSetpoints.BOTTOM);
			break;
		case SWITCH:
			setPosition(Constants.ElevatorSetpoints.SWITCH);
			break;
		case UNKNOWN:
			break;
		default:
			System.err.println("Failed to set elevatorstate " + state);
		}
	}
	
	public double positionAtState(ElevatorState state) {
		switch (state) {
		case TOP:
			return (MAX_SCALE_HEIGHT);
		case SCALE_MAX:
			return (Constants.ElevatorSetpoints.SCALE_TOP);
		case SCALE_MED:
			return (Constants.ElevatorSetpoints.SCALE_MED); // 66301
		case SCALE_LOW:
			return (Constants.ElevatorSetpoints.SCALE_LOW);
		case BOTTOM:
			return (Constants.ElevatorSetpoints.BOTTOM);
		case SWITCH:
			return (Constants.ElevatorSetpoints.SWITCH);
		case UNKNOWN:
			return 0;
		default:
			System.err.println("Failed to set elevatorstate " + state);
			return 0;
		}
	}
	
	public double getElevatorHeight() {
		return elevatorMaster.getSelectedSensorPosition(0);
	}
	
	public boolean safeToDeployBackwards() {
		if (elevatorMaster.getSelectedSensorPosition(0) >= Constants.ElevatorSetpoints.DEPLOY_HEIGHT) {
			return true;
		}
		return false;
	}

	public double getElevatorHeightPercentage() {
		return elevatorHeight / MAX_SCALE_HEIGHT;
	}
	
	public boolean atSetpoint() {
		return (elevatorMaster.getSelectedSensorPosition(0) - elevatorHeight) < SETPOINT_DEADZONE;
	}
	
	public void incrementHeight() {
		setPosition(elevatorHeight + TELEOP_ELEVATOR_INCREMENT);
		state = ElevatorState.UNKNOWN;
	}
	public void decrementHeight() {
		setPosition(elevatorHeight - TELEOP_ELEVATOR_INCREMENT);
		state = ElevatorState.UNKNOWN;
	}

	public void zero() {
		elevatorMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
	}

	@Override
	protected void initDefaultCommand() {
	}

}
