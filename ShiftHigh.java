package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftHigh extends Command{
	
	@Override
	protected void initialize() {
		Drivetrain.getInstance().setShifter(true);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
