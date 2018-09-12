package com.team3925.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team3925.frc2018.Constants;
import com.team3925.frc2018.RobotMap;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

	private static final double K_INTAKE = 1504;
	private final int LIFT_BIG_GEAR_TEETH = 60;
	private final int LIFT_SMALL_GEAR_TEETH = 40;

	private double currentSetpoint = 0;

	private final TalonSRX leftIntake = RobotMap.IntakeMap.LEFT_INTAKE;

	private final TalonSRX rightIntake = RobotMap.IntakeMap.RIGHT_INTAKE;

	private final TalonSRX liftMotor = RobotMap.IntakeMap.LIFT_MOTOR;

	private final DoubleSolenoid grabSolenoid = RobotMap.IntakeMap.GRAB_SOLENOID;
	private final DoubleSolenoid springSolenoid = RobotMap.IntakeMap.SPRING_SOLENOID;
	public final DigitalInput bottomSwitch = new DigitalInput(4);
	private final Counter counter = new Counter(bottomSwitch);

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
		liftMotor.config_kP(0, 3, Constants.TIMEOUT_MS);
		liftMotor.config_kI(0, 0.01, Constants.TIMEOUT_MS);
		liftMotor.config_kD(0, 0, Constants.TIMEOUT_MS);
		liftMotor.config_kF(0, 0.35, Constants.TIMEOUT_MS);

		liftMotor.configMotionAcceleration(300, Constants.TIMEOUT_MS);
		liftMotor.configMotionCruiseVelocity(450, Constants.TIMEOUT_MS);
		liftMotor.setInverted(true);
		liftMotor.setSensorPhase(true);
		liftMotor.overrideLimitSwitchesEnable(true);
		leftIntake.setInverted(true);
	}

	private void setPosition(double revolutions) {
		liftMotor.set(ControlMode.MotionMagic, (revolutions * Constants.CTRE_ENCODER_TICKS_PER_REV));
	}

	public void setIntakeRollers(double speed) {
		if (Math.abs(speed) <= 0)
			speed = 0.25;
		leftIntake.set(ControlMode.PercentOutput, speed);
		rightIntake.set(ControlMode.PercentOutput, speed);
	}

	public void setLiftMotorRaw(double speed) {
		liftMotor.set(ControlMode.PercentOutput, speed);
	}

	public void counterInit() {
		counter.reset();
	}

	public void outputCurrent() {
		System.out.println("Left " + RobotMap.IntakeMap.LEFT_INTAKE.getOutputCurrent());
		System.out.println("Right: " + RobotMap.IntakeMap.RIGHT_INTAKE.getOutputCurrent());
		System.out.println("Lift: " + RobotMap.IntakeMap.LIFT_MOTOR.getOutputCurrent());

	}

	public void dsOutput() {

		SmartDashboard.putNumber("Intake Current", RobotMap.IntakeMap.LIFT_MOTOR.getOutputCurrent());

	}

	public void stopIntake() {
		if (Intake.getInstance().isSwitchSet() == true) {
			System.out.println("Intake Stopped");
			// RobotMap.IntakeMap.LIFT_MOTOR.disable();

		} else {
			System.out.println("Intake is Good");
		}
	}

	public boolean getLiftMotorLimitSwitch() {
		return liftMotor.getSensorCollection().isRevLimitSwitchClosed();
	}

	public boolean isSwitchSet() {
		return counter.get() > 0;
	}

	public void setGrabber(boolean grab) {
		grabSolenoid.set((grab) ? Value.kForward : Value.kReverse);
		springSolenoid.set((grab) ? Value.kForward : Value.kReverse);
	}

	public boolean isAtSetpoint() {
		return Math.abs(liftMotor.getSelectedSensorPosition(0) - currentSetpoint) < 750;
	}

	public double getLiftMotorEncoder() {
		return liftMotor.getSelectedSensorPosition(0);
	}

	public void setAngle(double degrees) {
		liftMotor.set(ControlMode.MotionMagic, (-(90 - degrees) / 90) * K_INTAKE);
		currentSetpoint = (-(90 - degrees) / 90) * K_INTAKE;
		// setPosition(((degrees / 360) * (LIFT_BIG_GEAR_TEETH / LIFT_SMALL_GEAR_TEETH))
		// * Constants.CTRE_ENCODER_TICKS_PER_REV);
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
