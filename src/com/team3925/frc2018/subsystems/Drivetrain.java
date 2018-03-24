package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;
import com.team3925.utils.PIDTunable;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem implements PIDTunable {

	private final TalonSRX leftMaster = RobotMap.DrivetrainMap.LEFT_MASTER;

	private final TalonSRX rightMaster = RobotMap.DrivetrainMap.RIGHT_MASTER;

	private final DoubleSolenoid shiftSolenoid = RobotMap.DrivetrainMap.SHIFT_SOLENOID;

	public static Drivetrain instance;
	private static boolean shiftState = true;
	private static final boolean isGyroInverted = false;

	private double kP = 0.2;
	private double kI = 0;
	private double kD = 0;
	private double kF = 0.9;

	private static final NeutralMode drivetrainNeutralMode = NeutralMode.Brake;

	public static Drivetrain getInstance() {
		if (instance == null)
			instance = new Drivetrain();
		return instance;
	}

	private Drivetrain() {
		RobotMap.DrivetrainMap.LEFT_MASTER.setInverted(true);
		RobotMap.DrivetrainMap.LEFT_SLAVE_A.setInverted(true);
		RobotMap.DrivetrainMap.LEFT_SLAVE_B.setInverted(true);

		leftMaster.setSensorPhase(true);
		rightMaster.setInverted(false);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

		leftMaster.config_kF(Constants.PID_ID_X, kF, Constants.TIMEOUT_MS);
		leftMaster.config_kP(Constants.PID_ID_X, kP, Constants.TIMEOUT_MS);
		leftMaster.config_kI(Constants.PID_ID_X, kI, Constants.TIMEOUT_MS);
		leftMaster.config_kD(Constants.PID_ID_X, kD, Constants.TIMEOUT_MS);

		rightMaster.config_kF(Constants.PID_ID_X, kF, Constants.TIMEOUT_MS);
		rightMaster.config_kP(Constants.PID_ID_X, kP, Constants.TIMEOUT_MS);
		rightMaster.config_kI(Constants.PID_ID_X, kI, Constants.TIMEOUT_MS);
		rightMaster.config_kD(Constants.PID_ID_X, kD, Constants.TIMEOUT_MS);

		RobotMap.DrivetrainMap.LEFT_MASTER.setNeutralMode(drivetrainNeutralMode);
		RobotMap.DrivetrainMap.LEFT_SLAVE_A.setNeutralMode(drivetrainNeutralMode);
		RobotMap.DrivetrainMap.LEFT_SLAVE_B.setNeutralMode(drivetrainNeutralMode);

		RobotMap.DrivetrainMap.LEFT_MASTER.configOpenloopRamp(0.2, Constants.TIMEOUT_MS);
		RobotMap.DrivetrainMap.RIGHT_MASTER.configOpenloopRamp(0.2, Constants.TIMEOUT_MS);

		RobotMap.DrivetrainMap.RIGHT_MASTER.setNeutralMode(drivetrainNeutralMode);
		RobotMap.DrivetrainMap.RIGHT_SLAVE_A.setNeutralMode(drivetrainNeutralMode);
		RobotMap.DrivetrainMap.RIGHT_SLAVE_B.setNeutralMode(drivetrainNeutralMode);

		leftMaster.overrideLimitSwitchesEnable(false);
		rightMaster.overrideLimitSwitchesEnable(false);
	}

	public void setRaw(double l, double r) {
		leftMaster.set(ControlMode.PercentOutput, l);
		rightMaster.set(ControlMode.PercentOutput, r);
	}

	public void setShifter(boolean isHigh) {
		shiftSolenoid.set((isHigh) ? Value.kForward : Value.kReverse);
		shiftState = isHigh;
	}

	public boolean getShiftState() {
		return shiftState;
	}

	@Deprecated
	public TalonSRX getLeftMaster() {
		return leftMaster;
	}

	@Deprecated
	public TalonSRX getRightMaster() {
		return rightMaster;
	}

	public void zero() {
		leftMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
		rightMaster.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);

	}

	public double getLeftEncoderPosition() {
		return leftMaster.getSelectedSensorPosition(Constants.PID_ID_X);
	}

	public double getRightEncoderPosition() {
		return -rightMaster.getSelectedSensorPosition(Constants.PID_ID_X);
	}

	public double getLeftSpeed() {
		return leftMaster.getSelectedSensorVelocity(Constants.PID_ID_X);
	}

	public double getRightSpeed() {
		return -rightMaster.getSelectedSensorVelocity(Constants.PID_ID_X);
	}

	public void setVelocity(double l, double r) {
		leftMaster.set(ControlMode.Velocity, l);
		rightMaster.set(ControlMode.Velocity, r);
	}

	public void setRamp(double ramp) {
		leftMaster.configOpenloopRamp(ramp, Constants.TIMEOUT_MS);
		rightMaster.configOpenloopRamp(ramp, Constants.TIMEOUT_MS);
	}

	public double getGyroHeading() {
		return ((isGyroInverted) ? -1 : 1) * RobotMap.DrivetrainMap.DRIVETRAIN_IMU.getFusedHeading();
	}

	@Override
	protected void initDefaultCommand() {
	}

	// Get-er and set-er methods
	@Override
	public double getkP() {
		return kP;
	}

	@Override
	public void setkP(double kP) {
		this.kP = kP;
	}

	@Override
	public double getkI() {
		return kI;
	}

	@Override
	public void setkI(double kI) {
		this.kI = kI;
	}

	@Override
	public double getkD() {
		return kD;
	}

	@Override
	public void setkD(double kD) {
		this.kD = kD;
	}

	@Override
	public double getkF() {
		return kF;
	}

	@Override
	public void setkF(double kF) {
		this.kF = kF;
	}
}
