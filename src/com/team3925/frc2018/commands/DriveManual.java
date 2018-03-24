package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Drivetrain;
import com.team3925.frc2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class DriveManual extends Command {

	public interface DriveManualInput {
		public abstract double getFwd();

		public abstract double getLeft();
	}

	private final boolean doReverseWhenReversing;

	private DriveManualInput input;
	private double prelimLeft, prelimRight;
	private double fwd, turn;
	private double scale;
	private static final double K_HEIGHT_SUBTRACTION = 0.8;

	public DriveManual(DriveManualInput input) {
		this.input = input;
		doReverseWhenReversing = false;
		requires(Drivetrain.getInstance());
	}

	@Override
	protected void initialize() {
		Drivetrain.getInstance().setRaw(0, 0);
	}

	@Override
	protected void execute() {
		fwd = input.getFwd();
		turn = input.getLeft();
		if (Math.abs(fwd) < 0.1)
			fwd = 0;
		if (doReverseWhenReversing && fwd != 0)
			turn *= Math.signum(fwd);
		prelimLeft = fwd + turn;
		prelimRight = fwd - turn;
		if (prelimLeft != 0 || prelimRight != 0) {
			scale = Math.max(Math.abs(fwd), Math.abs(turn)) / Math.max(Math.abs(prelimLeft), Math.abs(prelimRight));
			prelimLeft *= scale;
			prelimRight *= scale;
		}

		Drivetrain.getInstance().setRaw(
				prelimLeft * (1 - (Elevator.getInstance().getElevatorHeightPercentage() * K_HEIGHT_SUBTRACTION)),
				prelimRight * (1 - (Elevator.getInstance().getElevatorHeightPercentage() * K_HEIGHT_SUBTRACTION)));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Drivetrain.getInstance().setRaw(0, 0);
	}

	@Override
	protected void interrupted() {
		Drivetrain.getInstance().setRaw(0, 0);
	}

}
