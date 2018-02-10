package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

public class RunLift extends Command{
	
	@Override
	protected void initialize() {
		Lift.getInstance().setRaw(0.1);
	}
	
	@Override
	protected void end() {
		Lift.getInstance().setRaw(0); 
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
