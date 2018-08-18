package com.team3925.frc2018.commands;

import com.team3925.frc2018.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftLow extends Command{
	
	@Override
	protected void initialize() {
		Drivetrain.getInstance().setShifter(false);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
