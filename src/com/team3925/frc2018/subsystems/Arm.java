package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

	public static enum ArmState {
		UNKNOWN, FORWARD_EXTENDED, RETRACTED, REVERSE_EXTENDED, SCALE_ANGLE;
	}
	
	//***** TUNE ME! *****
	private static final double kP = 3.00;
	private static final double kI = 0.00;
	private static final double kD = 0.00;
	private static final double kF = 0.35;
	
	private static final int ACCELERATION = 1500; // 400
	private static final int VELOCITY = 3000; //600
	
	private static final int SETPOINT_DEADZONE = 750;
	
	//*********************
	
	private static ArmState state = ArmState.UNKNOWN;

	private final TalonSRX liftMotor = RobotMap.IntakeMap.LIFT_MOTOR;
	
	private double currentSetpoint = 0;

	private static Arm instance;

	public static Arm getInstance() {
		if (instance == null)
			instance = new Arm();
		return instance;
	}

	public Arm() {
		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID_X,
				Constants.TIMEOUT_MS);

		liftMotor.selectProfileSlot(0, Constants.PID_ID_X);
		liftMotor.config_kP(0, kP, Constants.TIMEOUT_MS);
		liftMotor.config_kI(0, kI, Constants.TIMEOUT_MS);
		liftMotor.config_kD(0, kD, Constants.TIMEOUT_MS);
		liftMotor.config_kF(0, kF, Constants.TIMEOUT_MS);

		liftMotor.configMotionAcceleration	(ACCELERATION, Constants.TIMEOUT_MS);
		liftMotor.configMotionCruiseVelocity(VELOCITY, Constants.TIMEOUT_MS);
		
		liftMotor.setInverted	(true);
		liftMotor.setSensorPhase(false);
		liftMotor.overrideLimitSwitchesEnable(true);
	}
	
	@Deprecated
	public void setRaw(double speed) {
		liftMotor.set(ControlMode.PercentOutput, speed);
	}
	
	public double getPosition() {
		return liftMotor.getSelectedSensorPosition(0);
	}
	
	public boolean isAtSetpoint() {
		return (liftMotor.getSelectedSensorPosition(0) - currentSetpoint) < SETPOINT_DEADZONE;
	}
	
	public void zero() {
		liftMotor.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
	}
	
	public void setSetpoint(ArmState state) {
		Arm.state = state;
		switch (state) {
		case FORWARD_EXTENDED:
			setSetpoint(Constants.ArmSetpoints.EXTENDED);
			break;
		case RETRACTED:
			setSetpoint(Constants.ArmSetpoints.RETRACTED);
			break;
		case REVERSE_EXTENDED:
			setSetpoint(Constants.ArmSetpoints.BACKWARDS);
			break;
		case SCALE_ANGLE:
			setSetpoint(Constants.ArmSetpoints.SCALEASSIST);
			break;
		default:
			System.err.println("Failed to set " + state);
		}
	}
	
	public double positionAtState(ArmState state) {
		switch (state) {
		case FORWARD_EXTENDED:
			return (Constants.ArmSetpoints.EXTENDED);
		case RETRACTED:
			return (Constants.ArmSetpoints.RETRACTED);
		case REVERSE_EXTENDED:
			return (Constants.ArmSetpoints.BACKWARDS);
		case SCALE_ANGLE:
			return (Constants.ArmSetpoints.SCALEASSIST);
		default:
			System.err.println("Failed to get " + state);
			return 0;
		}
	}
	
	public boolean safeToGoDown() {
		if (liftMotor.getSelectedSensorPosition(0) >= Constants.ArmSetpoints.RETRACTED) {
			return false;
		}
		return true;
	}
	
	public ArmState getState() {
		return state;
	}
	
	public void setSetpoint(double setpoint) {
		liftMotor.set(ControlMode.MotionMagic, setpoint);
		currentSetpoint = setpoint;
	}
	
	@Override
	protected void initDefaultCommand() {
	}

}
