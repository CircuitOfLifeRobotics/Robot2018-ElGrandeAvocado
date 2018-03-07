package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	private final int LIFT_BIG_GEAR_TEETH = 60;
	private final int LIFT_SMALL_GEAR_TEETH = 40;

	private final TalonSRX leftIntake = RobotMap.IntakeMap.LEFT_INTAKE;

	private final VictorSPX rightIntake = RobotMap.IntakeMap.RIGHT_INTAKE;

	private final TalonSRX liftMotor = RobotMap.IntakeMap.LIFT_MOTOR;
	

	private final DoubleSolenoid grabSolenoid = RobotMap.IntakeMap.GRAB_SOLENOID;

	private static Intake instance;

	public static Intake getInstance() {
		if (instance == null)
			instance = new Intake();
		return instance;
	}

	private Intake() {
		liftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.PID_ID_X,
				Constants.TIMEOUT_MS);

		liftMotor.selectProfileSlot(0, Constants.PID_ID_X);
		liftMotor.config_kP(0, 0, Constants.TIMEOUT_MS);
		liftMotor.config_kI(0, 0, Constants.TIMEOUT_MS);
		liftMotor.config_kD(0, 0, Constants.TIMEOUT_MS);
		liftMotor.config_kF(0, 0, Constants.TIMEOUT_MS);

		liftMotor.configMotionAcceleration(0, Constants.TIMEOUT_MS);
		liftMotor.configMotionCruiseVelocity(0, Constants.TIMEOUT_MS);

		leftIntake.setInverted(true);
	}

	private void setPosition(double revolutions) {
		liftMotor.set(ControlMode.MotionMagic, (revolutions * Constants.CTRE_ENCODER_TICKS_PER_REV));
	}

	public void setIntakeRollers(double speed) {
		leftIntake.set(ControlMode.PercentOutput, speed);
		rightIntake.set(ControlMode.PercentOutput, speed);
	}

	public void setLiftMotorRaw(double speed) {
		liftMotor.set(ControlMode.PercentOutput, speed);
	}

	public boolean getLiftMotorLimitSwitch() {
		return liftMotor.getSensorCollection().isRevLimitSwitchClosed();
	}

	public void setGrabber(boolean grab) {
		grabSolenoid.set((!grab) ? Value.kForward : Value.kReverse);
	}

	public void setAngle(double degrees) {
		setPosition(((degrees / 360) * (LIFT_BIG_GEAR_TEETH / LIFT_SMALL_GEAR_TEETH)) * Constants.CTRE_ENCODER_TICKS_PER_REV);
	}

	public double getPosition() {
		return liftMotor.getSelectedSensorPosition(0);
	}

	public void zeroLift() {
		liftMotor.setSelectedSensorPosition(0, Constants.PID_ID_X, Constants.TIMEOUT_MS);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public double getLeftCurrent() {

		return leftIntake.getOutputCurrent();

	}

	public double getRightCurrent() {

		return rightIntake.getOutputCurrent();

	}

}
