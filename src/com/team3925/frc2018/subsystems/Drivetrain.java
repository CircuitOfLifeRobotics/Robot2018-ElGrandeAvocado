package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

	private final TalonSRX leftMaster = RobotMap.DrivetrainMap.LEFT_MASTER;

	private final TalonSRX rightMaster = RobotMap.DrivetrainMap.RIGHT_MASTER;

	private final DoubleSolenoid shiftSolenoid = RobotMap.DrivetrainMap.SHIFT_SOLENOID;

	public static Drivetrain instance;
	private static boolean shiftState = true;
	private static final boolean isGyroInverted = true;

	private static final double kP = 0.1;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final double kF = 0.9;

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
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		
		leftMaster.config_kF(Constants.pidIDx, kF, Constants.timeoutMs);
		leftMaster.config_kP(Constants.pidIDx, kP, Constants.timeoutMs);
		leftMaster.config_kI(Constants.pidIDx, kI, Constants.timeoutMs);
		leftMaster.config_kD(Constants.pidIDx, kD, Constants.timeoutMs);
		
		rightMaster.config_kF(Constants.pidIDx, kF, Constants.timeoutMs);
		rightMaster.config_kP(Constants.pidIDx, kP, Constants.timeoutMs);
		rightMaster.config_kI(Constants.pidIDx, kI, Constants.timeoutMs);
		rightMaster.config_kD(Constants.pidIDx, kD, Constants.timeoutMs);
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

	public double getLeftEncoderPosition() {
		return leftMaster.getSelectedSensorPosition(Constants.pidIDx);
	}

	public double getRightEncoderPosition() {
		return rightMaster.getSelectedSensorPosition(Constants.pidIDx);
	}

	public double getLeftSpeed() {
		return leftMaster.getSelectedSensorVelocity(Constants.pidIDx);
	}

	public double getRightSpeed() {
		return rightMaster.getSelectedSensorVelocity(Constants.pidIDx);
	}

	public void setVelocity(double l, double r) {
		leftMaster.set(ControlMode.Velocity, l);
		rightMaster.set(ControlMode.Velocity, r);
	}

	public double getGyroHeading() {
		return ((isGyroInverted) ? -1 : 1 ) * RobotMap.DrivetrainMap.DRIVETRAIN_IMU.getFusedHeading();
	}
	
	@Override
	protected void initDefaultCommand() {
	}

}
