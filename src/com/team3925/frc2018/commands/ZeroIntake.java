package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroIntake extends Command {

	@Override
	protected boolean isFinished() {
		return Intake.getInstance().getLiftMotorLimitSwitch();

	}

	@Override
	protected void end() {
		// stop motor
		Intake.getInstance().zeroLift();
		Intake.getInstance().setLiftMotorRaw(0);

	}

	@Override
	protected void execute() {
		Intake.getInstance().setLiftMotorRaw(-0.2);

	}

}// end of class
