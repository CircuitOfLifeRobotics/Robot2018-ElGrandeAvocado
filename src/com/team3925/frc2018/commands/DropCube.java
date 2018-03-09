package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class DropCube extends CommandGroup{
	public DropCube() {
		addParallel(new OpenGrabbers());
		addSequential(new RunIntakeWheels(-0.40));
		addSequential(new WaitCommand(0.5));
		addParallel(new RunIntakeWheels(0));
		addSequential(new CloseGrabbers());
	}
	
	@Override
	protected void interrupted() {
		Intake.getInstance().setGrabber(false);
		Intake.getInstance().setIntakeRollers(0);
	}
	
	@Override
	protected void end() {
		this.interrupted();
	}
}
