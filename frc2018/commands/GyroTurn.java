package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class GyroTurn extends PIDCommand {

	private static final double THRESHOLD_ERROR = 2;
	private double angle;
	private double zeroGyro;
	private double finalTime, startTime;

	public GyroTurn(double desiredAngle, double timeInMillis) {
		super(0.035, 0, 0.075);

		angle = desiredAngle;
		finalTime = timeInMillis;
	}

	protected void initialize() {
		zeroGyro();
		startTime = Timer.getFPGATimestamp();
		System.out.println("GyroTurnStarted");
	}

	protected void execute() {
		super.setSetpoint(angle * getSmoothingFunction(Timer.getFPGATimestamp(), startTime + finalTime));
		System.out.println("Gyro: " + getGyroHeading() + "\tSetpoint = " + super.getSetpoint());
		// System.out.println(Gyro.getInstance().getFusedHeading());
	}

	protected boolean isFinished() {
		return (Math.abs(super.getPIDController().getError()) < THRESHOLD_ERROR);
	}

	protected void end() {
		Drivetrain.getInstance().setRaw(0, 0);
	}

	protected void interrupted() {
		this.end();
	}

	@Override
	protected double returnPIDInput() {
		return (getGyroHeading());
	}

	@Override
	protected void usePIDOutput(double output) {
		Drivetrain.getInstance().setRaw(output, -output);
	}

	private double getSmoothingFunction(double currentTime, double finalTime) {
		// Desmos Graph: https://www.desmos.com/calculator/3sls1cegnx
		return (0.5 * (1 - (Math.cos(Math.PI * (currentTime / finalTime)))));
	}

	private void zeroGyro() {
		zeroGyro = Drivetrain.getInstance().getGyroHeading();
	}

	private double getGyroHeading() {
		double heading = Drivetrain.getInstance().getGyroHeading();
		double finalAngle = heading;
		if (heading > 180) {
			finalAngle = heading - 360;
		}
		return finalAngle;
	}
}