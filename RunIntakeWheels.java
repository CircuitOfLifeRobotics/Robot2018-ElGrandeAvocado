package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntakeWheels extends Command{
	
	@Override
	protected void initialize() {
		Intake.getInstance().setIntakeRollers(1);
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setIntakeRollers(0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
