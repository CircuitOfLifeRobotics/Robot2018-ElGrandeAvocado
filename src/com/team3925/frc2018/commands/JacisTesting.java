package com.team3925.frc2018.commands;

import com.team3925.utils.MotionProfileCommand;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Waypoint;

public class JacisTesting extends Command {

	MotionProfileCommand command;

	@Override
	protected void initialize() {
		command = new MotionProfileCommand(new Waypoint[] {
				new Waypoint(0, 10, 0)
		});
		
		command.start();
		System.out.println("I started");
	}
	
	@Override
	protected void execute() {
		System.out.println("I Running");
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
