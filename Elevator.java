package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;
import com.team3925.frc2018.subsystems.Elevator.ElevatorState;

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

	private static final double MAX_SCALE_HEIGHT = 71805;//78900
	public static double elevatorHeight = 0;
	public static final double TELEOP_ELEVATOR_INCREMENT1 = 12000;
	private static final double SETPOINT_DEADZONE = 4096;

	private static Elevator instance;

	public static Elevator getInstance() {
		if (instance == null)
			instance = new Elevator();
		return instance;
	}

	public Elevator() {
		elevatorMaster.setInverted(true);
		RobotMap.ElevatorMap.SLAVE_A.setInverted(true);
		RobotMap.ElevatorMap.SLAVE_B.setInverted(false);
		RobotMap.ElevatorMap.SLAVE_C.setInverted(false);
		elevatorMaster.setSensorPhase(false);
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
		RobotMap.ElevatorMap.SLAVE_A.configNominalOutputForward(0, Constants.TIMEOUT_MS);
		RobotMap.ElevatorMap.SLAVE_A.configNominalOutputReverse(0, Constants.TIMEOUT_MS);
	}

	public void setRaw(double speed) {
		elevatorMaster.set(ControlMode.PercentOutput, speed);
	}
	public TalonSRX getMaster() {
		return elevatorMaster;
	}

	public void setPosition(double inches) {
		elevatorMaster.set(ControlMode.MotionMagic, inches);
		elevatorHeight = inches;
	}

	public void setPosition(ElevatorState state) {
		if (state != ElevatorState.UNKNOWN) {
			System.out.println("Elevator State is now " + state);
			switch (state) {
			case TOP:
				setPosition(MAX_SCALE_HEIGHT);
				break;
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
			default:
				System.err.println("Failed to set elevatorstate " + state);
				break;
			}
		}
	}
	
	public void setState(ElevatorState state) {
		this.state = state;
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
			System.err.println("Failed to get elevatorstate " + state);
			return 0;
		}
	}

	public double getElevatorHeight() {
		return elevatorMaster.getSelectedSensorPosition(0);
	}
	
	public void outputCurrent() {
		
		System.out.println("Master: " + RobotMap.ElevatorMap.MASTER.getOutputCurrent());
		System.out.println("SlaveA: " + RobotMap.ElevatorMap.SLAVE_A.getOutputCurrent());
		System.out.println("SlaveB: " + RobotMap.ElevatorMap.SLAVE_B.getOutputCurrent());
		System.out.println("SlaveC: " + RobotMap.ElevatorMap.SLAVE_C.getOutputCurrent());
		
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
		return Math.abs(elevatorMaster.getSelectedSensorPosition(0) - elevatorHeight) < SETPOINT_DEADZONE;
	}

	public void incrementHeight() {
		setPosition(elevatorHeight + TELEOP_ELEVATOR_INCREMENT);
		state = ElevatorState.OTHER;
	}

	public void decrementHeight() {
		setPosition(elevatorHeight - TELEOP_ELEVATOR_INCREMENT);
		state = ElevatorState.OTHER;
	}

	public void zero() {
		elevatorMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
	}
	
	public ElevatorState getState() {
		return state;
	}

	public void log() {
		SmartDashboard.putNumber("Elevator Current 1", elevatorMaster.getOutputCurrent());
		SmartDashboard.putNumber("Elevator Current 2", RobotMap.ElevatorMap.SLAVE_A.getOutputCurrent());
	}

	@Override
	protected void initDefaultCommand() {
	}

	public boolean getLimitSwitch() {
		// TODO Auto-generated method stub
		return false;
	}

}