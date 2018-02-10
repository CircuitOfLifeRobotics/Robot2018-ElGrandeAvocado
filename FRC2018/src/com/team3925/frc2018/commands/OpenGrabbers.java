package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class OpenGrabbers extends Command{
	
	@Override
	protected void initialize() {
		Intake.getInstance().setGrabber(true);
	}
	
	@Override
	protected void end() {
		Intake.getInstance().setGrabber(false);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
