package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Intake;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class ShootCube extends CommandGroup{
	public ShootCube() {
		addParallel(new CloseGrabbers());
		addSequential(new RunIntakeWheels(-1));
		addSequential(new WaitCommand(0.5));
		addParallel(new RunIntakeWheels(0));
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
