package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class ReverseIntakeWheels extends Command{
	
	@Override
	protected void initialize() {
		Intake.getInstance().setIntakeRollers(-0.6);
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
